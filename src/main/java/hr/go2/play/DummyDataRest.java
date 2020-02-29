package hr.go2.play;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hr.go2.play.entities.Camera;
import hr.go2.play.entities.ContactInformation;
import hr.go2.play.entities.Field;
import hr.go2.play.entities.Location;
import hr.go2.play.entities.Role;
import hr.go2.play.entities.Subscription;
import hr.go2.play.entities.SubscriptionType;
import hr.go2.play.entities.User;
import hr.go2.play.entities.Video;
import hr.go2.play.entities.WorkingHours;
import hr.go2.play.impl.CameraServiceImpl;
import hr.go2.play.impl.ContactInformationServiceImpl;
import hr.go2.play.impl.FieldServiceImpl;
import hr.go2.play.impl.LocationServiceImpl;
import hr.go2.play.impl.RoleServiceImpl;
import hr.go2.play.impl.SubscriptionServiceImpl;
import hr.go2.play.impl.SubscriptionTypeServiceImpl;
import hr.go2.play.impl.UserServiceImpl;
import hr.go2.play.impl.VideoServiceImpl;
import hr.go2.play.impl.WorkingHoursServiceImpl;

@RestController
@RequestMapping(path = "/dummy", produces = "application/json", method = { RequestMethod.GET, RequestMethod.PUT })
public class DummyDataRest {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private CameraServiceImpl cameraService;

	@Autowired
	private ContactInformationServiceImpl contactInfoService;

	@Autowired
	private FieldServiceImpl fieldService;

	@Autowired
	private VideoServiceImpl videoService;

	@Autowired
	private RoleServiceImpl roleService;

	@Autowired
	private LocationServiceImpl locationService;

	@Autowired
	private WorkingHoursServiceImpl whService;

	@Autowired
	private SubscriptionServiceImpl subService;

	@Autowired
	private SubscriptionTypeServiceImpl subTypeService;

	@PostMapping("/")
	public void populateDB() {

		createContactInformations();
		createUsers();
		createWorkingHours();
		createSubscriptions();
	}

	private void createSubscriptions() {
		SubscriptionType subType1 = new SubscriptionType();
		subType1.setName("tip1");
		subTypeService.saveSubscriptionType(subType1);

		SubscriptionType subType2 = new SubscriptionType();
		subType2.setName("tip2");
		subTypeService.saveSubscriptionType(subType2);

		SubscriptionType subType3 = new SubscriptionType();
		subType3.setName("tip3");
		subTypeService.saveSubscriptionType(subType3);

		Subscription sub1 = new Subscription();
		sub1.setSubscriptionType(subType1);
		sub1.setValid(true);
		sub1.setValidFrom(new Date(120, 0, 23));
		sub1.setValidTo(new Date(121, 0, 23));

		Subscription sub2 = new Subscription();
		sub2.setSubscriptionType(subType2);
		sub2.setValid(true);
		sub2.setValidFrom(new Date(120, 0, 23));
		sub2.setValidTo(new Date(121, 0, 23));

		Subscription sub3 = new Subscription();
		sub3.setSubscriptionType(subType3);
		sub3.setValid(true);
		sub3.setValidFrom(new Date(120, 0, 23));
		sub3.setValidTo(new Date(121, 0, 23));

		subService.saveSubscription(sub1);
		subService.saveSubscription(sub2);
		subService.saveSubscription(sub3);

	}

	private void createWorkingHours() {
		WorkingHours wh1 = new WorkingHours();
		wh1.setFromTime(new Date(120, 0, 21, 12, 0));
		wh1.setToTime(new Date(120, 0, 21, 20, 0));

		WorkingHours wh2 = new WorkingHours();
		wh2.setFromTime(new Date(120, 0, 22, 12, 0));
		wh2.setToTime(new Date(120, 0, 22, 20, 0));

		WorkingHours wh3 = new WorkingHours();
		wh3.setFromTime(new Date(120, 0, 23, 12, 0));
		wh3.setToTime(new Date(120, 0, 23, 20, 0));

		whService.saveWorkingHours(wh1);
		whService.saveWorkingHours(wh2);
		whService.saveWorkingHours(wh3);

		Location location1 = new Location();
		location1.setAddress(locationService.findLocationByAddress("Zagreb").getAddress());
		location1.setFields(locationService.findLocationByAddress("Zagreb").getFields());
		location1.setName(locationService.findLocationByAddress("Zagreb").getName());
		locationService.updateLocation(locationService.findLocationByAddress("Zagreb").getId(), location1);

		Location location2 = new Location();
		location2.setAddress(locationService.findLocationByAddress("Rijeka").getAddress());
		location2.setFields(locationService.findLocationByAddress("Rijeka").getFields());
		location2.setName(locationService.findLocationByAddress("Rijeka").getName());
		locationService.updateLocation(locationService.findLocationByAddress("Rijeka").getId(), location2);

		Location location3 = new Location();
		location3.setAddress(locationService.findLocationByAddress("Split").getAddress());
		location3.setFields(locationService.findLocationByAddress("Split").getFields());
		location3.setName(locationService.findLocationByAddress("Split").getName());
		locationService.updateLocation(locationService.findLocationByAddress("Split").getId(), location3);
	}

