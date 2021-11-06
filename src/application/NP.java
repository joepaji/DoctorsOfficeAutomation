package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;



public class NP {
	
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
	
	public NP() {
		
	}
	

	public void userBackToLogin(ActionEvent event) {
		//checkLogin();
		Main m = new Main();
		m.changeScene("login.fxml");
	}
	
	

}