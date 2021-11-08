package application;

public class Doctor extends UserAccount {

	public Doctor(String username, String password, String firstName, String lastName, String birthDate) {
		super(username, password, firstName, lastName, birthDate);
		// TODO Auto-generated constructor stub
		this.setUserType(0);
	}
	
	// add prescription to patient in database
	public void createPrescription() {
		
	}
	
	// replace current prescription
	public void changePrescription() {
		
	}
}
