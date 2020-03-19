package hr.go2.play.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.Role;
import hr.go2.play.entities.User;
import hr.go2.play.repositories.RoleRepository;
import hr.go2.play.repositories.UserRepository;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
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
	}

	public User updateUser(User user) {
		return this.userRepo.save(user);
	}

	public void deleteUser(User user) {
		this.userRepo.delete(user);
	}

	public User findUserByUsername(String username) {
		if (userRepo.findByUsername(username).isPresent()) {
			return userRepo.findByUsername(username).get();
		} else {
			return null;
		}
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

	private UserDetails buildUserForAuthentication (User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

}
