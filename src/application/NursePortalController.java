package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;

public class NursePortalController{
	
	// FXML Parameters
	@FXML
	private Button signOut;
	@FXML
	private Button toMessages;
	@FXML
	private Button toHome;
	@FXML
	private Button checkIn;
	@FXML
	private Button search;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField dateOfBirth;
	@FXML
	private Label searchError;
	@FXML
	private Label checkinError;
	@FXML
	private TextArea prevCheckin;
	@FXML
	private Label hello;
	
	// Class Parameters
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String destination;
	private String username;
	private String patientUsername;

	
	public NursePortalController()
	{		
		
	}
	
	// Back to home screen
	public void toHome(ActionEvent event) throws IOException {
		destination = "NursePortal.fxml";   
		FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
		root = loader.load();
		NursePortalController npc = loader.getController();
		npc.setUsername(username);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Sign out takes the user back to login screen
	public void signOut(ActionEvent event) throws IOException
	{	
		destination = "login.fxml";
		root = FXMLLoader.load(getClass().getResource(destination));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();  // assigns the stage to the currently running stage from main
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Takes the user to the messenger portal
	public void toMessages(ActionEvent event) throws IOException
	{
		// Set the destination to the proper portal and load it into root
		destination = "Messenger.fxml";   
		FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
		root = loader.load();
		
		// Pass the username into the messenger controller
		MessengerController messenger = loader.getController();
		messenger.setUsername(username);
		// Set the first and last name, and display past messages
		messenger.setSelfFirstLast();
		messenger.displayMessages(); 
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();  // assigns the stage to the currently running stage from main
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// search function which is used to lookup patients by their first and last names and date of birth
	public void search(ActionEvent event) throws IOException
	{
		try {
			// Connect to the database
			Database db = new Database();
			Connection c = db.connect();
			Statement stmt = c.createStatement();
			
			// Search the database for entry with matching first name, last name, and date of birth
			String sql = "SELECT * FROM patient WHERE (first_name = '" + firstName.getText().toString() +
												  "' AND last_name = '" + lastName.getText().toString() + "'"
												  		+ "AND dob = '" + dateOfBirth.getText().toString()+"');";	
			// Execute the query and save into resultset
			ResultSet rs = stmt.executeQuery(sql); 
			
			// If the resultset is not empty, set the patient username from the appropriate column in the resultset
			if(rs.next()) 
			{
				patientUsername = rs.getString("username");
				searchError.setText("");
				// Display the last checkin now that the patient username has been set
				displayLastCheckin();
				
			// Else patient not found, set error text
			} else searchError.setText("Patient not found.");
				
		} catch (SQLException e) {	
			System.out.println("Error in connecting to postgreSQL server.");
			e.printStackTrace();
		}
	}
	
	// checkIn function takes the nurse to the PatientCheckIn portal
	public void checkIn(ActionEvent event) throws IOException
	{
		// Check for null username and display error text
		if(patientUsername == null || patientUsername == "")
		{
			checkinError.setText("Oops, no patients selected.");
		}
		else // Create a new checkin portal and set the username and patientUsername
		{
			destination = "PatientCheckIn.fxml";   
			FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
			root = loader.load();
			
			//This passes the username of the nurse and the patient to the patientCheckIn controller
			PatientCheckIn patientCheckIn = loader.getController();
			patientCheckIn.setUsername(username);
			patientCheckIn.setPatientUsername(patientUsername);
			// Display's the patients checkIn info 
			patientCheckIn.displayInfo();
			
			// Sets the new scene and shows it
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();  // assigns the stage to the currently running stage from main
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		
	}
	
	// Used to pass username data from previous portals to this one, and sets the hello label 
	public void setUsername(String username) {
		this.username = username;
		hello.setText("Hello, " + username + "!");
	}
	
	// Display the last checkin of the current patient
	public void displayLastCheckin() {
		DoctorNurseActions actions = new DoctorNurseActions(patientUsername);
		prevCheckin.setText(actions.getLatestCheckin());
	}
	
}
