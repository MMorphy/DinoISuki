package hr.go2.play.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import hr.go2.play.entities.User;
import hr.go2.play.entities.WorkingHours;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationServiceImplTests {

	@Autowired
	private LocationServiceImpl locationService;

	private Location location = new Location();
	private User user = new User();
	private WorkingHours wh = new WorkingHours();
	private Field field = new Field();
	private List<Field> fields = new ArrayList<>();

	@Before
	public void init() {
		fields.add(field);

		wh.setFromTime(new Date());
		wh.setToTime(new Date());

		location.setAddress("Zagreb, Krapinska 12");
		// location.setHours(wh);
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
	public void findByHours() {
		List<Location> locationTest = locationService.findLocationByWorkingHours(wh);
		assertThat(locationTest.get(0).getId()).isEqualTo(location.getId());
	}


	@Test
	@Order(4)
	public void updateContactInformation() {
		location.setAddress("Sisak, Moslavacka 1");

		locationService.updateLocation(location.getId(), location);
		assertThat(locationService.findLocationById(location.getId()).getAddress()).isEqualTo(location.getAddress());
	}

	@Test
	@Order(5)
	public void findByName() {
		Location locationTest = locationService.findLocationByName("Rudes");
		assertThat(locationTest.getName()).isEqualTo(location.getName());
	}

	@After
	public void deleteAll() {
		locationService.deleteAllLocations();
	}

}
