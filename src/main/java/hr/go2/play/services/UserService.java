package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.ContactInformation;
import hr.go2.play.entities.Subscription;
import hr.go2.play.entities.User;

public interface UserService {

	public abstract List<User> findAllUsers();

	public abstract User findUserById(Long id);

	public abstract List<User> findUsersByRoleName(String name);

	public abstract List<User> findUsersByEnabled(boolean enabled);

	public abstract User findUserByUsername(String username);

	public abstract User findUserByProfilePhoto(String photo);

	public abstract void deleteUserById(Long id);

	public abstract User saveUser(User user);

	public abstract User updateUser(Long id, User user);

	public abstract void deleteAllUsers();

	public abstract boolean existsUserById(Long id);

	public abstract boolean existsUserByUsernmae(String username);

	public abstract User findByContactInformation(ContactInformation contactInformation);

	public abstract List<Subscription> findByIdAndValidSubscription(Long userId, boolean valid);

	public abstract int countActiveUsers();

}
