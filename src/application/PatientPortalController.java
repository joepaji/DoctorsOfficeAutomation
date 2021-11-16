package application;

//Importing needed libraries
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

public class PatientPortalController implements Initializable{

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
	private Button editButton;
	@FXML
	private TextArea contactInfo;
	@FXML
	private TextArea lastVisitSummary;
	
	//Creating Variables
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String destination;
	private String username;
	
	//Default constructor
	public PatientPortalController() {

	}
	
	//Method to set the username 
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	
	//Code for text areas
	//Shows patient's last visit summary
	public void lastVisitSummary() {
		//Setting the textfield with the given username
		DoctorNurseActions actions = new DoctorNurseActions(username);
		lastVisitSummary.setText(actions.getLatestCheckin());
	}
	
	//Shows patient's contact summary
	public void patientContactinfo() {
		//Creating variables
		String patientInfo = "";
		String fName, lName, dob, phoneNum, emailAddress;
		
		//PostGreSQL
		try {
			//Query line to get variables
			String sql = "SELECT first_name, last_name, dob, phone, email "
					+ " FROM patient"
					+ " WHERE username = '" + username + "';";
			
			//Connecting to dataabse
			Database database = new Database();
			Connection c = database.connect();
			Statement stmt = c.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE,
				    ResultSet.CONCUR_READ_ONLY
				);
			
			//Executing the statment
			ResultSet result = stmt.executeQuery(sql);
			
			//If statment to get variables
			if(result.first()) {
				// Get contact information
				fName = result.getString("first_name");
				lName = result.getString("last_name");
				dob = result.getString("dob");
				phoneNum = result.getString("phone");
				emailAddress = result.getString("email");
				
				//Concatination of strings
				patientInfo += "First Name: " + fName;
				patientInfo += "\nLast Name: " + lName;
				patientInfo += "\nDate of Birth: " + dob;
				patientInfo += "\nPhone Number: " + phoneNum;
				patientInfo += "\nEmail Address: " + emailAddress;
			}
						
			c.close();
		}
		catch(SQLException e) {
			//Catches error 
			e.printStackTrace();
		}
	
		//Setting the textfield
		contactInfo.setText(patientInfo);
	}
	
	
	//Code for buttons
	//Home button for the patient
	public void patientHome(ActionEvent event) throws IOException {
		//Making the destination the patient portal GUI
		destination = "PatientPortal.fxml";
		
		//Creating a new FXMLLoader to be able to set the usename on the click of the button and text fields to load
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
		
		//Creating a new FXMLLoader to be able to load the visits on button press passing the username
		FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
		root = loader.load();
		VisitsController vc = loader.getController();
		vc.setUsername(username);
		vc.allVisitSummary();
	
		//Creating the stage and scene of the GUI
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
		stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		scene = new Scene(root);
		stage.setScene(scene);
		MessengerController messengerController = loader.getController();
		messengerController.setUsername(username);
		messengerController.setSelfFirstLast();
		messengerController.displayMessages();
		stage.show();
	}
	
	//Sign out button for the patient
	public void patientSignOut(ActionEvent event) throws IOException {
		//Making the destination the login GUI
		destination = "login.fxml";
		root = FXMLLoader.load(getClass().getResource(destination));
		
		//Creating the stage and scene of the GUI
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//Edit button to edit patient info
	public void patientEdit(ActionEvent event) throws IOException {
		//Making the destination the patient contact edit GUI
		destination = "patientContactEdit.fxml";
		
		//Creating a new FXMLLoader to be able to load the edits on button press
		FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
		root = loader.load();
		PatientEditController pec = loader.getController();
		pec.setUsername(username);
		
		//Creating the stage and scene of the GUI
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}