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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

public class PatientCheckIn implements Initializable{
	
	@FXML
	private Button signOut;
	@FXML
	private Button messages;
	@FXML
	private Button home;
	@FXML
	private Button confirmEntry;
	@FXML
	private TextField height;
	@FXML
	private TextField weight;
	@FXML
	private TextField temperature;
	@FXML
	private TextField bloodPressure;
	@FXML
	private TextArea healthConcerns;

	@FXML
	private TextArea allergies;
	@FXML
	private TextArea notes;
	
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String destination;
	private String heightData;
	private String weightData;
	private String temperatureData;
	private String bpData;
	private String healthConcernsData;
	private String allergiesData;
	private String notesData;
	private String username;
	
	
	public PatientCheckIn()
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
	
	public void toMessages(ActionEvent event) throws IOException
	{
		//destination = "Messages.fxml";
		destination = "login.fxml";    //testing
		root = FXMLLoader.load(getClass().getResource(destination));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();  // assigns the stage to the currently running stage from main
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void toHome(ActionEvent event) throws IOException
	{
		destination = "NursePortal.fxml"; 
		root = FXMLLoader.load(getClass().getResource(destination));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();  // assigns the stage to the currently running stage from main
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void confirmEntry(ActionEvent event) throws IOException
	{
		heightData = height.getText().toString();
		weightData = weight.getText().toString();
		temperatureData = temperature.getText().toString();
		bpData = height.getText().toString();
		healthConcernsData = weight.getText().toString();
		allergiesData = height.getText().toString();
		notesData = weight.getText().toString();
		
		
		// test
		System.out.println(heightData);
		System.out.println(weightData);
		saveEntry();
		
	}
	
	public void saveEntry() {
		LocalDateTime dateTime = LocalDateTime.now();
		System.out.println("User is: " + username);
//		try {
//			Database db = new Database();
//			Connection c = db.connect();
//			Statement stmt = c.createStatement();
//				
//			// Insert into table
//			String sql = "INSERT into visits(height, weight,"
//					+ "temperature, bp, health_concerns, medications, allergies, nurse_notes,dateTime"; 
//				
//			PreparedStatement prepStatement = c.prepareStatement(sql);
//			
//			prepStatement.setString(1, heightData);
//			
//			
//		} catch (SQLException e) {	
//			System.out.println("Error in adding record. Check that all required values are filled out.");
//			printSQLException(e);
//		}
	}
	
	public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
	
	public void setUsername(String username) {
		this.username = username;
	}
}
