package hr.go2.play.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import hr.go2.play.entities.Location;
import hr.go2.play.entities.Role;
import hr.go2.play.entities.User;
import hr.go2.play.repositories.RoleRepository;
import hr.go2.play.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceTests {
	
	@Autowired
	private RoleServiceImpl roleService;
	@Autowired
	private UserServiceImpl userService;

	private Role role = new Role();
	private Location location = new Location();
	private User user = new User();
	private List<User> users = new ArrayList<>();
	private List<Location> locations = new ArrayList<>();
	private List<Role> roles = new ArrayList<>();
	
	@Before
	public void init() {
		location.setAddress("Zagreb, Smiciklasova 12");
		location.setName("Rudes");
		locations.add(location);

		user.setCreatedAt(new Date());
		user.setEnabled(true);
		user.setUsername("userRol");
		user.setPassword("1234");
		user.setDateOfBirth(new Date());
		users.add(user);

		role.setName("admin");
		role.setLocations(locations);
		role.setUsers(users);
		roles.add(role);

		role.setId(roleService.saveRole(role).getId());

		user.setRoles(roles);
		userService.saveUser(user);
	}

	@Test
	@Order(1)
	public void findByName() {
		List<Role> roleTest = (List<Role>) roleService.findRoleByName(role.getName());
		assertThat(roleTest.get(0).getId()).isEqualTo(role.getId());
	}

	@Test
	@Order(2)
	public void findByLocations_Name() {
		Role roleTest = roleService.findRoleByLocationsName(location.getName());
		assertThat(roleTest.getId()).isEqualTo(role.getId());
	}

	@Test
	@Order(3)
	public void findByLocations_Address() {
		Role roleTest = roleService.findRoleByLocationsAddress(location.getAddress());
		assertThat(roleTest.getId()).isEqualTo(role.getId());
	}

	@Test
	@Order(4)
	public void findByUsers_Username() {
		List<Role> roleTest = (List<Role>) roleService.findRoleByUsersUsername(user.getUsername());
		assertThat(roleTest.get(0).getId()).isEqualTo(role.getId());
	}

	@Test
	@Order(5)
	public void findByNameAndLocationsName() {
		Role roleTest = roleService.findRoleByNameAndLocationsName(role.getName(), location.getName());
		assertThat(roleTest.getId()).isEqualTo(role.getId());
	}

	@After
	public void deleteAll() {
		roleService.deleteAllRoles();
		userService.deleteAllUsers();
	}

}