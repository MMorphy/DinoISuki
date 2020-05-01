package hr.go2.play;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.go2.play.entities.Camera;
import hr.go2.play.entities.ContactInformation;
import hr.go2.play.entities.DayType;
import hr.go2.play.entities.Field;
import hr.go2.play.entities.Location;
import hr.go2.play.entities.Role;
import hr.go2.play.entities.Subscription;
import hr.go2.play.entities.SubscriptionType;
import hr.go2.play.entities.User;
import hr.go2.play.entities.WorkingHours;
import hr.go2.play.impl.CameraServiceImpl;
import hr.go2.play.impl.ContactInformationServiceImpl;
import hr.go2.play.impl.DayTypeServiceImpl;
import hr.go2.play.impl.FieldServiceImpl;
import hr.go2.play.impl.LocationServiceImpl;
import hr.go2.play.impl.RoleServiceImpl;
import hr.go2.play.impl.SubscriptionServiceImpl;
import hr.go2.play.impl.UserServiceImpl;
import hr.go2.play.impl.WorkingHoursServiceImpl;
import hr.go2.play.services.SubscriptionTypeService;
import hr.go2.play.util.Commons;

@RestController
@RequestMapping(path = "/api/dev")
public class DevRest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DevRest.class);

	private static final int NO_OF_FIELDS = 3;

	private static final int NO_OF_CAMERAS = 3;

	private static final long MILIS_IN_A_DAY = TimeUnit.DAYS.toMillis(1);

	@Autowired
	private LocationServiceImpl locService;

	@Autowired
	private FieldServiceImpl fieldService;

	@Autowired
	private CameraServiceImpl camService;

	@Autowired
	private ContactInformationServiceImpl ciService;

	@Autowired
	private WorkingHoursServiceImpl whService;

	@Autowired
	private DayTypeServiceImpl dtService;

	@Autowired
	private Commons commons;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private RoleServiceImpl roleService;

	@Autowired
	private SubscriptionServiceImpl subService;

	@Autowired
	private SubscriptionTypeService stService;

	@PostMapping("/generateDummy")
	public ResponseEntity<String> generateDummyData() {
		return new ResponseEntity<String>(generateDummyDataLogic(), HttpStatus.ACCEPTED);
	}

	@PostMapping("/generateSub")
	public ResponseEntity<String> generateSubDummyData() {
		return new ResponseEntity<String>(generateSubscriptionDummyDataLogic(), HttpStatus.ACCEPTED);
	}

	private String generateDummyDataLogic() {
		generateTestLocation();
//		addRoles();
		return commons.JSONfyReturnMessage("Successfully made PSQL entries!");
	}

	private String generateSubscriptionDummyDataLogic() {
		generateSubscriptions();
		return commons.JSONfyReturnMessage("Successfully made subscriptions for all users!");
	}
	private void generateSubscriptions() {
		if (stService.findAllSubscriptionTypes().isEmpty()) {
			generateSubscriptionTypes();
		}
		List<User> users = userService.findAllUsers();
		for (User u : users) {
			Subscription activeSub = new Subscription();
			activeSub.setSubscriptionType(stService.findSubscriptionTypeByName("Half Year").get(0));
			activeSub.setValid(true);
			activeSub.setValidFrom(new Date());
			activeSub.setValidTo(new Date(Instant.now().toEpochMilli() + (MILIS_IN_A_DAY * 180)));
			subService.saveSubscription(activeSub);

			Subscription inactiveSub = new Subscription();
			inactiveSub.setSubscriptionType(stService.findSubscriptionTypeByName("Monthly").get(0));
			inactiveSub.setValid(false);
			inactiveSub.setValidFrom(new Date(Instant.now().toEpochMilli() - (MILIS_IN_A_DAY * 180)));
			inactiveSub.setValidTo(new Date(Instant.now().toEpochMilli() - (MILIS_IN_A_DAY * 150)));
			subService.saveSubscription(inactiveSub);


			Collection<Subscription> userSubs = u.getSubscriptions();
			userSubs.add(activeSub);
			userSubs.add(inactiveSub);
			u.setSubscriptions(userSubs);
			userService.updateUser(u);
		}
	}

	private void generateSubscriptionTypes() {
		SubscriptionType type = new SubscriptionType();
		type.setName("Daily");
		type.setPrice(10F);
		stService.saveSubscriptionType(type);

		type = new SubscriptionType();
		type.setName("Weekly");
		type.setPrice(50F);
		stService.saveSubscriptionType(type);

		type = new SubscriptionType();
		type.setName("Monthly");
		type.setPrice(150F);
		stService.saveSubscriptionType(type);

		type = new SubscriptionType();
		type.setName("Half Year");
		type.setPrice(500F);
		stService.saveSubscriptionType(type);
	}

	private void addRoles() {
		Role role = new Role();
		if (!roleService.existsRoleByName("ROLE_USER")) {
			role.setName("ROLE_USER");
			role.setUsers(new ArrayList<>());
			roleService.saveRole(role);
		}
		if (!roleService.existsRoleByName("ROLE_ADMIN")) {
			role.setName("ROLE_ADMIN");
			role.setUsers(new ArrayList<>());
			roleService.saveRole(role);
		}
	}

	private Location generateTestLocation() {
		Location location = new Location();
		try {
			location.setAddress("Dragutina Kušlana 52");
			location.setName("Klinček");
			location.setFields(generateTestFields(location.getName()));
			location.setHours(new ArrayList<>());
			location.setContactInformation(generateCI());
			location = locService.saveLocation(location);
			location.setHours(generateWorkingHours(location));
			locService.updateLocation(location.getId(), location);
		} catch (Exception ex) {
			LOGGER.error("Location generation failed!");
			return location;
		}
		return location;
	}

	private List<Field> generateTestFields(String locationName) {
		List<Field> fields = new ArrayList<>();
		for (int i = 0; i < NO_OF_FIELDS; i++) {
			Field f = new Field();
			f.setCameras(generateTestCameras(i + 1, locationName));
			fields.add(f);
			fieldService.saveField(f);
		}
		return fields;
	}

	private List<Camera> generateTestCameras(int fieldNO, String locationName) {
		List<Camera> cameras = new ArrayList<>();
		for (int i = 0; i < NO_OF_CAMERAS; i++) {
			Camera c = new Camera();
			c.setAvailable(true);
			c.setName(locationName.substring(0, 3).toUpperCase() + (i + 1 + 3 * fieldNO - 1));
			c.setVideos(null);
			cameras.add(c);
			camService.saveCamera(c);
		}
		return cameras;
	}

	private List<WorkingHours> generateWorkingHours(Location location) {
		Collection<Location> locs = new ArrayList<>();
		locs.add(location);
		List<WorkingHours> whs = new ArrayList<>();

		WorkingHours wh = new WorkingHours();
		wh.setFromTime(new Date(1970, 1, 1, 8, 0, 0));
		wh.setToTime(new Date(1970, 1, 1, 20, 0, 0));
		wh.setDayType(generateDayType("WORKDAY"));
		wh.setLocations(locs);
		whService.saveWorkingHours(wh);
		whs.add(wh);

		wh = new WorkingHours();
		wh.setFromTime(new Date(1970, 1, 1, 12, 0, 0));
		wh.setToTime(new Date(1970, 1, 1, 20, 0, 0));
		wh.setDayType(generateDayType("WEEKEND_SATURDAY"));
		wh.setLocations(locs);
		whService.saveWorkingHours(wh);
		whs.add(wh);

		wh = new WorkingHours();
		wh.setFromTime(new Date(1970, 1, 1, 16, 0, 0));
		wh.setToTime(new Date(1970, 1, 1, 20, 0, 0));
		wh.setDayType(generateDayType("WEEKEND_SUNDAY"));
		wh.setLocations(locs);
		whService.saveWorkingHours(wh);
		whs.add(wh);

		wh = new WorkingHours();
		wh.setFromTime(new Date(1970, 1, 1, 18, 0, 0));
		wh.setToTime(new Date(1970, 1, 1, 20, 0, 0));
		wh.setDayType(generateDayType("HOLYDAY"));
		wh.setLocations(locs);
		whService.saveWorkingHours(wh);
		whs.add(wh);

		return whs;
	}

	private DayType generateDayType(String type) {
		DayType dt = new DayType();
		dt.setType(type);
		return dtService.saveDayType(dt);
	}

	private ContactInformation generateCI() {
		ContactInformation contactinfo = new ContactInformation();
		contactinfo.setEmail("test@gmail.com");
		contactinfo.setTelephoneNumber("012322933");
		return ciService.saveContactInformation(contactinfo);
	}
}
