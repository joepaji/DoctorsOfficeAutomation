package application;
import java.sql.*;

public class Database {
	
	public static void main(String[] args) {
		
		//THIS NEEDS TO CHANGE AND COME FROM OUR ACTUAL DATABASE
		String url = "jdbc:postgresql://ec2-3-217-91-165.compute-1.amazonaws.com:5432/daru65ongv34rq";
		String username = "vabxlxvlcddmrq";
		String password = "972f4aeaf501184937bbc4680ac80512f01726446c3ffd7841a4a8bb449ae6da";
	
		try {
			
			Connection c = DriverManager.getConnection(url,username,password);
			
			System.out.println("Connected to server.");
			
			String sql = "SELECT * FROM doctors";
					
			Statement stmt = c.createStatement();

			ResultSet result = stmt.executeQuery(sql);
			
			while(result.next()) {
				String user = result.getString("username");
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				
				System.out.println("Username: "+user+"\nFirst Name: "+firstName+"\nLast Name: "+lastName);
			}
			
			c.close();
		} catch (SQLException e) {
			System.out.println("Error in connecting to postgreSQL server.");
			e.printStackTrace();
		}
		
	}  
}
