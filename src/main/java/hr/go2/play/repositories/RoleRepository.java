package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByName(String name);

	Collection<Role> findByUsers_Username (String username);

	Boolean existsByName (String name);
}

