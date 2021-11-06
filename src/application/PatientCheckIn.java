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

public class PatientCheckIn{
	
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
	private TextArea medications;
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
	private String medicationData;
	private String allergiesData;
	private String notesData;
	
	
	public PatientCheckIn()
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
	
	public void toHome(ActionEvent event) throws IOException
	{
		destination = "login.fxml"; 
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
		medicationData = temperature.getText().toString();
		allergiesData = height.getText().toString();
		notesData = weight.getText().toString();
		
		
		// test
		System.out.println(heightData);
		System.out.println(weightData);
		
	}
	
}
