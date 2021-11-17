package application;
import java.sql.*;

//opens the data base server using our pgadmin username and password
public class Database {
	private String url = "jdbc:postgresql://ec2-3-217-91-165.compute-1.amazonaws.com:5432/daru65ongv34rq";
	private String username = "vabxlxvlcddmrq";
	private String password = "972f4aeaf501184937bbc4680ac80512f01726446c3ffd7841a4a8bb449ae6da";
	
	
	// Creates a database connection to the project database. Remember to invoke .close() to close the connection after use
	public Connection connect() {
		
		try {
			Connection c = DriverManager.getConnection(url,username,password);
			System.out.println("Connected to server.");
			
			return c;
		}
		catch (SQLException e) {
			System.out.println("Error in connecting to postgreSQL server.");
			e.printStackTrace();
		}
		
		return null;
	}

}
