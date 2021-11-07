package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private TextField email;
	@FXML
	private TextField dateOfBirth;
	@FXML
	private TextField phoneNum;
//	@FXML
//	private TextField contactPhoneNum;
	@FXML
	private TextArea insuranceInfo;
	@FXML
	private TextArea pharmInfo;
	@FXML
	private TextArea previousHealthIssues;
	@FXML
	private TextArea prevCurrMed;
	@FXML
	private TextArea historyOfImm;

	@FXML
	private Button confirm;
	@FXML
	private Label error;
	
	//data
	private String firstNameData;
	private String lastNameData;
	private String dateOfBirthData;
	private String emailData;
	private String phoneNumData;
//	private String contactPhoneNumData;
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
		
		firstNameData = firstName.getText().toString();
		lastNameData = lastName.getText().toString();
		dateOfBirthData = dateOfBirth.getText().toString();
		emailData = email.getText().toString();
		phoneNumData = phoneNum.getText().toString();
//		contactPhoneNumData = contactPhoneNum.getText().toString();
		insuranceInfoData = insuranceInfo.getText().toString();
		pharmInfoData = pharmInfo.getText().toString();
		previousHealthIssuesData = previousHealthIssues.getText().toString();
		prevCurrMedData = prevCurrMed.getText().toString();
		historyOfImmData = historyOfImm.getText().toString();
		usernameData = username.getText().toString();
		passwordData = password.getText().toString();
		
		if(isFieldEmpty(firstNameData, lastNameData, dateOfBirthData, usernameData, passwordData)) 
			error.setText("Oops, you missed some required fields");
		
		else {
			error.setText("Account has been created.");
			saveEntry();
		}
			
	}
	
	public void saveEntry()
	{
		try {
			Database db = new Database();
			Connection c = db.connect();
			Statement stmt = c.createStatement();
			
			// Check if username already exists
			String sql = "SELECT * from patient WHERE username = '" + usernameData + "'";
			
			ResultSet result = stmt.executeQuery(sql);
			
			if(result.isBeforeFirst()) {
				error.setText("Oops, that username already exists");
			}
			else {
				// Insert into table
				sql = "INSERT into patient(first_name, last_name,"
						+ "username, password, email, phone, dob, insurance, pharmacy, "
						+ "health_issues, curr_med, immunization, usertype) "
						+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"; 
				
				PreparedStatement prepStatement = c.prepareStatement(sql);
				
				prepStatement.setString(1,firstNameData);
				prepStatement.setString(2,lastNameData);
				prepStatement.setString(3,usernameData);
				prepStatement.setString(4,passwordData);
				prepStatement.setString(5,emailData);
				prepStatement.setString(6,phoneNumData);
				prepStatement.setString(7,dateOfBirthData);
				prepStatement.setString(8,insuranceInfoData);
				prepStatement.setString(9,pharmInfoData);
				prepStatement.setString(10,previousHealthIssuesData);
				prepStatement.setString(11,prevCurrMedData);
				prepStatement.setString(12,historyOfImmData);
				//Usertype is 1 for patient
				prepStatement.setInt(13, 1);
				
				prepStatement.executeUpdate();
			}
			
			c.close();
			
			
		} catch (SQLException e) {	
			System.out.println("Error in adding user. Check that all required values are filled out.");
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
	
	public static boolean isFieldEmpty(String... strings) {
		for(String s : strings) 
			if(s == null || s.isEmpty()) 
				return true;
				
		return false;
	}

}




