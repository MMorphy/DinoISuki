package hr.go2.play.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.Sports;

public interface SportsRepository extends JpaRepository<Sports, Long>{

}
