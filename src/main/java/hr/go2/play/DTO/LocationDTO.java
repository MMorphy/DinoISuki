package hr.go2.play.DTO;

import java.util.ArrayList;
import java.util.List;

public class LocationDTO {

	private Long id;

	private WorkingHoursDTO hours;

	private List<FieldDTO> fields;

	private String address;

	private String name;

	private UserDTO contactUser;

	public LocationDTO() {
		this.fields = new ArrayList<>();
	}

	public LocationDTO(Long id, WorkingHoursDTO hours, List<FieldDTO> fields, String address, String name,
			UserDTO contactUser) {
		super();
		this.id = id;
		this.hours = hours;
		this.fields = fields;
		this.address = address;
		this.name = name;
		this.contactUser = contactUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WorkingHoursDTO getHours() {
		return hours;
	}

	public void setHours(WorkingHoursDTO hours) {
		this.hours = hours;
	}

	public List<FieldDTO> getFields() {
		return fields;
	}

	public void setFields(List<FieldDTO> fields) {
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

	public UserDTO getContactUser() {
		return contactUser;
	}

	public void setContactUser(UserDTO contactUser) {
		this.contactUser = contactUser;
	}
}
