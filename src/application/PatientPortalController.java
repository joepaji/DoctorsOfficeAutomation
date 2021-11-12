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
		String fName, lName, dob, phoneNum, emailAddress;
		
		//PostGreSQL
		try {
			String sql = "SELECT first_name, last_name, dob, phone, email "
					+ " FROM patient"
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
			e.printStackTrace();
		}
	
		contactInfo.setText(patientInfo);
	}
	
	
	//Code for buttons
	//Home button for the patient
	public void patientHome(ActionEvent event) throws IOException {
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
	
	//Visits button for the patient
	public void patientVisits(ActionEvent event) throws IOException {
		destination = "Visits.fxml";
		root = FXMLLoader.load(getClass().getResource(destination));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();  
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//Messages button for the patients
	public void patientMessages(ActionEvent event) throws IOException {
		destination = "Messages.fxml";
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
		destination = "patientContactEdit.fxml";
		FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
		//root = FXMLLoader.load(getClass().getResource(destination));
		root = loader.load();
		PatientEditController pec = loader.getController();
		pec.setUsername(username);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
}







