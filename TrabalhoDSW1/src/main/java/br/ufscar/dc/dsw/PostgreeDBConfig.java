package br.ufscar.dc.dsw;

import java.sql.*;

public class PostgreeDBConfig {
	
	private static String URL = "jdbc:postgresql://localhost:5432/sistemaConsultas";
	private static String username = "postgres";
	private static String password = "admin";
	
	public static Connection getConnection() throws SQLException{
		Connection db = null;
		try {
			Class.forName("org.postgresql.Driver");
			db = DriverManager.getConnection(URL, username , password);
			System.out.println("Conectado com sucesso ao banco");
		}catch(ClassNotFoundException e) {
			//e.printStackTrace();
			System.out.println("Erro na conexão com o banco");
		}finally {
			System.out.println("Fim da conexão");
		}
		return db;
	}

}
