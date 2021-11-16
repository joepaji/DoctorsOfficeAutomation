package application;

//Importing needed libraries
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientEditController {
	
	
	//Variables for FXML
	@FXML
	private Button signOut;
	@FXML
	private Button toMessages;
	@FXML
	private Button toHome;
	@FXML
	private Button toVisits;
	@FXML
	private Button saveButton;
	@FXML
	private TextField fNameEdit;
	@FXML
	private TextField lNameEdit;
	@FXML
	private TextField dobEdit;
	@FXML
	private TextField phoneNumEdit;
	@FXML
	private TextField emailAddressEdit;
	@FXML
	private Label errorMessage;
	
	//Creating Variables
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String destination;
	private String username;
	
	//Default constructor
	public PatientEditController() {

	}
	
	//Method to set the username 
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	//Code for buttons
	//Home button for the patient
	public void patientHome(ActionEvent event) throws IOException {
		////Making the destination the patient portal GUI
		destination = "PatientPortal.fxml";   
		
		//Creating a new FXMLLoader to be able to set the usename on the click of the button
		FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
		root = loader.load();
		PatientPortalController ppc = loader.getController();
		ppc.setUsername(username);
		ppc.lastVisitSummary();
		ppc.patientContactinfo();
		
		//Creating the stage and scene of the GUI
		stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
		
	//Visits button for the patient
	public void patientVisits(ActionEvent event) throws IOException {
		//Making the destination the visits GUI
		destination = "Visits.fxml";
		FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
		root = loader.load();
		VisitsController visitsController=loader.getController();
		visitsController.setUsername(username);
		visitsController.allVisitSummary();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();  
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
		
	//Messages button for the patients
	public void patientMessages(ActionEvent event) throws IOException {
		destination = "Messenger.fxml";
		FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
		root = loader.load();
		MessengerController messengerController=loader.getController();
		messengerController.setUsername(username);
		messengerController.setSelfFirstLast();
		messengerController.displayMessages();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
		
	//Sign out button for the patient
	public void patientSignOut(ActionEvent event) throws IOException {
		//Making the destination the login GUI 
		destination = "login.fxml";
		FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
		root = loader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//Save Button for the patient
	public void patientSave(ActionEvent event) throws IOException {
		//If statment to check all the textfields are not empty
		if (fNameEdit.getText().isEmpty()) {
			errorMessage.setText("Error! Please Fill All Boxes");
		}
		else if (lNameEdit.getText().isEmpty()) {
			errorMessage.setText("Error! Please Fill All Boxes");
		}
		else if (dobEdit.getText().isEmpty()) {
			errorMessage.setText("Error! Please Fill All Boxes");
		}
		else if (phoneNumEdit.getText().isEmpty()) {
			errorMessage.setText("Error! Please Fill All Boxes");
		}
		else if (emailAddressEdit.getText().isEmpty()) {
			errorMessage.setText("Error! Please Fill All Boxes");
		}
		else {
			//Calling method to save in database
			saveEdit();
			
			//Making the destination the patient portal GUI 
			destination = "PatientPortal.fxml"; 
			
			//Creating a new FXMLLoader to be able to change the edits on the click of the button
			FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
			root = loader.load();
			PatientPortalController ppc = loader.getController();
			ppc.setUsername(username);
			ppc.lastVisitSummary();
			ppc.patientContactinfo();
			
			//Creating the stage and scene of the GUI to go to the patient portal page
			stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}

	//patientSave method
	public void saveEdit() {
		try {
			//Creating vriables to save from the database
			String fName = fNameEdit.getText().toString();
			String lName = lNameEdit.getText().toString();
			String dob = dobEdit.getText().toString();
			String phoneNum = phoneNumEdit.getText().toString();
			String emailAddress = emailAddressEdit.getText().toString();
			
			//Connecting to the database
			Database db = new Database();
			Connection c = db.connect();	
			
			//Query to update the edits 
			String sql ="UPDATE patient SET first_name='"+fName+"',last_name='"+lName+"',dob='"+dob+"',phone='"+phoneNum+"',email='"+emailAddress+"' WHERE username='"+username+"'";
			PreparedStatement prepStatement = c.prepareStatement(sql);
			prepStatement.executeUpdate();
			c.close();
		} catch (SQLException e) {	
			
			//Error message if it catches an exception
			System.out.println("Error in adding record. Check that all required values are filled out.");
			e.printStackTrace();
		}
		
	}	
}
