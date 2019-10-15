package hr.go2.play.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import hr.go2.play.entities.Term;
import hr.go2.play.entities.User;
import hr.go2.play.repositories.UserRepository;
import hr.go2.play.services.UserService;

public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
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
	public List<User> findUsersByReservedTerms(Term reservedTerm) {
		return (List<User>) this.userRepo.findByReservedTerms(reservedTerm);
	}

	@Override
	public List<User> findUsersByLikedSportsName(String name) {
		return (List<User>) this.userRepo.findByLikedSports_Name(name);
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
	public User saveUser(User user) {
		return this.userRepo.save(user);
	}

	@Override
	public User updateUser(Long id, User user) {
		Optional<User> optUser = this.userRepo.findById(id);
		if (optUser.isPresent()) {
			User userNew = optUser.get();
			userNew.setCreatedAt(user.getCreatedAt());
			userNew.setDateOfBirth(user.getDateOfBirth());
			userNew.setEnabled(user.isEnabled());
			userNew.setLikedSports(user.getLikedSports());
			userNew.setPaidVideos(user.getPaidVideos());
			userNew.setPassword(user.getPassword());
			userNew.setReservedTerms(user.getReservedTerms());
			userNew.setRoles(user.getRoles());
			userNew.setUsername(user.getUsername());
			return this.userRepo.save(userNew);
		} else {
			return this.userRepo.save(user);
		}
	}

}
