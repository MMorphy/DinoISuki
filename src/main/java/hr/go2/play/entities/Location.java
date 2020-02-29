package hr.go2.play.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "locations")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "location_working_hours", joinColumns = @JoinColumn(name = "location_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "working_hours_id", referencedColumnName = "id"))
	private Collection<WorkingHours> hours;

	@OneToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinColumn(name = "location_id")
	private Collection<Field> fields;

	@Column(nullable = false)
	private String address;

	@Column(nullable=false)
	private String name;

	@OneToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "contact_info_id")
	private ContactInformation contactInformation;

	public Location() {

	}

	public Location(Long id, Collection<WorkingHours> hours, Collection<Field> fields, String address, String name,
			ContactInformation contactInformation) {
		super();
		this.id = id;
		this.hours = hours;
		this.fields = fields;
		this.address = address;
		this.name = name;
		this.contactInformation = contactInformation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<WorkingHours> getHours() {
		return hours;
	}

	public void setHours(Collection<WorkingHours> hours) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ContactInformation getContactInformation() {
		return contactInformation;
	}

	public void setContactInformation(ContactInformation contactInformation) {
		this.contactInformation = contactInformation;
	}
}
