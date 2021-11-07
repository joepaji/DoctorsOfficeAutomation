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
	
	// Returns patient contact info and history A[0,1]
	public String[] getPatientInfo() {
		String patientInfo = "";
		String patientHistory = "";
		// Variables to build patientHistory
		String immunization, currMed, prevMed, healthIssues;
		
		immunization = "Immunizations\n";
		currMed = "\nCurrent Medication\n";
		prevMed = "\nPrevious Medications\n";
		healthIssues = "\nPrevious Health Issues\n";
		
		int prevMedLength = prevMed.length();
		
		try {
			String sql = "SELECT first_name, last_name, dob, phone, immunization, "
					+ "curr_med, health_issues FROM patient"
					+ " WHERE username = '" + username + "';";
			
			Database database = new Database();
			Connection c = database.connect();
			Statement stmt = c.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE,
				    ResultSet.CONCUR_READ_ONLY
				);
			ResultSet result = stmt.executeQuery(sql);
			
			if(result.first()) {
				// Get contact information
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				String dob = result.getString("dob");
				String phone = result.getString("phone");
				
				// Get history variables
				if(result.getString("immunization").isBlank())
					immunization += "None\n";
				else immunization += result.getString("immunization");
				
				if(result.getString("curr_med").isBlank())
					currMed += "None\n";
				else currMed += result.getString("curr_med");
				
				if(result.getString("health_issues").isBlank())
					healthIssues += "None\n";
				else healthIssues += result.getString("health_issues");
				
				patientInfo += "Patient Name: " + firstName + " " + lastName;
				patientInfo += "\n\nDate of Birth: " + dob;
				patientInfo += "\n\nPhone Number: " + phone;
			}
			
			// Get previously prescribed medications from visits table
			sql = "SELECT medications FROM visits WHERE username = '" + username + "'";
			result = stmt.executeQuery(sql);
			
			while(result.next()) {
				if(!result.getString("medications").isBlank())
					prevMed += result.getString("medications") + "\n";
			}
			
			if(prevMed.length() == prevMedLength) 
				prevMed += "None\n";
			
			patientHistory += immunization + currMed + prevMed + healthIssues;
			
			
			c.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return new String[] {patientInfo, patientHistory};
	}
}
