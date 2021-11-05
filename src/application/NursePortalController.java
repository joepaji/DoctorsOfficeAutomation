package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class NursePortalController{
	
	@FXML
	private Button backToLogin;
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
	

	
	public NursePortalController()
	{
		
	}
	
	public void userBackToLogin(ActionEvent event) {
		Main m = new Main();
		m.changeScene("login.fxml");
	}
}
