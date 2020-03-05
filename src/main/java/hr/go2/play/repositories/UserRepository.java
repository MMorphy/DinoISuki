package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.ContactInformation;
import hr.go2.play.entities.Subscription;
import hr.go2.play.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Collection<User> findByRoles_name(String name);

	Collection<User> findByEnabled(boolean enabled);

	Optional<User> findByUsername(String username);

	Boolean existsUserByUsername(String username);

	Optional<User> findByProfilePhoto(String photo);

	Optional<User> findByContactInformation(ContactInformation contactInformation);

	@Query("SELECT s FROM User u JOIN u.subscriptions s WHERE u.id = ?1 AND s.valid = ?2")
	Collection<Subscription> findByIdAndValidSubscription(Long userId, boolean valid);
}
