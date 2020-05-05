package hr.go2.play.DTO;

import java.util.List;

public class LocationWithWorkingHoursDTO {

	private Long id;
	private String name;
	private String address;
	private List<WorkingHoursDTO> workingHours;
	private String contactTel;
	private String contactEmail;

	public LocationWithWorkingHoursDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<WorkingHoursDTO> getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(List<WorkingHoursDTO> workingHours) {
		this.workingHours = workingHours;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

}
