package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.Term;
import hr.go2.play.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Collection<User> findByRoles_name(String name);

	Collection<User> findByEnabled(boolean enabled);

	Collection<User> findByReservedTerms(Term reservedTerm);

	Collection<User> findByLikedSports_Name(String name);

	Optional<User> findByUsername(String username);
}
