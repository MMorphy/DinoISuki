package hr.go2.play.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "locations")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "working_hours_id")
	private WorkingHours hours;

	@OneToMany()
	@JoinColumn(name = "location_id")
	private Collection<Field> fields;

	@Column(nullable = false)
	private String address;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contact_user_id")
	private User contactUser;

	public Location() {

	}

	public Location(Long id, WorkingHours hours, Collection<Field> fields, String address, User contactUser) {
		super();
		this.id = id;
		this.hours = hours;
		this.fields = fields;
		this.address = address;
		this.contactUser = contactUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WorkingHours getHours() {
		return hours;
	}

	public void setHours(WorkingHours hours) {
		this.hours = hours;
	}

	public Collection<Field> getFields() {
		return fields;
	}

	public void setFields(Collection<Field> fields) {
		this.fields = fields;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getContactUser() {
		return contactUser;
	}

	public void setContactUser(User contactUser) {
		this.contactUser = contactUser;
	}

}
