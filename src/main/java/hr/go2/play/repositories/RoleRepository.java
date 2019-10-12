package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Collection<Role> findByName(String name);

	Optional<Role> findByLocations_Name (String name);

	Optional<Role> findByLocations_Address (String address);

	Collection<Role> findByUsers_Username (String username);
}

