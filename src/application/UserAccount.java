package application;

public class UserAccount {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String birthDate;
	// determines if doctor (0), nurse (1) or patient (2)
	private int userType; 
	
	// UserAccount constructor
	public UserAccount(String username, String password, String firstName, String lastName, 
			String birthDate) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;

	}
	
	// Returns this UserAccount object
	// Not sure if we'll need this, we can't really return this account object without creating a 
	// new account object(call constructor). plus why would we do that?
	
	public void getAccount() {
	
	}
	
	// Adds account to database
	public void createAccount(UserAccount account) {
		
	}
	
	// Creates a message object
	// Might not need this method if we add send and view methods in Messenger.java
	public void createMessenger() {
		
	}

	
	// Getters
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getBirthDate() {
		return birthDate;
	}
	
	public int getUserType() {
		return userType;
	}
	
	// Setters for modifiable attributes
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	public void setUserType(int userType) {
		this.userType = userType;
	}
}
