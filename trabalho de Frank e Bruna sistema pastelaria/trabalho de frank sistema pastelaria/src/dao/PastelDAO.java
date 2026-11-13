package dao;

import conexao.Conexao;
import entidade.Pastel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PastelDAO {

    public boolean inserir(Pastel pastel) {
        String sql = "INSERT INTO pastel (sabor, tamanho, categoria, preco, quantidade_disponivel, data_validade) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, pastel.getSabor());
            stmt.setString(2, pastel.getTamanho());
            stmt.setString(3, pastel.getCategoria());
            stmt.setDouble(4, pastel.getPreco());
            stmt.setInt(5, pastel.getQuantidadeDisponivel());
            stmt.setDate(6, pastel.getDataValidade());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar pastel: " + e.getMessage());
            return false;
        }
    }

    public List<Pastel> listar() {
        List<Pastel> lista = new ArrayList<>();
        String sql = "SELECT * FROM pastel";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pastel p = new Pastel();
                p.setId(rs.getInt("id"));
                p.setSabor(rs.getString("sabor"));
                p.setTamanho(rs.getString("tamanho"));
                p.setCategoria(rs.getString("categoria"));
                p.setPreco(rs.getDouble("preco"));
                p.setQuantidadeDisponivel(rs.getInt("quantidade_disponivel"));
                p.setDataValidade(rs.getDate("data_validade"));

                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pastéis: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Pastel pastel) {
        String sql = "UPDATE pastel SET sabor = ?, tamanho = ?, categoria = ?, preco = ?, quantidade_disponivel = ?, data_validade = ? WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, pastel.getSabor());
            stmt.setString(2, pastel.getTamanho());
            stmt.setString(3, pastel.getCategoria());
            stmt.setDouble(4, pastel.getPreco());
            stmt.setInt(5, pastel.getQuantidadeDisponivel());
            stmt.setDate(6, pastel.getDataValidade());
            stmt.setInt(7, pastel.getId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pastel: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM pastel WHERE id = ?";
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir pastel: " + e.getMessage());
            return false;
        }
    }
}