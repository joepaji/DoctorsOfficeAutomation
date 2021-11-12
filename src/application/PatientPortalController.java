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
	
	//Variables
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String destination;
	private String username;
	
	//Default constructor
	public PatientPortalController() {

	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	
	//Code for text areas
	//Shows patient's last visit summary
	public void lastVisitSummary() {
		DoctorNurseActions actions = new DoctorNurseActions(username);
		lastVisitSummary.setText(actions.getLatestCheckin());
	}
	
	//Shows patient's contact summary
	public void patientContactinfo() {
		String patientInfo = "";
		String fName, lName, dob, address, city, state, phoneNum, emailAddress;
		
		//
	
	}
	
	
	//Code for buttons
	//Home button for the patient
	public void patientHome(ActionEvent event) throws IOException {
		destination = "PatientPortal.fxml";   
		root = FXMLLoader.load(getClass().getResource(destination));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//Visits button for the patient
	public void patientVisits(ActionEvent event) throws IOException {
		//destination = "Visits.fxml";
		destination = "login.fxml";    //CHANGE LATER
		root = FXMLLoader.load(getClass().getResource(destination));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();  
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//Messages button for the patients
	public void patientMessages(ActionEvent event) throws IOException {
		//destination = "Messages.fxml";
		destination = "login.fxml";    //CHANGE LATER
		root = FXMLLoader.load(getClass().getResource(destination));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//Sign out button for the patient
	public void patientSignOut(ActionEvent event) throws IOException {
		destination = "login.fxml";
		root = FXMLLoader.load(getClass().getResource(destination));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//Edit button to edit patient info
	public void patientEdit(ActionEvent event) throws IOException {
		destination = "patientEdit.fxml";
		root = FXMLLoader.load(getClass().getResource(destination));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
}







