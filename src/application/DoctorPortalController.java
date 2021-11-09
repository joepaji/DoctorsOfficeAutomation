package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;

public class DoctorPortalController{
	
	@FXML
	private Button signOut;
	@FXML
	private Button toMessages;
	@FXML
	private Button toHome;
	@FXML
	private Button beginExam;
	@FXML
	private Button search;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField dateOfBirth;
	@FXML
	private Label searchError;
	@FXML
	private Label checkinError;
	@FXML
	private TextArea prevCheckin;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String destination;
	private String username;
	
	public DoctorPortalController()
	{
		
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
	
	public void search(ActionEvent event) throws IOException
	{
		System.out.println("search");
		try {
			Database db = new Database();
			Connection c = db.connect();
			Statement stmt = c.createStatement();
			
			// search the database for shopping_cart with matching userID
			String sql = "SELECT * FROM patient WHERE (first_name = '" + firstName.getText().toString() +
												  "' AND last_name = '" + lastName.getText().toString() + "'"
												  		+ "AND dob = '" + dateOfBirth.getText().toString()+"');";	
			ResultSet rs = stmt.executeQuery(sql); 
			
			if(rs.next()) {
				String first = rs.getString("first_name");
				String last = rs.getString("last_name"); 
				username = rs.getString("username");
				// ************** TODO
				System.out.println(first);
				System.out.println(last);
				searchError.setText("");
				displayLastCheckin();
			} else {
				searchError.setText("Patient not found.");
			}
				
		} catch (SQLException e) {	
			System.out.println("Error in connecting to postgreSQL server.");
			e.printStackTrace();
		}
	}
	
	public void beginExam(ActionEvent event) throws IOException
	{
		//TODO check if username is selected (should be assigned from search function).
		if(username == null || username == "") {
			checkinError.setText("Oops, no patients selected.");
		}else {
			System.out.println("begin exam");
			destination = "PatientExam.fxml";    //testing
			FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
			root = loader.load();
			
			PatientExamController patientExamController = loader.getController();
			patientExamController.setUsername(username);
			patientExamController.displayPrevCheckIn();
			
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();  // assigns the stage to the currently running stage from main
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
	
	public void displayLastCheckin() {
		DoctorNurseActions actions = new DoctorNurseActions(username);
		prevCheckin.setText(actions.getLatestCheckin());
	}
	
}
