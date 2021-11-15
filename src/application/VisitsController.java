package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.IOException;

public class VisitsController {


	//Variables for FXML
	@FXML
	private Button signOut;
	@FXML
	private Button toMessages;
	@FXML
	private Button toHome;
	@FXML
	private TextArea lastVisitSummary;
	
	//Variables
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String destination;
	private String username;
	
	
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
	
	
	//Default constructor
	public VisitsController() {

	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	
	//Code for text areas
	//Shows patient's last visit summary
	public void lastVisitSummary() {
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
			
			while(result.next()) {
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
				System.out.println(height + weight);
				System.out.println("_______________");
			}
		/*	
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
			*/
		
			System.out.println(latestCheckin);
			
			c.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	//Code for buttons
	//Home button for the patient
	public void toHome(ActionEvent event) throws IOException {
		destination = "PatientPortal.fxml";   
		FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
		root = loader.load();
		PatientPortalController ppc = loader.getController();
		ppc.setUsername(username);
		ppc.lastVisitSummary();
		ppc.patientContactinfo();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//Messages button for the patients
	public void toMessages(ActionEvent event) throws IOException {
		destination = "Messages.fxml";
		root = FXMLLoader.load(getClass().getResource(destination));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//Sign out button for the patient
	public void signOut(ActionEvent event) throws IOException {
		destination = "login.fxml";
		root = FXMLLoader.load(getClass().getResource(destination));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}


