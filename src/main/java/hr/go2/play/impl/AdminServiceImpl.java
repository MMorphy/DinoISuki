package hr.go2.play.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.Location;
import hr.go2.play.entities.Role;
import hr.go2.play.entities.User;
import hr.go2.play.services.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	UserServiceImpl userService;
	@Autowired
	RoleServiceImpl roleService;
	@Autowired
	CameraServiceImpl cameraService;
	@Autowired
	LocationServiceImpl locationService;
	@Autowired
	WorkingHoursServiceImpl whService;
	@Autowired
	SubscriptionServiceImpl subService;
	@Autowired
	SubscriptionTypeServiceImpl subTypeService;

	//Returns null if username or locationName can't be found
	public User promoteUserToLocationAdmin(String usernameToPromote, String locationName) {
		Optional<User> user = Optional.of(userService.findUserByUsername(usernameToPromote));
		Optional<Location> location = Optional.of(locationService.findLocationByName(locationName));
		if (!user.isPresent() || !location.isPresent()) {
			return null;
		}
		Optional<Role> roleOpt = Optional.of(roleService.findRoleByNameAndLocationsName("ADMIN", locationName));
		if (roleOpt.isPresent()) {
			return user.get();
		}
		else {
			Role role = new Role();
			role.addLocation(location.get());
			role.setName("ADMIN");
			role.addUser(user.get());
			roleService.saveRole(role);
			return userService.findUserByUsername(usernameToPromote);
		}
	}
}
