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
	private TextField recipientID;
	@FXML
	private TextField subject;
	@FXML
	private TextField messageBody;
	@FXML
	private TextArea recentMessages;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String destination;
	private String username;
	private int userID;
	
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
	
	public void displayMessages()
	{
		try {
			String sql = "SELECT * FROM messages WHERE (receiver_username = '"+ username +"' or sender_username = '"+ username +"');";
			
			Database database = new Database();
			Connection c = database.connect();
			Statement stmt = c.createStatement(
				    ResultSet.TYPE_SCROLL_INSENSITIVE,
				    ResultSet.CONCUR_READ_ONLY
				);
			ResultSet result = stmt.executeQuery(sql);
			String output = "";
			while(result.next())
			{
				output += "Sender:\t\t" + result.getString(1) + "\n";
				output += "Recipient:\t " + result.getString(2) + "\n";
				output += "Date:\t" + result.getString(4) + "\n\n";
				output += "Message:\t" + result.getString(3) + "\n\n";
				output += "---------------------------------------------------------\n";
				
			}
			recentMessages.setText(output);
			
			
			
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
