package application;

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
	
	//Variables
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String destination;
	private String username;
	
	//Default constructor
	public PatientEditController() {

	}
	
	public void setUsername(String username) {
		this.username = username;
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
		//If statment to check for errors
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
			saveEdit();
			destination = "PatientPortal.fxml"; 
			FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
			root = loader.load();
			PatientPortalController ppc = loader.getController();
			ppc.setUsername(username);
			ppc.lastVisitSummary();
			ppc.patientContactinfo();
			//root = FXMLLoader.load(getClass().getResource(destination));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}

	//patientSave method
	public void saveEdit() {
		try {
			String fName = fNameEdit.getText().toString();
			String lName = lNameEdit.getText().toString();
			String dob = dobEdit.getText().toString();
			String phoneNum = phoneNumEdit.getText().toString();
			String emailAddress = emailAddressEdit.getText().toString();
			
			Database db = new Database();
			Connection c = db.connect();	
			String sql ="UPDATE patient SET first_name='"+fName+"',last_name='"+lName+"',dob='"+dob+"',phone='"+phoneNum+"',email='"+emailAddress+"' WHERE username='"+username+"'";
			PreparedStatement prepStatement = c.prepareStatement(sql);
			prepStatement.executeUpdate();
			c.close();
		} catch (SQLException e) {	
			System.out.println("Error in adding record. Check that all required values are filled out.");
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
