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

import hr.go2.play.entities.Field;
import hr.go2.play.entities.Location;
import hr.go2.play.entities.Sports;
import hr.go2.play.entities.User;
import hr.go2.play.entities.WorkingHours;
import hr.go2.play.repositories.LocationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationServiceImplTests {
	
	@Autowired
	private LocationServiceImpl locationService;

	private Location location = new Location();
	private User user = new User();
	private WorkingHours wh = new WorkingHours();
	private Sports sport = new Sports();
	private Field field = new Field();
	private List<Field> fields = new ArrayList<>();

	@Before
	public void init() {
		sport.setName("tenis");
		field.setSport(sport);
		fields.add(field);
		
		user.setCreatedAt(new Date());
		user.setEnabled(true);
		user.setUsername("userLoc");
		user.setPassword("123");
		user.setDateOfBirth(new Date());
		
		wh.setFromTime(new Date());
		wh.setToTime(new Date());
		
		location.setAddress("Zagreb, Krapinska 12");
		location.setContactUser(user);
		location.setHours(wh);
		location.setFields(fields);
		location.setName("Rudes");
		
		location.setId(locationService.saveLocation(location).getId());
	}
	
	@Test
	@Order(1)
	public void findByAddress() {
		Location locationTest = locationService.findLocationByAddress(location.getAddress());
		assertThat(locationTest.getId()).isEqualTo(location.getId());
	}
	
	@Test
	@Order(2)
	public void findByAddressLike() {
		Location locationTest = locationService.findLocationByAddressLike("%Zagreb%");
		assertThat(locationTest.getId()).isEqualTo(location.getId());
	}
	
	@Test
	@Order(3)
	public void findByContactUser() {
		Location locationTest = locationService.findLocationByContactUser(user);
		assertThat(locationTest.getId()).isEqualTo(location.getId());
	}
	
	@Test
	@Order(4)
	public void findByHours() {
		List<Location> locationTest = (List<Location>) locationService.findLocationByWorkingHours(wh);
		assertThat(locationTest.get(0).getId()).isEqualTo(location.getId());
	}
	
	@Test
	@Order(5)
	public void findByFields_Sport_Name() {
		Location locationTest = locationService.findLocationByFieldsSportName(sport.getName());
		assertThat(locationTest.getId()).isEqualTo(location.getId());
	}
	
	@Test
	@Order(6)
	public void updateContactInformation() {
		location.setAddress("Sisak, Moslavacka 1");
		
		locationService.updateLocation(location.getId(), location);
		assertThat(locationService.findLocationById(location.getId()).getAddress()).isEqualTo(location.getAddress());
	}
	
	@After
	public void deleteAll() {
		locationService.deleteAllLocations();
	}

}
