package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

    // ATIVIDADE: O PROGRAMA ESTABELECE UMA CONEXÃO COM O BANCO DE DADOS,
    // INSERE UM NOVO VENDEDOR NA TABELA "SELLER" E RETORNA O ID DO NOVO VENDEDOR.
	public static void main(String[] args) {

        // CRIA UM FORMATO DE DATA ESPECÍFICO (DD/MM/YYYY)
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null; // INICIALIZAÇÃO DA CONEXÃO COM O BANCO DE DADOS
		PreparedStatement st = null; // INICIALIZAÇÃO DO PREPARED STATEMENT
		try {
            // OBTER A CONEXÃO COM O BANCO DE DADOS
			conn = DB.getConnection();

			// EXEMPLO 1: PREPARAÇÃO DE UMA INSTRUÇÃO SQL PARA INSERIR UM VENDEDOR
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS); // OBTENÇÃO DE CHAVES GERADAS AUTOMATICAMENTE

            // DEFINIÇÃO DOS VALORES PARA OS PARÂMETROS DA INSTRUÇÃO SQL
			st.setString(1, "Carl Purple"); // NOME
			st.setString(2, "carl@gmail.com"); // EMAIL
			st.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime())); // DATA DE NASCIMENTO
			st.setDouble(4, 3000.0); // SALÁRIO BASE
			st.setInt(5, 4); // ID DO DEPARTAMENTO

			// EXEMPLO 2: (COMENTADO) INSERÇÃO DE DEPARTAMENTOS
			//st = conn.prepareStatement(
			//		"insert into department (Name) values ('D1'),('D2')", 
			//		Statement.RETURN_GENERATED_KEYS);

            // EXECUÇÃO DA INSTRUÇÃO SQL
			int rowsAffected = st.executeUpdate();
			
            // VERIFICA SE A INSERÇÃO FOI BEM-SUCEDIDA
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys(); // OBTEÇÃO DAS CHAVES GERADAS
				while (rs.next()) { // ITERAÇÃO SOBRE AS CHAVES GERADAS
					int id = rs.getInt(1); // OBTENÇÃO DO ID
					System.out.println("Done! Id: " + id); // IMPRESSÃO DO ID NO CONSOLE
				}
			}
			else {
				System.out.println("No rows affected!"); // NENHUMA LINHA AFETADA
			}
		} 
		catch (SQLException e) {
			e.printStackTrace(); // IMPRESSÃO DE ERROS DE SQL
		} 
		catch (ParseException e) {
			e.printStackTrace(); // IMPRESSÃO DE ERROS DE PARSE
		}
		finally {
            // FECHAMENTO DO STATEMENT E DA CONEXÃO
			DB.closeStatement(st);
			DB.closeConnection(); 
		} 
	} 
}
