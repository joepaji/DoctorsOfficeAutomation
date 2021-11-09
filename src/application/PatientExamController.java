package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class PatientExamController {

	@FXML
	private Button signOut;
	@FXML
	private Button toMessages;
	@FXML
	private Button toHome;
	@FXML
	private Button saveInputs;
	@FXML
	private TextArea prevCheckIn;
	@FXML
	private TextArea inputPrescription;
	@FXML
	private TextArea inputNotes;
	@FXML
	private Label checkinError;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String destination;
	private String prescriptionsData;
	private String notesData;
	private String username;
	
	public PatientExamController() {
		
	}
	
	public void displayPrevCheckIn() {
		DoctorNurseActions action = new DoctorNurseActions(username);
		String patientDetails = action.getLatestCheckin();
		prevCheckIn.setText(patientDetails);
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
		destination = "DoctorsPortal.fxml"; 
		root = FXMLLoader.load(getClass().getResource(destination));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();  // assigns the stage to the currently running stage from main
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void saveInputs(ActionEvent event) throws IOException{
		prescriptionsData=inputPrescription.getText().toString();
		notesData=inputNotes.getText().toString();
		
		if(isFieldEmpty(prescriptionsData,notesData))
			checkinError.setText("Oops, you missed some required fields.");
		
		else{
			saveEntry();
			// May change destination later
			toHome(event);
		}
	}
	
	public void saveEntry() {
		
	}
	
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public static boolean isFieldEmpty(String... strings) {
		for(String s : strings) 
			if(s == null || s.isEmpty()) 
				return true;
				
		return false;
	}
}
