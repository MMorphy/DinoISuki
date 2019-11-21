package hr.go2.play.impl;

import java.util.ArrayList;
import java.util.List;

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
	
	public void saveUser (User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		List<Role> userRole = (List<Role>) roleRepo.findByName("admin");
		user.setRoles(userRole);
		userRepo.save(user);
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
