package hr.go2.play.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.ContactInformation;
import hr.go2.play.entities.Role;
import hr.go2.play.entities.User;
import hr.go2.play.repositories.ContactInformationRepository;
import hr.go2.play.repositories.RoleRepository;
import hr.go2.play.repositories.UserRepository;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ContactInformationRepository contactInfoRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	public void saveUser (User user, ContactInformation contactInfo) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		List<Role> userRole = (List<Role>) roleRepo.findByName("role_user");
		if (!roleRepo.existsByName("role_user")) {
        	userRole.add(new Role(null, "role_user", null, null));
        	roleRepo.save(userRole.get(0));
        }
		user.setRoles(userRole);
		user.setContactInfo(contactInfo);
		userRepo.save(user);
	}
	
	public User updateUser(Long id, User user, ContactInformation contactInfo) {
		Optional<User> optUser = this.userRepo.findById(id);
		if (optUser.isPresent()) {
			User userNew = optUser.get();
			userNew.setCreatedAt(user.getCreatedAt());
			userNew.setDateOfBirth(user.getDateOfBirth());
			userNew.setEnabled(user.isEnabled());
			userNew.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userNew.setRoles(user.getRoles());
			userNew.setUsername(user.getUsername());
			return this.userRepo.save(userNew);
		} else {
			return this.userRepo.save(user);
		}
	}

	public User findUserByUsername(String username) {
	    return userRepo.findByUsername(username).get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username).get();
		if (user != null) {
			List<GrantedAuthority> authorities = getUserAuthority((List<Role>) user.getRoles());
			return buildUserForAuthentication (user, authorities);
		} else {
			throw new UsernameNotFoundException("username not found");
		}
	}
	
	private List<GrantedAuthority> getUserAuthority(List<Role> userRoles) {
		List<GrantedAuthority> roles = new ArrayList<>();
		userRoles.forEach(role -> roles.add(new SimpleGrantedAuthority(role.getName())));
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication (User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

}
