package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;



public class Login {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private Button login;
	@FXML
	private Label wrongLogin;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Button createAccount;
	
	//data
	private String usernameData;
	private String passwordData;
	
	public Login() {
		
	}
	
	public void userLogin(ActionEvent event) throws IOException {
		usernameData = username.getText().toString();
		passwordData = password.getText().toString();
		int loginType = authenticateUser(usernameData, passwordData);  // authenticateUser will return -1 upon failed authetication, or usertype upon success
		//System.out.println(Data);
		System.out.println(loginType);
		
		String destination = "";
		
		
		switch(loginType) // this switch will determine what the outcome of the authenticator was
		{
			case -1 : destination = "";
				wrongLogin.setText("Incorrect username/password. Please try again.");
				break;
				
			case 1 : destination = "PatientPortal.fxml";
				break;
				
			case 2 : destination = "NursePortal.fxml";	

				break;
				
			case 3 : destination = "DoctorPortal.fxml";
				break;
		}
		
		// open the proper scene depending on userType
		if(destination != "" /*&& destination != "DoctorPortal.fxml"*/)  // make sure there was a valid destination
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
			root = loader.load();
			PatientPortalController ppc = loader.getController();
			ppc.setUsername(usernameData);
			ppc.lastVisitSummary();
//			root = FXMLLoader.load(getClass().getResource(destination));		
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();  // assigns the stage to the currently running stage from main
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
			
		}/*
		else if (destination.equals("PatientPortal.fxml")) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
			root = loader.load();
			PatientPortalController patient = loader.getController();
			patient.setUsername(usernameData);
			patient.lastVisitSummary();
			//root = FXMLLoader.load(getClass().getResource(destination));		
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();  // assigns the stage to the currently running stage from main
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}*/
	}
	
	public void userCreateAccount(ActionEvent event) throws IOException {
		//checkLogin();
		root = FXMLLoader.load(getClass().getResource("createAccount.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();  // assigns the stage to the currently running stage from main
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// authenticate user upon login
	public int authenticateUser(String pUsername, String pPassword){
		try {
			Database db = new Database();
			Connection c = db.connect();
			Statement stmt = c.createStatement();
			
			// make sure the username is unique 
			String sql1 = "SELECT * FROM patient WHERE username = '"+ pUsername +"';";
			ResultSet rs1 = stmt.executeQuery(sql1);
			c.close();
			
			String pass = "";
			int usertype = 0;
			
			// check to make sure rs is not empty
			if(rs1.next()) {
				pass = rs1.getString("password");
				usertype = rs1.getInt("usertype");
				System.out.println(pass);
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
	
	/*
	public void displayLastCheckIn() {
		DoctorNurseActions actions = new DoctorNurseActions(usernameData);
		lastVisitSummary.setText(actions.getLatestCheckin());
	}
	*/

}