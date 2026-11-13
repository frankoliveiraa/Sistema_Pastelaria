package entidade;

import java.sql.Date;

public class Pastel {
    private int id;
    private String sabor;
    private String tamanho;
    private String categoria;
    private double preco;
    private int quantidadeDisponivel;
    private Date dataValidade;

    public Pastel() {
    }

    public Pastel(String sabor, String tamanho, String categoria, double preco, int quantidadeDisponivel, Date dataValidade) {
        this.sabor = sabor;
        this.tamanho = tamanho;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.dataValidade = dataValidade;
    }

    public Pastel(int id, String sabor, String tamanho, String categoria, double preco, int quantidadeDisponivel, Date dataValidade) {
        this.id = id;
        this.sabor = sabor;
        this.tamanho = tamanho;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.dataValidade = dataValidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }
}