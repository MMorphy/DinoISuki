package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.Term;
import hr.go2.play.entities.User;

public interface UserService {
	
	public abstract List<User> findAllUsers();
	
	public abstract User findUserById(Long id);
	
	public abstract List<User> findUsersByRoleName(String name);
	
	public abstract List<User> findUsersByEnabled(boolean enabled);
	
	public abstract List<User> findUsersByReservedTerms(Term reservedTerm);
	
	public abstract List<User> findUsersByLikedSportsName(String name);
	
	public abstract User findUserByUsername(String username);
	
	public abstract void deleteUserById(Long id);
	
	public abstract User saveUser(User user);
	
	public abstract User updateUser(Long id, User user);

	public abstract void deleteAllUsers();

	public abstract boolean existsUserById(Long id);

}
