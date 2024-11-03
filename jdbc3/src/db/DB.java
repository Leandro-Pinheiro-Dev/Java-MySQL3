package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	private static Connection conn = null; // INICIALIZAÇÃO DA CONEXÃO ESTÁTICA
	
    // MÉTODO PARA OBTER A CONEXÃO COM O BANCO DE DADOS
	public static Connection getConnection() {
		if (conn == null) { // VERIFICA SE A CONEXÃO JÁ EXISTE
			try {
				Properties props = loadProperties(); // CARREGAMENTO DAS PROPRIEDADES DO BANCO
				String url = props.getProperty("dburl"); // OBTENÇÃO DA URL DO BANCO
				conn = DriverManager.getConnection(url, props); // ESTABELECIMENTO DA CONEXÃO
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage()); // EXCEÇÃO EM CASO DE ERRO NA CONEXÃO
			}
		}
		return conn; // RETORNA A CONEXÃO
	}
	
    // MÉTODO PARA FECHAR A CONEXÃO COM O BANCO DE DADOS
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close(); // FECHA A CONEXÃO
			} catch (SQLException e) {
				throw new DbException(e.getMessage()); // EXCEÇÃO EM CASO DE ERRO AO FECHAR A CONEXÃO
			}
		}
	}
	
    // MÉTODO PARA CARREGAR AS PROPRIEDADES DO BANCO A PARTIR DO ARQUIVO
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties(); // INICIALIZAÇÃO DO OBJETO PROPRIEDADES
			props.load(fs); // CARREGAMENTO DAS PROPRIEDADES
			return props; // RETORNA AS PROPRIEDADES
		}
		catch (IOException e) {
			throw new DbException(e.getMessage()); // EXCEÇÃO EM CASO DE ERRO DE I/O
		}
	}
	
    // MÉTODO PARA FECHAR O STATEMENT
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close(); // FECHA O STATEMENT
			} catch (SQLException e) {
				throw new DbException(e.getMessage()); // EXCEÇÃO EM CASO DE ERRO AO FECHAR O STATEMENT
			}
		}
	}

    // MÉTODO PARA FECHAR O RESULTSET
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close(); // FECHA O RESULTSET
			} catch (SQLException e) {
				throw new DbException(e.getMessage()); // EXCEÇÃO EM CASO DE ERRO AO FECHAR O RESULTSET
			}
		}
	}
}