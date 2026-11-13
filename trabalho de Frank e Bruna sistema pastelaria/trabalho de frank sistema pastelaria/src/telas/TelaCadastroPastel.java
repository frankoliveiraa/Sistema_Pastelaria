package telas;

import dao.PastelDAO;
import entidade.Pastel;

import javax.swing.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TelaCadastroPastel extends JFrame {

    private JTextField txtId;
    private JTextField txtSabor;
    private JTextField txtTamanho;
    private JTextField txtCategoria;
    private JTextField txtPreco;
    private JTextField txtQuantidade;
    private JTextField txtDataValidade;
    private JButton btnSalvar;
    private JButton btnCancelar;

    private Pastel pastelEdicao;
    private Runnable aoSalvar;

    public TelaCadastroPastel(Pastel pastelParaEditar, Runnable aoSalvar) {
        this.pastelEdicao = pastelParaEditar;
        this.aoSalvar = aoSalvar;

        configurarJanela();
        criarComponentes();

        if (pastelEdicao != null) {
            carregarDadosEdicao();
        }
    }

    private void configurarJanela() {
        setTitle(pastelEdicao == null ? "Cadastro de Pastel" : "Editar Pastel");
        setSize(445, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    private void criarComponentes() {
        JLabel lblId = new JLabel("Código (ID):");
        lblId.setBounds(25, 20, 150, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(180, 20, 220, 25);
        txtId.setEnabled(false); // Chave primária não pode ser alterada
        add(txtId);

        JLabel lblSabor = new JLabel("Sabor:");
        lblSabor.setBounds(25, 60, 150, 25);
        add(lblSabor);

        txtSabor = new JTextField();
        txtSabor.setBounds(180, 60, 220, 25);
        add(txtSabor);

        JLabel lblTamanho = new JLabel("Tamanho:");
        lblTamanho.setBounds(25, 100, 150, 25);
        add(lblTamanho);

        txtTamanho = new JTextField();
        txtTamanho.setBounds(180, 100, 220, 25);
        add(txtTamanho);

        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(25, 140, 150, 25);
        add(lblCategoria);

        txtCategoria = new JTextField();
        txtCategoria.setBounds(180, 140, 220, 25);
        add(txtCategoria);

        JLabel lblPreco = new JLabel("Preço (R$):");
        lblPreco.setBounds(25, 180, 150, 25);
        add(lblPreco);

        txtPreco = new JTextField();
        txtPreco.setBounds(180, 180, 220, 25);
        add(txtPreco);

        JLabel lblQuantidade = new JLabel("Qtd. Disponível:");
        lblQuantidade.setBounds(25, 220, 150, 25);
        add(lblQuantidade);

        txtQuantidade = new JTextField();
        txtQuantidade.setBounds(180, 220, 220, 25);
        add(txtQuantidade);

        JLabel lblDataValidade = new JLabel("Validade (dd/mm/aaaa):");
        lblDataValidade.setBounds(25, 260, 150, 25);
        add(lblDataValidade);

        txtDataValidade = new JTextField();
        txtDataValidade.setBounds(180, 260, 220, 25);
        add(txtDataValidade);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(95, 310, 110, 30);
        btnSalvar.addActionListener(e -> salvar());
        add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(225, 310, 110, 30);
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);
    }

    private void carregarDadosEdicao() {
        txtId.setText(String.valueOf(pastelEdicao.getId()));
        txtSabor.setText(pastelEdicao.getSabor());
        txtTamanho.setText(pastelEdicao.getTamanho());
        txtCategoria.setText(pastelEdicao.getCategoria());
        txtPreco.setText(String.valueOf(pastelEdicao.getPreco()));
        txtQuantidade.setText(String.valueOf(pastelEdicao.getQuantidadeDisponivel()));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtDataValidade.setText(sdf.format(pastelEdicao.getDataValidade()));
    }

    private void salvar() {
        String sabor = txtSabor.getText().trim();
        String tamanho = txtTamanho.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String precoTexto = txtPreco.getText().trim().replace(",", ".");
        String qtdTexto = txtQuantidade.getText().trim();
        String validadeTexto = txtDataValidade.getText().trim();

        if (sabor.isBlank() || tamanho.isBlank() || categoria.isBlank() || precoTexto.isBlank() || qtdTexto.isBlank() || validadeTexto.isBlank()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double preco = Double.parseDouble(precoTexto);
            int quantidade = Integer.parseInt(qtdTexto);

            if (preco <= 0 || quantidade < 0) {
                JOptionPane.showMessageDialog(this, "O preço deve ser maior que zero e a quantidade não pode ser negativa.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            java.util.Date dataConvertida = sdf.parse(validadeTexto);
            Date dataValidade = new Date(dataConvertida.getTime());

            PastelDAO dao = new PastelDAO();

            if (pastelEdicao == null) {
                Pastel novo = new Pastel(sabor, tamanho, categoria, preco, quantidade, dataValidade);
                if (dao.inserir(novo)) {
                    JOptionPane.showMessageDialog(this, "Pastel cadastrado com sucesso!");
                    if (aoSalvar != null) aoSalvar.run();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar pastel no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                pastelEdicao.setSabor(sabor);
                pastelEdicao.setTamanho(tamanho);
                pastelEdicao.setCategoria(categoria);
                pastelEdicao.setPreco(preco);
                pastelEdicao.setQuantidadeDisponivel(quantidade);
                pastelEdicao.setDataValidade(dataValidade);

                if (dao.atualizar(pastelEdicao)) {
                    JOptionPane.showMessageDialog(this, "Pastel atualizado com sucesso!");
                    if (aoSalvar != null) aoSalvar.run();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar pastel no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço e Quantidade devem ser números válidos.", "Erro de Tipo", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Data de validade inválida. Utilize o formato dd/mm/aaaa.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
        }
    }
}
