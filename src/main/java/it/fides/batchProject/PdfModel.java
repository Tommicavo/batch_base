package it.fides.batchProject;

public class PdfModel {
	
	private String firstName;
	private String lastName;
	private String email;
	
	public PdfModel() {}

	private String getTitle() {
		return "SCHEDA VALUTAZIONE - " + getFirstName() + getLastName();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return
				getTitle() + "\n" +
				"FirstName: " + getFirstName() + "\n" +
				"LastName: " + getLastName() + "\n" +
				"Email: " + getEmail() + "\n";
	}	
}
