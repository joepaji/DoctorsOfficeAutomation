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
	private String doctorUsername;
	private String patientUsername;
	
	public PatientExamController() {
		
	}
	
	public void displayPrevCheckIn() {
		DoctorNurseActions action = new DoctorNurseActions(doctorUsername);
		prevCheckIn.setText(action.getLatestCheckin());
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
		destination = "Messenger.fxml";
		FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
		root = loader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
		scene = new Scene(root);
		stage.setScene(scene);
		MessengerController messengerController = loader.getController();
		messengerController.setUsername(doctorUsername);
		messengerController.setSelfFirstLast();
		messengerController.displayMessages();
		stage.show();
	}
	
	public void toHome(ActionEvent event) throws IOException
	{
		destination = "DoctorPortal.fxml";   
		FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
		root = loader.load();
		DoctorPortalController dpc = loader.getController();
		dpc.setUsername(doctorUsername);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow(); 
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
		try {
			String notes=inputNotes.getText().toString();
			String medications=inputPrescription.getText().toString();
			String date_time="";
			Database db = new Database();
			Connection c = db.connect();
			String sql= "SELECT date_time FROM visits ORDER BY date_time DESC";
			Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet result = stmt.executeQuery(sql);
			if(result.first()) {
				date_time = result.getString("date_time");
			}
			sql="UPDATE visits SET doctor_notes='"+notes+"',medications='"+medications+"'WHERE date_time='"+date_time+"'";
			PreparedStatement prepStatement = c.prepareStatement(sql);
			prepStatement.executeUpdate();
			sql="UPDATE patient SET curr_med='"+medications+"' WHERE username='"+ patientUsername+"'";
			prepStatement = c.prepareStatement(sql);
			prepStatement.executeUpdate();
			c.close();
		} catch (SQLException e) {	
			System.out.println("Error in adding record. Check that all required values are filled out.");
			e.printStackTrace();
		}
	}
	
	
	public void setUsername(String username) {
		this.doctorUsername = username;
	}
	
	public void setPatientUsername(String patientUsername) {
		this.patientUsername = patientUsername;
	}
	
	public static boolean isFieldEmpty(String... strings) {
		for(String s : strings) 
			if(s == null || s.isEmpty()) 
				return true;
				
		return false;
	}
}
