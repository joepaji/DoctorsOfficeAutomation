package application;

public class Nurse extends UserAccount {

	public Nurse(String username, String password, String firstName, String lastName, String birthDate, int userType) {
		super(username, password, firstName, lastName, birthDate);
		// TODO Auto-generated constructor stub
		this.setUserType(1);
	}
	
	public void createPatientExam() {
		
	}
	
	public void createPatientAccount(String username, String password, String firstName, 
			String lastName, String birthDate, int userType) {
		UserAccount account = new UserAccount(username, password,firstName, lastName, birthDate);
		createAccount(account);
	}

}
