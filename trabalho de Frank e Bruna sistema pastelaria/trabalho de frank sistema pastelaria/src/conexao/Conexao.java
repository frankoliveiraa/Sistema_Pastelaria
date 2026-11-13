package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {
    private static final String URL = "jdbc:sqlite:pastelaria.db";

    public static Connection conectar() throws SQLException {
        Connection conexao = DriverManager.getConnection(URL);
        criarTabela(conexao);
        return conexao;
    }

    private static void criarTabela(Connection conexao) {
        String sql = "CREATE TABLE IF NOT EXISTS pastel (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "sabor TEXT NOT NULL, " +
                     "tamanho TEXT NOT NULL, " +
                     "categoria TEXT NOT NULL, " +
                     "preco REAL NOT NULL, " +
                     "quantidade_disponivel INTEGER NOT NULL, " +
                     "data_validade DATE NOT NULL" +
                     ");";
        try (Statement stmt = conexao.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }
    }
}