package hr.go2.play.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="contact_informations")
public class ContactInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//TODO Dodaj validatore
	@Column(name = "telephone_number")
	private String telephoneNumber;

	//TODO Dodaj validatore
	@Column(name = "email", unique=true, nullable = false)
	private String email;

	public ContactInformation() {}

	public ContactInformation(Long id, String telephoneNumber, String email) {
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
