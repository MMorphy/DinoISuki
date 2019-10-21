package hr.go2.play.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@ManyToMany(mappedBy = "roles", cascade = { CascadeType.MERGE })
	private Collection<User> users;

	@OneToMany(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "role_id", nullable = true)
	private Collection<Location> locations;

	public Role() {

	}

	public Role(Long id, String name, Collection<User> users, Collection<Location> locations) {
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

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public boolean addUser(User user) {
		return this.users.add(user);
	}

	public Collection<Location> getLocations() {
		return locations;
	}

	public void setLocations(Collection<Location> locations) {
		this.locations = locations;
	}

	public boolean addLocation(Location location) {
		return this.locations.add(location);
	}

}
