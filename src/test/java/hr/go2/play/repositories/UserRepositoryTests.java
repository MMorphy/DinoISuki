package hr.go2.play.repositories;

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
import hr.go2.play.entities.Sports;
import hr.go2.play.entities.Term;
import hr.go2.play.entities.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {
	
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	
	private Role role = new Role();
	private Location location = new Location();
	private User user = new User();
	private Term term = new Term();
	private Sports sport = new Sports();
	private List<User> users = new ArrayList<>();
	private List<Location> locations = new ArrayList<>();
	private List<Role> roles = new ArrayList<>();
	private List<Term> reservedTerms = new ArrayList<>();
	private List<Sports> likedSports = new ArrayList<>();
	
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
		reservedTerms.add(term);
		user.setReservedTerms(reservedTerms);
		sport.setName("plivanje");
		likedSports.add(sport);
		user.setLikedSports(likedSports);
		users.add(user);
		
		role.setName("admin");
		role.setLocations(locations);
		role.setUsers(users);
		roles.add(role);
		
		role.setId(roleRepository.save(role).getId());
		
		user.setRoles(roles);
		user.setId(userRepository.save(user).getId());
	}
	
	@Test
	@Order(1)
	public void findByRoles_name() {
		List<User> userTest = (List<User>) userRepository.findByRoles_name(role.getName());
		assertThat(userTest.get(0).getId()).isEqualTo(user.getId());
	}
	
	@Test
	@Order(2)
	public void findByEnabled() {
		List<User> userTest = (List<User>) userRepository.findByEnabled(user.isEnabled());
		assertThat(userTest.get(0).getId()).isEqualTo(user.getId());
	}
	
	@Test
	@Order(3)
	public void findByReservedTerms() {
		List<User> userTest = (List<User>) userRepository.findByReservedTerms(term);
		assertThat(userTest.get(0).getId()).isEqualTo(user.getId());
	}
	
	@Test
	@Order(4)
	public void findByLikedSports_Name() {
		List<User> userTest = (List<User>) userRepository.findByLikedSports_Name(sport.getName());
		assertThat(userTest.get(0).getId()).isEqualTo(user.getId());
	}
	
	@Test
	@Order(5)
	public void findByUsername() {
		Optional<User> userTest = userRepository.findByUsername(user.getUsername());
		assertThat(userTest.isPresent()).isTrue();
		assertThat(userTest.get().getId()).isEqualTo(user.getId());
	}
	
	@After
	public void deleteAll() {
		roleRepository.deleteAll();
		userRepository.deleteAll();
	}

}
