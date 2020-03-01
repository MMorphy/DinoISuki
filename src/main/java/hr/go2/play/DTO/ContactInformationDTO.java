package hr.go2.play.DTO;

public class ContactInformationDTO {

	private String username;

	private String telephoneNumber;

	private String email;

	public ContactInformationDTO() {
	}

	public ContactInformationDTO(String username, String telephoneNumber, String email) {
		this.username = username;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
