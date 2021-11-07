package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DoctorNurseActions {
	private String username;
	
	public DoctorNurseActions(String username) {
		this.username = username;
	}
	
	public void displayPatientHistory() {
		try {
			String sql = "";
			Database database = new Database();
			Connection c = database.connect();
			Statement stmt = c.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			
			
			c.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getPatientInfo() {
		String patientInfo = "";
		
		try {
			String sql = "SELECT first_name, last_name, dob, phone FROM "
					+ "patient WHERE username = '" + username + "';";
			
			Database database = new Database();
			Connection c = database.connect();
			Statement stmt = c.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE,
				    ResultSet.CONCUR_READ_ONLY
				);
			ResultSet result = stmt.executeQuery(sql);
			
			if(result.first()) {
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				String dob = result.getString("dob");
				String phone = result.getString("phone");
				
				patientInfo += "Patient Name: " + firstName + " " + lastName;
				patientInfo += "\n\nDate of Birth: " + dob;
				patientInfo += "\n\nPhone Number: " + phone;
			}
			
			c.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return patientInfo;
	}
}
