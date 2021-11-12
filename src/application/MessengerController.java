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
	
	public void signOut(ActionEvent event) throws IOException
	{	
		destination = "login.fxml";
		root = FXMLLoader.load(getClass().getResource(destination));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();  // assigns the stage to the currently running stage from main
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// to home? need to distinguish between who is going where
	public void toPatientPortal(ActionEvent event) throws IOException
	{
		destination = "PatientPortal.fxml";    
		root = FXMLLoader.load(getClass().getResource(destination));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();  // assigns the stage to the currently running stage from main
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void sendMessage(ActionEvent event) throws IOException
	{
		
		recipientFirstName = recipientFirst.getText().toString();
		recipientLastName = recipientLast.getText().toString();
		dateString = date.getText().toString();
		message = messageBody.getText().toString();
		try {
			String sql = "INSERT INTO messages(receiver_first, receiver_last, sender_first, sender_last, date, message_body)"
				 	   + "VALUES('"+ recipientFirstName +"', '"+ recipientLastName +"', '"+ first +"', '"+ last +"', '"+ dateString +"', '"+ message +"');";
			
			Database database = new Database();
			Connection c = database.connect();
			Statement stmt = c.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE,
				    ResultSet.CONCUR_READ_ONLY
			);
			
			stmt.executeUpdate(sql);
			
			/*if(result.first())
			{
				System.out.println("successfully sent message");
			}
			else 
			{
				System.out.println("error sending message");
			}*/
			
			displayMessages();
			
			
			c.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void displayMessages()
	{
		try {
			System.out.println("first: " + first);
			System.out.println("last: " + last);
			String sql = "SELECT * FROM messages WHERE ( (receiver_first = '"+ first +"' and receiver_last = '"+ last +"') or "
													  + "(sender_first = '"+ first +"' and sender_last = '"+ last +"') );";
			
			Database database = new Database();
			Connection c = database.connect();
			Statement stmt = c.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE,
				    ResultSet.CONCUR_READ_ONLY
			);
			
			ResultSet result = stmt.executeQuery(sql);
			String output = "";
			
			
			if(result.last())
			{
				
			}
			
			while(result.previous())
			{
				output += "Sender:\t\t" + result.getString(3) + " " + result.getString(4) + "\n";
				output += "Recipient:\t" + result.getString(5) + " " + result.getString(6) + "\n";
				output += "Date:\t\t" + result.getString(2) + "\n\n";
				output += "Message:\t" + result.getString(1) + "\n\n";
				output += "---------------------------------------------------------\n";
				
			}
			recentMessages.setText(output);
			
			
			
			c.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setSelfFirstLast()
	{
		try {
			String sql = "SELECT * FROM staff WHERE (username = '"+ username +"');";
			
			Database database = new Database();
			Connection c = database.connect();
			Statement stmt = c.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE,
				    ResultSet.CONCUR_READ_ONLY
			);
			
			ResultSet result = stmt.executeQuery(sql);
			
			if(result.next())
			{
				this.first = result.getString("first_name");
				this.last = result.getString("last_name");
			}
			
			
			c.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
