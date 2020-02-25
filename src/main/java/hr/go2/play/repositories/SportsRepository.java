package hr.go2.play.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.Sports;

@Repository
public interface SportsRepository extends JpaRepository<Sports, Long>{

	Optional<Sports> findByName(String name);
}
