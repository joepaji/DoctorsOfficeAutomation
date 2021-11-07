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
	@FXML
	private TextArea patientInfo;
	@FXML
	private TextArea patientHistory;
	
	
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
	
	public void displayInfo() {
		DoctorNurseActions action = new DoctorNurseActions(username);
		String[] patientDetails = action.getPatientInfo();
		patientInfo.setText(patientDetails[0]);
		patientHistory.setText(patientDetails[1]);
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
		bpData = bloodPressure.getText().toString();
		healthConcernsData = healthConcerns.getText().toString();
		allergiesData = allergies.getText().toString();
		notesData = notes.getText().toString();

		saveEntry();
		// May change destination later
		toHome(event);
	}
	
	public void saveEntry() {
		LocalDateTime dateTime = LocalDateTime.now();
		try {
			Database db = new Database();
			Connection c = db.connect();
				
			// Insert into table
			String sql = "INSERT into visits(height, weight, temperature, bp,"
					+ "health_concerns, allergies, nurse_notes, date_time, username)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
				
			PreparedStatement prepStatement = c.prepareStatement(sql);
			
			prepStatement.setString(1, heightData);
			prepStatement.setString(2, weightData);
			prepStatement.setString(3, temperatureData);
			prepStatement.setString(4, bpData);
			prepStatement.setString(5, healthConcernsData);
			prepStatement.setString(6, allergiesData);
			prepStatement.setString(7, notesData);
			prepStatement.setObject(8, dateTime);
			prepStatement.setString(9, username);
			
			prepStatement.executeUpdate();
			
			c.close();
		} catch (SQLException e) {	
			System.out.println("Error in adding record. Check that all required values are filled out.");
			printSQLException(e);
		}
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
