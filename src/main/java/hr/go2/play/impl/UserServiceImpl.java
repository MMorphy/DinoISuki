package hr.go2.play.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hr.go2.play.DTO.UserWithContactInfoDTO;
import hr.go2.play.entities.ContactInformation;
import hr.go2.play.entities.Role;
import hr.go2.play.entities.Subscription;
import hr.go2.play.entities.User;
import hr.go2.play.repositories.RoleRepository;
import hr.go2.play.repositories.UserRepository;
import hr.go2.play.services.UserService;
import hr.go2.play.util.Commons;

@Service
public class UserServiceImpl implements UserService, org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private Commons commons;

	@Autowired
	private RoleServiceImpl roleService;

	@Autowired
	private ContactInformationServiceImpl contactService;

	@Autowired
	private RoleRepository roleRepo;

	@Override
	public boolean existsUserById(Long id) {
		return userRepo.existsById(id);
	}

	@Override
	public List<User> findAllUsers() {
		return this.userRepo.findAll();
	}

	@Override
	public User findUserById(Long id) {
		try {
			return this.userRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public List<User> findUsersByRoleName(String name) {
		return (List<User>) this.userRepo.findByRoles_name(name);
	}

	@Override
	public List<User> findUsersByEnabled(boolean enabled) {
		return (List<User>) this.userRepo.findByEnabled(enabled);
	}

	@Override
	public User findUserByUsername(String username) {
		try {
			return this.userRepo.findByUsername(username).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public void deleteUserById(Long id) {
		this.userRepo.deleteById(id);
	}

	@Override
	public void deleteAllUsers() {
		this.userRepo.deleteAll();
		this.contactService.deleteAllContactInformations();
	}

	@Override
	public User saveUser(User user) {
		if (!commons.isPasswordEncripted(user.getPassword())) {
			user.setPassword(commons.encodePassword(user.getPassword()));
		}
		user.setEnabled(user.isEnabled());
		Optional<Role> userRole = roleRepo.findByName("role_user");
		Collection<Role> roles = new ArrayList<Role>();
		if (!userRole.isPresent()) {
			Role role = new Role(null, "role_user", null);
			roleRepo.save(role);
			roles.add(role);
		} else {
			roles.add(userRole.get());
		}
		user.setRoles(roles);
		userRepo.save(user);
		return user;
	}

	// Saves user (with "ROLE_USER" role) and saves the new ContactInformation
	public User saveUserAndSaveRoleAndContactInfo(User user, ContactInformation userCI) {
		user.setContactInformation(userCI);
		Role userRole = roleService.findRoleByName("ROLE_USER");
		user.addRole(userRole);
		userRole.addUser(user);
		contactService.saveContactInformation(userCI);
		this.saveUser(user);
		roleService.updateRole(userRole.getId(), userRole);
		return user;
	}

	@Override
	public User updateUser(Long id, User user) {
		Optional<User> optUser = this.userRepo.findById(id);
		if (optUser.isPresent()) {
			User userNew = optUser.get();
			userNew.setCreatedAt(user.getCreatedAt());
			userNew.setDateOfBirth(user.getDateOfBirth());
			userNew.setEnabled(user.isEnabled());
			if (commons.isPasswordEncripted(user.getPassword())) {
				userNew.setPassword(user.getPassword());
			} else {
				userNew.setPassword(commons.encodePassword(user.getPassword()));
			}
			userNew.setRoles(user.getRoles());
			userNew.setUsername(user.getUsername());
			userNew.setProfilePhoto(user.getProfilePhoto());
			contactService.updateContactInformation(userNew.getContactInformation().getId(), user.getContactInformation());
			userNew.setContactInformation(user.getContactInformation());
			return this.userRepo.save(userNew);
		} else {
			return this.userRepo.save(user);
		}
	}

	public User updateUser(User user) {
		return this.userRepo.save(user);
	}

	public User editProfile(String username, User user) {
		Optional<User> optUser = this.userRepo.findByUsername(username);
		if (optUser.isPresent()) {
			User userNew = optUser.get();
			userNew.setDateOfBirth(user.getDateOfBirth());
			if (commons.isPasswordEncripted(user.getPassword())) {
				userNew.setPassword(user.getPassword());
			} else {
				userNew.setPassword(commons.encodePassword(user.getPassword()));
			}
			contactService.updateContactInformation(userNew.getContactInformation().getId(), user.getContactInformation());
			userNew.setContactInformation(user.getContactInformation());
			return this.userRepo.save(userNew);
		} else {
			return this.userRepo.save(user);
		}
	}

	// Updates user and contact information
	public User updateUserAndContactInfo(User oldUser, UserWithContactInfoDTO newUserWithContactInfoDTO) {
		ContactInformation oldCI = oldUser.getContactInformation();
		// Update CI
		oldCI.setEmail(newUserWithContactInfoDTO.getContactInfoDto().getEmail());
		oldCI.setTelephoneNumber(newUserWithContactInfoDTO.getContactInfoDto().getTelephoneNumber());
		// Update User
		oldUser.setDateOfBirth(newUserWithContactInfoDTO.getUserDto().getDateOfBirth());
		oldUser.setContactInformation(oldCI);
		this.editProfile(oldUser.getUsername(), oldUser);
		return null;
	}

	@Override
	public User findUserByProfilePhoto(String photo) {
		try {
			return this.userRepo.findByProfilePhoto(photo).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public boolean existsUserByUsernmae(String username) {
		return this.userRepo.existsUserByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (userRepo.findByUsername(username).isPresent()) {
			User user = userRepo.findByUsername(username).get();
			if (user != null) {
				List<GrantedAuthority> authorities = getUserAuthority((List<Role>) user.getRoles());
				return buildUserForAuthentication(user, authorities);
			} else {
				throw new UsernameNotFoundException("username not found");
			}
		} else {
			return null;
		}
	}

	private List<GrantedAuthority> getUserAuthority(List<Role> userRoles) {
		List<GrantedAuthority> roles = new ArrayList<>();
		userRoles.forEach(role -> roles.add(new SimpleGrantedAuthority(role.getName())));
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}

	@Override
	public User findByContactInformation(ContactInformation contactInformation) {
		Optional<User> optionalUser = userRepo.findByContactInformation(contactInformation);
		if (optionalUser.isPresent()) {
			return userRepo.findByContactInformation(contactInformation).get();
		} else {
			return null;
		}
	}

	@Override
	public List<Subscription> findByIdAndValidSubscription(Long userId, boolean valid) {
		return (List<Subscription>) userRepo.findByIdAndValidSubscription(userId, valid);
	}

	@Override
	public int countActiveUsers() {
		return userRepo.countActiveUsers();
	}

	@Override
	public User findBySubscriptionId(Long subscriptionId) {
		Optional<User> optionalUser = userRepo.findBySubscriptionId(subscriptionId);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			return null;
		}
	}
}
