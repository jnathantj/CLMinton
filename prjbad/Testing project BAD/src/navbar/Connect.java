package navbar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Connect {
private static Connect instance;
	
	private String username = "root";
	private String password = "";
	
	private String host = "localhost:3306";
	private String database = "CLminton";
	
	private String url = String.format("jdbc:mysql://%s/%s", host, database);
	
	private Connection conn;

	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
			
			conn.createStatement();
			
			System.out.println("Koneksi terhubung");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static synchronized Connect getInstance() {
		if(instance == null) {
			instance = new Connect();
		}
		
		return instance;
	}
	
	public void executeUpdate(String query) throws SQLException{
		Statement statement = conn.createStatement();
		statement.executeUpdate(query);
	}
	
	public ResultSet executeQuery(String query) throws SQLException {
		Statement statement = conn.createStatement();
		return statement.executeQuery(query);
	}

}
