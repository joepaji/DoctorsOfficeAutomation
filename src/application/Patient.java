package application;

public class Patient extends UserAccount {
	
	private String pharmacy;
	private String email = "";
	private String phone = "";
	private float height = 0;
	private float weight = 0;
	
	public Patient(String username, String password, String firstName, String lastName, String birthDate, String pharmacy) {
		super(username, password, firstName, lastName, birthDate);
		
		this.pharmacy = pharmacy;
		this.setUserType(2);
	}
	
	// Displays past visit info
	public void displayVisits() {
		
	}
	
	// Display patient exam object
	public void displayPatientExam() {
		
	}
	
	// Getters
	public String getPharmacy() {
		return pharmacy;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getWeight() {
		return weight;
	}
	// Setters 
	public void setPharmacy(String pharmacy){
		this.pharmacy = pharmacy;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
	}
}
