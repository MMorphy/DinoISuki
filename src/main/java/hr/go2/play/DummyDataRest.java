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
import hr.go2.play.entities.Sports;
import hr.go2.play.entities.Subscription;
import hr.go2.play.entities.SubscriptionType;
import hr.go2.play.entities.Term;
import hr.go2.play.entities.User;
import hr.go2.play.entities.Video;
import hr.go2.play.entities.WorkingHours;
import hr.go2.play.impl.CameraServiceImpl;
import hr.go2.play.impl.ContactInformationServiceImpl;
import hr.go2.play.impl.FieldServiceImpl;
import hr.go2.play.impl.LocationServiceImpl;
import hr.go2.play.impl.RoleServiceImpl;
import hr.go2.play.impl.SportsServiceImpl;
import hr.go2.play.impl.SubscriptionServiceImpl;
import hr.go2.play.impl.SubscriptionTypeServiceImpl;
import hr.go2.play.impl.TermServiceImpl;
import hr.go2.play.impl.UserServiceImpl;
import hr.go2.play.impl.VideoServiceImpl;
import hr.go2.play.impl.WorkingHoursServiceImpl;

@RestController
@RequestMapping(path = "/dummy", produces = "application/json", method = {RequestMethod.GET, RequestMethod.PUT})
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
	private SportsServiceImpl sportsService;
	
	@Autowired
	private VideoServiceImpl videoService;
	
	@Autowired
	private TermServiceImpl termService;
	
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
	public void populateDB(){
		
		createContactInformations();
		createVideos();
		createCameras();
		createSports();
		createTerms();
		createFields();
		createLocations();
		createRoles();
		createUsers();
		createWorkingHours();
		createSubscriptionTypes();
		createSubscriptions();
	}
	
	private void createSubscriptions() {
		Subscription sub1 = new Subscription();
		sub1.setSubscriptionType(subTypeService.findSubscriptionTypeByName("tip1").get(0));
		sub1.setValid(true);
		sub1.setValidFrom(new Date(120, 0, 23));
		sub1.setValidTo(new Date(121, 0, 23));
		
		Subscription sub2 = new Subscription();
		sub2.setSubscriptionType(subTypeService.findSubscriptionTypeByName("tip2").get(0));
		sub2.setValid(true);
		sub2.setValidFrom(new Date(120, 0, 23));
		sub2.setValidTo(new Date(121, 0, 23));
		
		Subscription sub3 = new Subscription();
		sub3.setSubscriptionType(subTypeService.findSubscriptionTypeByName("tip3").get(0));
		sub3.setValid(true);
		sub3.setValidFrom(new Date(120, 0, 23));
		sub3.setValidTo(new Date(121, 0, 23));
		
		subService.saveSubscription(sub1);
		subService.saveSubscription(sub2);
		subService.saveSubscription(sub3);
		
	}
	
	private void createSubscriptionTypes() {
		SubscriptionType subType1 = new SubscriptionType();
		subType1.setName("tip1");
		
		SubscriptionType subType2 = new SubscriptionType();
		subType2.setName("tip2");
		
		SubscriptionType subType3 = new SubscriptionType();
		subType3.setName("tip3");
		
		subTypeService.saveSubscriptionType(subType1);
		subTypeService.saveSubscriptionType(subType2);
		subTypeService.saveSubscriptionType(subType3);
		
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
		location1.setHours(wh1);
		location1.setContactUser(userService.findUserByUsername("user1"));
		location1.setAddress(locationService.findLocationByAddress("Zagreb").getAddress());
		location1.setFields(locationService.findLocationByAddress("Zagreb").getFields());
		location1.setName(locationService.findLocationByAddress("Zagreb").getName());
		locationService.updateLocation(locationService.findLocationByAddress("Zagreb").getId(), location1);
		
		Location location2 = new Location();
		location2.setHours(wh2);
		location2.setContactUser(userService.findUserByUsername("user2"));
		location2.setAddress(locationService.findLocationByAddress("Rijeka").getAddress());
		location2.setFields(locationService.findLocationByAddress("Rijeka").getFields());
		location2.setName(locationService.findLocationByAddress("Rijeka").getName());
		locationService.updateLocation(locationService.findLocationByAddress("Rijeka").getId(), location2);
		
		Location location3 = new Location();
		location3.setHours(wh3);
		location3.setContactUser(userService.findUserByUsername("user3"));
		location3.setAddress(locationService.findLocationByAddress("Split").getAddress());
		location3.setFields(locationService.findLocationByAddress("Split").getFields());
		location3.setName(locationService.findLocationByAddress("Split").getName());
		locationService.updateLocation(locationService.findLocationByAddress("Split").getId(), location3);
	}
	
	private void createLocations() {
		Location location1 = new Location();
		location1.setAddress("Zagreb");
		location1.setFields(fieldService.findFieldBySportName("Nogomet"));
		location1.setName("location1");
		
		Location location2 = new Location();
		location2.setAddress("Rijeka");
		location2.setFields(fieldService.findFieldBySportName("Tenis"));
		location2.setName("location2");
		
		Location location3 = new Location();
		location3.setAddress("Split");
		location3.setFields(fieldService.findFieldBySportName("Kosarka"));
		location3.setName("location3");
		
		locationService.saveLocation(location1);
		locationService.saveLocation(location2);
		locationService.saveLocation(location3);
	}
	
	private void createRoles() {
		Role role1 = new Role();
		role1.setLocations(Stream.of(locationService.findLocationByName("location1")).collect(Collectors.toList()));
		role1.setName("admin");
		
		Role role2 = new Role();
		role2.setLocations(Stream.of(locationService.findLocationByName("location2")).collect(Collectors.toList()));
		role2.setName("private");
		
		Role role3 = new Role();
		role3.setLocations(Stream.of(locationService.findLocationByName("location3")).collect(Collectors.toList()));
		role3.setName("oper");
		
		roleService.saveRole(role1);
		roleService.saveRole(role2);
		roleService.saveRole(role3);
		
	}
	
	private void createTerms() {
		Term term1 = new Term();
		term1.setAvailable(true);
		term1.setDate(new Date(120, 0, 21));
		term1.setTimeFrom(new Date(120, 0, 21, 12, 0));
		term1.setTimeTo(new Date(120, 0, 21, 13, 9));
		term1.setVideos(Stream.of(videoService.findVideoByLocation("/path1")).collect(Collectors.toList()));
		
		Term term2 = new Term();
		term2.setAvailable(true);
		term2.setDate(new Date(120, 1, 22));
		term2.setTimeFrom(new Date(120, 1, 22, 14, 0));
		term2.setTimeTo(new Date(120, 1, 22, 15, 0));
		term2.setVideos(Stream.of(videoService.findVideoByLocation("/path2")).collect(Collectors.toList()));
		
		Term term3 = new Term();
		term3.setAvailable(true);
		term3.setDate(new Date(120, 2, 23));
		term3.setTimeFrom(new Date(120, 2, 23, 16, 0));
		term3.setTimeTo(new Date(120, 2, 23, 17, 0));
		term3.setVideos(Stream.of(videoService.findVideoByLocation("/path3")).collect(Collectors.toList()));
		
		termService.saveTerm(term1);
		termService.saveTerm(term2);
		termService.saveTerm(term3);
	}
	
	private void createSports() {
		Sports sport1 = new Sports();
		sport1.setName("Nogomet");
		
		Sports sport2 = new Sports();
		sport2.setName("Tenis");
		
		Sports sport3 = new Sports();
		sport3.setName("Kosarka");
		
		sportsService.saveSports(sport1);
		sportsService.saveSports(sport2);
		sportsService.saveSports(sport3);
	}
	
	private void createVideos() {
		Video video1 = new Video();
		video1.setLocation("/path1");
		
		Video video2 = new Video();
		video2.setLocation("/path2");
		
		Video video3 = new Video();
		video3.setLocation("/path3");
		
		videoService.saveVideo(video1);
		videoService.saveVideo(video2);
		videoService.saveVideo(video3);
	}
	
	private void createFields () {
		
		Field field1 = new Field();
		field1.setCameras(cameraService.findCameraByName("cam1"));
		field1.setSport(sportsService.findSportsByName("Nogomet"));
		field1.setTerms(Stream.of(termService.findTermByVideosLocation("/path1")).collect(Collectors.toList()));
		
		Field field2 = new Field();
		field2.setCameras(cameraService.findCameraByName("cam2"));
		field2.setSport(sportsService.findSportsByName("Tenis"));
		field2.setTerms(Stream.of(termService.findTermByVideosLocation("/path2")).collect(Collectors.toList()));
		
		Field field3 = new Field();
		field3.setCameras(cameraService.findCameraByName("cam3"));
		field3.setSport(sportsService.findSportsByName("Kosarka"));
		field3.setTerms(Stream.of(termService.findTermByVideosLocation("/path3")).collect(Collectors.toList()));
		
		fieldService.saveField(field1);
		fieldService.saveField(field2);
		fieldService.saveField(field3);
		
	}
	
	private void createContactInformations () {
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
	
	private  void createUsers() {
		User user1 = new User();
		user1.setDateOfBirth(new Date(91, 0, 21));
		user1.setEnabled(true);
		user1.setUsername("user1");
		user1.setCreatedAt(new Date());
		user1.setPassword("user1");
		user1.setLikedSports(Stream.of(sportsService.findSportsByName("Nogomet")).collect(Collectors.toList()));
		user1.setPaidVideos(Stream.of(videoService.findVideoByLocation("/path1")).collect(Collectors.toList()));
		user1.setReservedTerms(Stream.of(termService.findTermByVideosLocation("/path1")).collect(Collectors.toList()));
		user1.setRoles(roleService.findRoleByName("admin"));
		
		User user2 = new User();
		user2.setDateOfBirth(new Date(95, 2, 21));
		user2.setEnabled(true);
		user2.setUsername("user2");
		user2.setCreatedAt(new Date());
		user2.setPassword("user2");
		user2.setLikedSports(Stream.of(sportsService.findSportsByName("Tenis")).collect(Collectors.toList()));
		user2.setPaidVideos(Stream.of(videoService.findVideoByLocation("/path2")).collect(Collectors.toList()));
		user2.setReservedTerms(Stream.of(termService.findTermByVideosLocation("/path2")).collect(Collectors.toList()));
		user2.setRoles(roleService.findRoleByName("private"));
		
		User user3 = new User();
		user3.setDateOfBirth(new Date(100,5, 12));
		user3.setEnabled(true);
		user3.setUsername("user3");
		user3.setCreatedAt(new Date());
		user3.setPassword("user3");
		user3.setLikedSports(Stream.of(sportsService.findSportsByName("Kosarka")).collect(Collectors.toList()));
		user3.setPaidVideos(Stream.of(videoService.findVideoByLocation("/path3")).collect(Collectors.toList()));
		user3.setReservedTerms(Stream.of(termService.findTermByVideosLocation("/path3")).collect(Collectors.toList()));
		user3.setRoles(roleService.findRoleByName("oper"));
		
		userService.saveUser(user1);
		userService.saveUser(user2);
		userService.saveUser(user3);
		
		Role role1 = new Role();
		Location location1 = new Location();
		role1.setUsers(Stream.of(userService.findUserByUsername("user1")).collect(Collectors.toList()));
		role1.setName(roleService.findRoleByLocationsAddress("Zagreb").getName());
		role1.setLocations(roleService.findRoleByLocationsAddress("Zagreb").getLocations());
		location1.setContactUser(user1);
		location1.setAddress(locationService.findLocationByAddress("Zagreb").getAddress());
		location1.setFields(locationService.findLocationByAddress("Zagreb").getFields());
		location1.setHours(locationService.findLocationByAddress("Zagreb").getHours());
		location1.setName(locationService.findLocationByAddress("Zagreb").getName());
		roleService.updateRole(roleService.findRoleByLocationsAddress("Zagreb").getId(), role1);
		locationService.updateLocation(locationService.findLocationByAddress("Zagreb").getId(), location1);
		
		Role role2 = new Role();
		Location location2 = new Location();
		role2.setUsers(Stream.of(userService.findUserByUsername("user2")).collect(Collectors.toList()));
		role2.setName(roleService.findRoleByLocationsAddress("Rijeka").getName());
		role2.setLocations(roleService.findRoleByLocationsAddress("Rijeka").getLocations());
		location2.setContactUser(user2);
		location2.setAddress(locationService.findLocationByAddress("Rijeka").getAddress());
		location2.setFields(locationService.findLocationByAddress("Rijeka").getFields());
		location2.setHours(locationService.findLocationByAddress("Rijeka").getHours());
		location2.setName(locationService.findLocationByAddress("Rijeka").getName());
		roleService.updateRole(roleService.findRoleByLocationsAddress("Rijeka").getId(), role2);
		locationService.updateLocation(locationService.findLocationByAddress("Rijeka").getId(), location2);
		
		Role role3 = new Role();
		Location location3 = new Location();
		role3.setUsers(Stream.of(userService.findUserByUsername("user3")).collect(Collectors.toList()));
		role3.setName(roleService.findRoleByLocationsAddress("Split").getName());
		role3.setLocations(roleService.findRoleByLocationsAddress("Split").getLocations());
		location3.setContactUser(user3);
		location3.setAddress(locationService.findLocationByAddress("Split").getAddress());
		location3.setFields(locationService.findLocationByAddress("Split").getFields());
		location3.setHours(locationService.findLocationByAddress("Split").getHours());
		location3.setName(locationService.findLocationByAddress("Split").getName());
		roleService.updateRole(roleService.findRoleByLocationsAddress("Split").getId(), role3);
		locationService.updateLocation(locationService.findLocationByAddress("Split").getId(), location3);
}
	private  void createCameras() {
		Camera cam1 = new Camera();
		cam1.setName("cam1");
		cam1.setVideos(Stream.of(videoService.findVideoByLocation("/path1")).collect(Collectors.toList()));
		
		Camera cam2 = new Camera();
		cam2.setName("cam2");
		cam2.setVideos(Stream.of(videoService.findVideoByLocation("/path2")).collect(Collectors.toList()));
		
		Camera cam3 = new Camera();
		cam3.setName("cam3");
		cam3.setVideos(Stream.of(videoService.findVideoByLocation("/path3")).collect(Collectors.toList()));
		
		cameraService.saveCamera(cam1);
		cameraService.saveCamera(cam2);
		cameraService.saveCamera(cam3);
}
}
