package it.fides.batchProject;

public class PdfModel {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	
	public PdfModel() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	private String getTitle() {
		return "SCHEDA VALUTAZIONE - " + getFirstName().toUpperCase() + " " + getLastName().toUpperCase();
	}

	@Override
	public String toString() {
		return
				getTitle() + "\n" +
				"Id: " + getId() + "\n" +
				"FirstName: " + getFirstName() + "\n" +
				"LastName: " + getLastName() + "\n" +
				"Email: " + getEmail() + "\n";
	}	
}
