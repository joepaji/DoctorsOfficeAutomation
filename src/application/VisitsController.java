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
		DoctorNurseActions actions = new DoctorNurseActions(username);
		lastVisitSummary.setText(actions.getLatestCheckin());
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

}


