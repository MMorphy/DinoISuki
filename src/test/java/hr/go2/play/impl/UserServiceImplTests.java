package hr.go2.play.impl;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTests {

//	@Autowired
//	private RoleServiceImpl roleService;
//	@Autowired
//	private UserServiceImpl userService;
//
//	private Role role = new Role();
//	private Location location = new Location();
//	private User user = new User();
//	private Term term = new Term();
//	private Sports sport = new Sports();
//	private List<User> users = new ArrayList<>();
//	private List<Location> locations = new ArrayList<>();
//	private List<Role> roles = new ArrayList<>();
//	private List<Term> reservedTerms = new ArrayList<>();
//	private List<Sports> likedSports = new ArrayList<>();
//
//	@Before
//	public void init() {
//		location.setAddress("Zagreb, Smiciklasova 12");
//		location.setName("Rudes");
//		locations.add(location);
//
//		user.setCreatedAt(new Date());
//		user.setEnabled(true);
//		user.setUsername("userRol");
//		user.setPassword("1234");
//		user.setDateOfBirth(new Date());
//		//reservedTerms.add(term);
//		//user.setReservedTerms(reservedTerms);
//		sport.setName("plivanje");
//		likedSports.add(sport);
//		user.setLikedSports(likedSports);
//		users.add(user);
//
//		role.setName("role_user");
//		//role.setLocations(locations);
//		role.setUsers(users);
//		roles.add(role);
//
//		role.setId(roleService.saveRole(role).getId());
//
//		user.setRoles(roles);
//		user.setId(userService.saveUser(user).getId());
//	}
//
//	@Test
//	@Order(1)
//	public void findByRoles_name() {
//		List<User> userTest = (List<User>) userService.findUsersByRoleName(role.getName());
//		assertThat(userTest.get(0).getId()).isEqualTo(user.getId());
//	}
//
//	@Test
//	@Order(2)
//	public void findByEnabled() {
//		List<User> userTest = (List<User>) userService.findUsersByEnabled(user.isEnabled());
//		assertThat(userTest.get(0).getId()).isEqualTo(user.getId());
//	}
//
////	@Test
////	@Order(3)
////	public void findByReservedTerms() {
////		List<User> userTest = (List<User>) userService.findUsersByReservedTerms(term);
////		assertThat(userTest.get(0).getId()).isEqualTo(user.getId());
////	}
//
//	@Test
//	@Order(3)
//	public void findByLikedSports_Name() {
//		List<User> userTest = (List<User>) userService.findUsersByLikedSportsName(sport.getName());
//		assertThat(userTest.get(0).getId()).isEqualTo(user.getId());
//	}
//
//	@Test
//	@Order(4)
//	public void findByUsername() {
//		User userTest = userService.findUserByUsername(user.getUsername());
//		assertThat(userTest.getId()).isEqualTo(user.getId());
//	}
//
//	@After
//	public void deleteAll() {
//		userService.deleteAllUsers();
//		roleService.deleteAllRoles();
//	}

}
