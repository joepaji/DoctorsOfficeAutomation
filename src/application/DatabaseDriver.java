package application;
import java.sql.*;

// Sample database connection tester. Runs a simple query.
// Use this file as a template for making database queries
public class DatabaseDriver {
	
	public static void main(String[] args) {
			
			Database database = new Database();
		
			Connection c = database.connect();
			

			try {
				String sql = "SELECT * FROM patient";
						
				Statement stmt = c.createStatement();
	
				ResultSet result = stmt.executeQuery(sql);
				
				while(result.next()) {
					String user = result.getString("username");
					String firstName = result.getString("first_name");
					String lastName = result.getString("last_name");
					
					System.out.println("Username: "+user+"\nFirst Name: "+firstName+"\nLast Name: "+lastName);
				}
				
				c.close();
			} 
			catch (SQLException e) {
				System.out.println("Error in connecting to postgreSQL server.");
				e.printStackTrace();
			}
		
	}  
}
