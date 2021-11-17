package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

public class MessengerController implements Initializable{
	
	@FXML
	private Button signOut;
	@FXML
	private Button toHome;
	@FXML
	private Button sendMessage;
	@FXML
	private TextField recipientFirst;
	@FXML
	private TextField recipientLast;
	@FXML
	private TextField date;
	@FXML
	private TextField messageBody;
	@FXML
	private TextArea recentMessages;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String destination;
	public String username;
	private int userID;
	private String first;
	private String last;
	private String recipientFirstName;
	private String recipientLastName;
	private String dateString;
	private String message;
	
	public MessengerController()
	{
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	// Sign the user out, return to login 
	public void signOut(ActionEvent event) throws IOException
	{	
		destination = "login.fxml";
		root = FXMLLoader.load(getClass().getResource(destination));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();  // assigns the stage to the currently running stage from main
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// Takes the user back to the appropriate home portals
	public void toHome(ActionEvent event) throws IOException
	{
		
		int loginType = -1;
		
		// Create the query 
		String sql="SELECT usertype FROM patient WHERE username='" + username + "'"
				+ " union SELECT usertype FROM staff WHERE username='" + username + "';";
		
		// Connect to the database
		Database database = new Database();
		Connection c = database.connect();
		Statement stmt;
		
		try {
			stmt = c.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
				    ResultSet.CONCUR_READ_ONLY);
			
			// Execute the statement and save output into result		
			ResultSet result = stmt.executeQuery(sql);
		
			// Set the usertype data into loginType
			if(result.next())
			{
				loginType=result.getInt("usertype");
			}
					
		
			switch(loginType) // Set the appropriate destination based on the usertype that was authenticated
			{
			
				case 1 : destination = "PatientPortal.fxml";  
			
					//This passes the username of the patient to the patientCheckIn controller
					FXMLLoader patientLoader = new FXMLLoader(getClass().getResource(destination));
					root = patientLoader.load();
				
					PatientPortalController patientPortal = patientLoader.getController();
					patientPortal.setUsername(username);
					patientPortal.lastVisitSummary();
					patientPortal.patientContactinfo();


					break;
				
				case 2 : destination = "NursePortal.fxml";	
			
					//This passes the username of the patient to the patientCheckIn controller
					FXMLLoader nurseLoader = new FXMLLoader(getClass().getResource(destination));
					root = nurseLoader.load();
			
					NursePortalController nursePortal = nurseLoader.getController();
					nursePortal.setUsername(username);
				
					break;
				
				case 3 : destination = "DoctorPortal.fxml";
				
					// Passes the username of the patient to the patientCheckIn controller
					FXMLLoader doctorLoader = new FXMLLoader(getClass().getResource(destination));
					root = doctorLoader.load();
					DoctorPortalController doctorPortal = doctorLoader.getController();
					doctorPortal.setUsername(username);
			
					break;				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		if(destination != "")  // Check for valid destination, and then set and show new scene
		{	
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();  
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();			
		}
	}
	// Sends the new message to the database
	public void sendMessage(ActionEvent event) throws IOException
	{
		// Retrieve the text data from text fields
		recipientFirstName = recipientFirst.getText().toString();
		recipientLastName = recipientLast.getText().toString();
		dateString = date.getText().toString();
		message = messageBody.getText().toString();
		
		try {
			// Create the query 
			String sql = "INSERT INTO messages(receiver_first, receiver_last, sender_first, sender_last, date, message_body)"
				 	   + "VALUES('"+ recipientFirstName +"', '"+ recipientLastName +"', '"+ first +"', '"+ last +"', '"+ dateString +"', '"+ message +"');";
			
			// Connect to the database
			Database database = new Database();
			Connection c = database.connect();
			Statement stmt = c.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE,
				    ResultSet.CONCUR_READ_ONLY
			);
			
			// Execute the update and display messages again
			stmt.executeUpdate(sql);
			displayMessages();
			
			// Close connection
			c.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Display the current user's messages in the database
	public void displayMessages()
	{
		try {
			// Create the query by searching for messages to or from current user
			String sql = "SELECT * FROM messages WHERE ( (receiver_first = '"+ first +"' and receiver_last = '"+ last +"') or "
					   + "(sender_first = '"+ first +"' and sender_last = '"+ last +"') );";
			
			// Connect to the database
			Database database = new Database();
			Connection c = database.connect();
			Statement stmt = c.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE,
				    ResultSet.CONCUR_READ_ONLY
			);
			// Execute query and save the output into result
			ResultSet result = stmt.executeQuery(sql);
			String output = "";
			
			// Move the resultset cursor to the last row and retrieve the appropriate column data
			result.last();
			output += "Sender:\t" + result.getString(3) + " " + result.getString(4) + "\n";
			output += "Recipient:\t" + result.getString(5) + " " + result.getString(6) + "\n";
			output += "Date:\t\t" + result.getString(2) + "\n\n";
			output += "Message:\t" + result.getString(1) + "\n\n";
			output += "---------------------------------------------------------\n";
			
			// Move cursor to the previous entry. While result is not empty, iterate through and add to the formatted string
			while(result.previous())
			{
				output += "Sender:\t" + result.getString(3) + " " + result.getString(4) + "\n";
				output += "Recipient:\t" + result.getString(5) + " " + result.getString(6) + "\n";
				output += "Date:\t\t" + result.getString(2) + "\n\n";
				output += "Message:\t" + result.getString(1) + "\n\n";
				output += "---------------------------------------------------------\n";
				
			}	
		
			// Load the created string into the recentMessages textArea
			recentMessages.setText(output);
			
			
			// Close the connection
			c.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Set the first and last name of the user by searching the database by username
	public void setSelfFirstLast()
	{
		try {
			// Create the query
			String sql = "SELECT first_name, last_name FROM staff WHERE (username = '"+ username +"')"
					+ "union SELECT first_name, last_name FROM patient WHERE (username = '"+ username +"');";
			
			// Connect to the database
			Database database = new Database();
			Connection c = database.connect();
			Statement stmt = c.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE,
				    ResultSet.CONCUR_READ_ONLY
			);
			
			// Execute query and save output into result
			ResultSet result = stmt.executeQuery(sql);
			
			// If result is not null, set this.first and this.last
			if(result.next())
			{
				this.first = result.getString("first_name");
				this.last = result.getString("last_name");
			}
			// Close the connection
			c.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Set the username of the user. Used to pass user data from previous controller
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
