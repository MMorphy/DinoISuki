package hr.go2.play.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RoleDTO {

	private Long id;

	private String name;

	@JsonIgnore
	private List<UserDTO> users;

	private List<LocationDTO> locations;

	public RoleDTO() {
		this.users = new ArrayList<>();
		this.locations = new ArrayList<>();
	}
	
	public RoleDTO(Long id, String name, List<UserDTO> users, List<LocationDTO> locations) {
		super();
		this.id = id;
		this.name = name;
		this.users = users;
		this.locations = locations;
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

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public List<LocationDTO> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationDTO> locations) {
		this.locations = locations;
	}
}
