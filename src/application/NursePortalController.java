package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;

public class NursePortalController{
	
	@FXML
	private Button signOut;
	@FXML
	private Button toMessages;
	@FXML
	private Button toHome;
	@FXML
	private Button checkIn;
	@FXML
	private Button search;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField dateOfBirth;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private String destination;
	private String username;
	
	public NursePortalController()
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
	}
	
	public void checkIn(ActionEvent event) throws IOException
	{
		//TODO check if username is selected (should be assigned from search function)
		System.out.println("checkin");
		destination = "PatientCheckIn.fxml";    //testing
		FXMLLoader loader = new FXMLLoader(getClass().getResource(destination));
		root = loader.load();
		//This passes the username of the patient to the patientCheckIn controller
		PatientCheckIn patientCheckIn = loader.getController();
		patientCheckIn.setUsername(username);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();  // assigns the stage to the currently running stage from main
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
}
