package hr.go2.play.repositories;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationRepositoryTests {

//	@Autowired
//	private LocationRepository locationRepository;
//
//	private Location location = new Location();
//	private User user = new User();
//	private WorkingHours wh = new WorkingHours();
//	private Sports sport = new Sports();
//	private Field field = new Field();
//	private List<Field> fields = new ArrayList<>();
//
//	@Before
//	public void init() {
//		sport.setName("tenis");
//		field.setSport(sport);
//		fields.add(field);
//
//		user.setCreatedAt(new Date());
//		user.setEnabled(true);
//		user.setUsername("userLoc");
//		user.setPassword("123");
//		user.setDateOfBirth(new Date());
//
//		wh.setFromTime(new Date());
//		wh.setToTime(new Date());
//
//		location.setAddress("Zagreb, Krapinska 12");
//		location.setContactUser(user);
//		location.setHours(wh);
//		location.setFields(fields);
//		location.setName("Rudes");
//
//		location.setId(locationRepository.save(location).getId());
//	}
//
//	@Test
//	@Order(1)
//	public void findByAddress() {
//		Optional<Location> locationTest = locationRepository.findByAddress(location.getAddress());
//		assertThat(locationTest.isPresent()).isTrue();
//		assertThat(locationTest.get().getId()).isEqualTo(location.getId());
//	}
//
//	@Test
//	@Order(2)
//	public void findByAddressLike() {
//		Optional<Location> locationTest = locationRepository.findByAddressLike("%Zagreb%");
//		assertThat(locationTest.isPresent()).isTrue();
//		assertThat(locationTest.get().getId()).isEqualTo(location.getId());
//	}
//
//	@Test
//	@Order(3)
//	public void findByContactUser() {
//		Optional<Location> locationTest = locationRepository.findByContactUser(user);
//		assertThat(locationTest.isPresent()).isTrue();
//		assertThat(locationTest.get().getId()).isEqualTo(location.getId());
//	}
//
//	@Test
//	@Order(4)
//	public void findByHours() {
//		List<Location> locationTest = (List<Location>) locationRepository.findByHours(wh);
//		assertThat(locationTest.get(0).getId()).isEqualTo(location.getId());
//	}
//
//	@Test
//	@Order(5)
//	public void findByFields_Sport_Name() {
//		Optional<Location> locationTest = locationRepository.findByFields_Sport_Name(sport.getName());
//		assertThat(locationTest.isPresent()).isTrue();
//		assertThat(locationTest.get().getId()).isEqualTo(location.getId());
//	}
//
//	@Test
//	@Order(6)
//	public void findByName() {
//		Optional<Location> locationTest = locationRepository.findByName("Rudes");
//		assertThat(locationTest.isPresent()).isTrue();
//		assertThat(locationTest.get().getName()).isEqualTo(location.getName());
//	}
//
//	@After
//	public void deleteAll() {
//		locationRepository.deleteAll();
//	}
}
