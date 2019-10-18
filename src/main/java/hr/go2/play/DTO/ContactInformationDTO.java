package hr.go2.play.DTO;

public class ContactInformationDTO {

	private Long id;

	private String telephoneNumber;

	private String email;

	public ContactInformationDTO() {
	}

	
	public ContactInformationDTO(Long id, String telephoneNumber, String email) {
		super();
		this.id = id;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