	private void createContactInformations() {
		ContactInformation contactInfo1 = new ContactInformation();
		contactInfo1.setEmail("bla.bla");
		contactInfo1.setTelephoneNumber("0800999");

		ContactInformation contactInfo2 = new ContactInformation();
		contactInfo2.setEmail("gmail@hotmail");
		contactInfo2.setTelephoneNumber("091091");

		ContactInformation contactInfo3 = new ContactInformation();
		contactInfo3.setEmail("tvz.hr");
		contactInfo3.setTelephoneNumber("012882432");

		contactInfoService.saveContactInformation(contactInfo1);
		contactInfoService.saveContactInformation(contactInfo2);
		contactInfoService.saveContactInformation(contactInfo3);

	}

	private void createUsers() {
		Video video1 = new Video();
		video1.setLocation("/path1");
		videoService.saveVideo(video1);

		Video video2 = new Video();
		video2.setLocation("/path2");
		videoService.saveVideo(video2);

		Video video3 = new Video();
		video3.setLocation("/path3");
		videoService.saveVideo(video3);

		Camera cam1 = new Camera();
		cam1.setName("cam1");
		cam1.setVideos(Stream.of(video1).collect(Collectors.toList()));
		cameraService.saveCamera(cam1);

		Camera cam2 = new Camera();
		cam2.setName("cam2");
		cam2.setVideos(Stream.of(video2).collect(Collectors.toList()));
		cameraService.saveCamera(cam2);

		Camera cam3 = new Camera();
		cam3.setName("cam3");
		cam3.setVideos(Stream.of(video3).collect(Collectors.toList()));
		cameraService.saveCamera(cam3);

		Field field1 = new Field();
		field1.setCameras(Stream.of(cam1).collect(Collectors.toList()));
		fieldService.saveField(field1);

		Field field2 = new Field();
		field2.setCameras(Stream.of(cam2).collect(Collectors.toList()));
		fieldService.saveField(field2);

		Field field3 = new Field();
		field3.setCameras(Stream.of(cam3).collect(Collectors.toList()));
		fieldService.saveField(field3);

		Location location1 = new Location();
		location1.setAddress("Zagreb");
		location1.setFields(Stream.of(field1).collect(Collectors.toList()));
		location1.setName("location1");
		locationService.saveLocation(location1);

		Location location2 = new Location();
		location2.setAddress("Rijeka");
		location2.setFields(Stream.of(field2).collect(Collectors.toList()));
		location2.setName("location2");
		locationService.saveLocation(location2);

		Location location3 = new Location();
		location3.setFields(Stream.of(field3).collect(Collectors.toList()));
		location3.setName("location3");
		locationService.saveLocation(location3);

		Role role1 = new Role();
		role1.setName("admin");
		roleService.saveRole(role1);

		Role role2 = new Role();
		role2.setName("private");
		roleService.saveRole(role2);

		Role role3 = new Role();
		role3.setName("oper");
		roleService.saveRole(role3);

		User user1 = new User();
		user1.setDateOfBirth(new Date(91, 0, 21));
		user1.setEnabled(true);
		user1.setUsername("user1");
		user1.setCreatedAt(new Date());
		user1.setPassword("user1");
		user1.setRoles(Stream.of(role1).collect(Collectors.toList()));

		User user2 = new User();
		user2.setDateOfBirth(new Date(95, 2, 21));
		user2.setEnabled(true);
		user2.setUsername("user2");
		user2.setCreatedAt(new Date());
		user2.setPassword("user2");
		user2.setRoles(Stream.of(role2).collect(Collectors.toList()));

		User user3 = new User();
		user3.setDateOfBirth(new Date(100, 5, 12));
		user3.setEnabled(true);
		user3.setUsername("user3");
		user3.setCreatedAt(new Date());
		user3.setPassword("user3");
		user3.setRoles(Stream.of(role3).collect(Collectors.toList()));

		userService.saveUser(user1);
		userService.saveUser(user2);
		userService.saveUser(user3);
	}
}
