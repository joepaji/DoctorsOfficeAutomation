package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;



public class Login {
	
	@FXML
	private Button login;
	@FXML
	private Label wrongLogin;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Button createAccount;
	
	//data
	private String usernameData;
	private String passwordData;
	
	public Login() {
		
	}
	
	public void userLogin(ActionEvent event) {
		usernameData = username.getText().toString();
		passwordData = password.getText().toString();
		//Main m = new Main();
		//m.changeScene("");
	}
	
	public void userCreateAccount(ActionEvent event) {
		//checkLogin();
		Main m = new Main();
		m.changeScene("createAccount.fxml");
	}
	
	

}
