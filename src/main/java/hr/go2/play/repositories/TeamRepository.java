package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
	
	Optional<Team> findByName(String name);
	
	Collection<Team> findByUsers_Username (String username);

}
