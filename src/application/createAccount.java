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

public class createAccount {
	
	@FXML
	private Button backToLogin;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField dateOfBirth;
	@FXML
	private TextField email;
	@FXML
	private TextField phoneNum;
	@FXML
	private TextField contactPhoneNum;
	@FXML
	private TextField insuranceInfo;
	@FXML
	private TextField pharmInfo;
	@FXML
	private TextField previousHealthIssues;
	@FXML
	private TextField prevCurrMed;
	@FXML
	private TextField historyOfImm;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Button confirm;
	
	//data
	private String firstNameData;
	private String lastNameData;
	private String dateOfBirthData;
	private String emailData;
	private String phoneNumData;
	private String contactPhoneNumData;
	private String insuranceInfoData;
	private String pharmInfoData;
	private String previousHealthIssuesData;
	private String prevCurrMedData;
	private String historyOfImmData;
	private String usernameData;
	private String passwordData;
	
	public void userBackToLogin(ActionEvent event) {
		//checkLogin();
		Main m = new Main();
		m.changeScene("login.fxml");
	}
	
	public void userConfirmEntry(ActionEvent event) {
		//Main m = new Main();
		
		String stringBlock = "";
		firstNameData = firstName.getText().toString(); stringBlock += firstNameData + "\n";
		lastNameData = lastName.getText().toString();
		dateOfBirthData = dateOfBirth.getText().toString();
		emailData = email.getText().toString();
		phoneNumData = phoneNum.getText().toString();
		contactPhoneNumData = contactPhoneNum.getText().toString();
		insuranceInfoData = insuranceInfo.getText().toString();
		pharmInfoData = pharmInfo.getText().toString();
		previousHealthIssuesData = previousHealthIssues.getText().toString();
		prevCurrMedData = prevCurrMed.getText().toString();
		historyOfImmData = historyOfImm.getText().toString();
		usernameData = username.getText().toString();
		passwordData = password.getText().toString();
		
		
	}
	
	public void saveEntry()
	{
		try {
			Connection c = DriverManager.getConnection(Main.url, Main.username, Main.password);
			Statement stmt = c.createStatement();
			
			// make sure the username is unique 
			String sql1 = "INSERT INTO patient(medicalEntries) WHERE username = '"+ this.usernameData +"';";
			// todo
				
		} catch (SQLException e) {	
			System.out.println("Error in connecting to postgreSQL server.");
			e.printStackTrace();
		}
	}

}




