package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;


// Controller class for the login screen
public class Login {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	// FXML variables
	@FXML
	private Button login;
	@FXML
	private Label wrongLogin;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button createAccount;
	
	// Class parameters
	private String usernameData;
	private String passwordData;
	
	public Login() {
		
	}
	
	// Log the user in based on the inputted username and password, authenticate, and open proper destination
	public void userLogin(ActionEvent event) throws IOException {
		usernameData = username.getText().toString();
		passwordData = password.getText().toString();
		int loginType = authenticateUser(usernameData, passwordData);  // authenticateUser will return -1 upon failed authetication, or usertype upon success
		
		String destination = "";
		
		switch(loginType) // this switch will determine what the outcome of the authenticator was
		{
			case -1 : destination = "";
				wrongLogin.setText("Incorrect username/password. Please try again.");
				break;
			// Patient login, take to patient portal
			case 1 : destination = "PatientPortal.fxml";  
			
				//This passes the username of the patient to the patientCheckIn controller
				FXMLLoader patientLoader = new FXMLLoader(getClass().getResource(destination));
				root = patientLoader.load();
				
				PatientPortalController patientPortal = patientLoader.getController();
				patientPortal.setUsername(usernameData);
				patientPortal.lastVisitSummary();
				patientPortal.patientContactinfo();


				break;
			// 	
			case 2 : destination = "NursePortal.fxml";	
			
				// This passes the username of the patient to the patientCheckIn controller
				FXMLLoader nurseLoader = new FXMLLoader(getClass().getResource(destination));
				root = nurseLoader.load();
			
				NursePortalController nursePortal = nurseLoader.getController();
				nursePortal.setUsername(usernameData);
				
				break;
				
			case 3 : destination = "DoctorPortal.fxml";
				
				// This passes the username of the patient to the patientCheckIn controller
				FXMLLoader doctorLoader = new FXMLLoader(getClass().getResource(destination));
				root = doctorLoader.load();
		
				DoctorPortalController doctorPortal = doctorLoader.getController();
				doctorPortal.setUsername(usernameData);
			
				break;				
		}
		
		if(destination != "")  // make sure there was a valid destination
		{	
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();  // assigns the stage to the currently running stage from main
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();			
		}
	}
	
	public void userCreateAccount(ActionEvent event) throws IOException {
		// checkLogin();
		root = FXMLLoader.load(getClass().getResource("createAccount.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();  // assigns the stage to the currently running stage from main
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Authenticate user passsword and username combinatiob against the values in the database
	public int authenticateUser(String pUsername, String pPassword){
		try {
			Database db = new Database();
			Connection c = db.connect();
			Statement stmt = c.createStatement();
			
			// Build the query
			String sql1 = "SELECT password, usertype FROM patient WHERE username = '"+ pUsername + "' "
					+ "union SELECT password, usertype from staff WHERE username = '"+ pUsername + "' ";
		
			String sql2 = "SELECT * FROM staff WHERE username = '"+ pUsername +"';";
			
			ResultSet rs1 = stmt.executeQuery(sql1);
			
			c.close();
			
			String pass = "";
			int usertype = 0;
			
			// Check to make sure rs is not empty
			if(rs1.next()) {
				pass = rs1.getString("password");
				usertype = rs1.getInt("usertype");
				System.out.println("Usertype: " + usertype);
			} else return -1; //error code
			
			if(pPassword.equals(pass)) {
				return usertype;
			} else return -1; //error code
				
		} catch (SQLException e) {	
			System.out.println("Error in connecting to postgreSQL server.");
			e.printStackTrace();
			return -1; // error code
		}
	}

}