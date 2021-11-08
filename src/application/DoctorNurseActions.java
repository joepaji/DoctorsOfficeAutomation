package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// This class contains functions that will be used by both Doctor and Nurse controllers
public class DoctorNurseActions {
	private String username;
	
	public DoctorNurseActions(String username) {
		this.username = username;
	}
	
	// Gets the most recent checkin and returns as a string
	public String getLatestCheckin() {
		String latestCheckin = "";
		String firstName = "";
		String lastName = "";
		String dob = "";
		String phone = "";
		String height = "\n\nHeight: ";
		String weight = "\nWeight: ";
		String temp = "\nTemperature: ";
		String bp = "\nBlood Pressure: ";
		String allergies = "\n\nAllergies: ";
		String currMed = "\n\nCurrent Medication: ";
		String notes = "\n\nNotes: ";
		
		
		try {
			String sql = "SELECT * from visits WHERE username = '" + username + "'"
					+ " ORDER by date_time DESC";
			
			Database database = new Database();
			Connection c = database.connect();
			Statement stmt = c.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE,
				    ResultSet.CONCUR_READ_ONLY
				);
			ResultSet result = stmt.executeQuery(sql);
			
			if(result.first()) {
				height += result.getString("height");
				weight += result.getString("weight");
				temp += result.getString("temperature");
				bp += result.getString("bp");
				if(result.getString("nurse_notes").isBlank())
					notes += "None";
				else notes += result.getString("nurse_notes");
				if(result.getString("allergies").isBlank())
					allergies += "None";
				else allergies += result.getString("allergies");
			}
			
			sql = "SELECT first_name, last_name, dob, phone, curr_med "
					+ "from patient WHERE username = '" + username + "'";
			result = stmt.executeQuery(sql);
			
			if(result.first()) {
				firstName = result.getString("first_name");
				lastName = result.getString("last_name");
				dob = result.getString("dob");
				phone = result.getString("phone");
				
				if(result.getString("curr_med").isBlank())
					currMed += "None";
				else currMed += result.getString("curr_med");
			}
			
			latestCheckin += "Patient Name: " + firstName + " " + lastName;
			latestCheckin += "\nDate of Birth: " + dob;
			latestCheckin += "\nPhone Number: " + phone;
			latestCheckin += height + weight + temp + bp + allergies + currMed + notes;
			
			c.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return latestCheckin;
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
