package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;



public class Login {
	
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
	
	public void userLogin(ActionEvent event) {
		usernameData = username.getText().toString();
		passwordData = password.getText().toString();
		int loginType = authenticateUser(usernameData, passwordData);  // authenticateUser will return -1 upon failed authetication, or usertype upon success
		//System.out.println(Data);
		System.out.println(loginType);
		loginType = 2;
		
		Main m = new Main();
		String destination = "";
		
		switch(loginType) // this switch will determine what the outcome of the authenticator was
		{
			case -1 : destination = "login.fxml";
				wrongLogin.setText("Incorrect username/password. Please try again.");
				System.out.println("Sorry, it looks like that username/password combination does not match any of our records."); // change this to display to user	
				break;
				
			case 1 : destination = "Patient Portal.fxml";
				m.changeScene(destination);
				break;
				
			case 2 : destination = "NP.fxml";
				m.changeScene(destination);	
				break;
				
			case 3 : destination = "Doctors Portal.fxml";
				m.changeScene(destination);
				default : 
		}
		
		//
	}
	
	public void userCreateAccount(ActionEvent event) {
		//checkLogin();
		Main m = new Main();
		m.changeScene("createAccount.fxml");
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
	

}