package hr.go2.play.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{

}
