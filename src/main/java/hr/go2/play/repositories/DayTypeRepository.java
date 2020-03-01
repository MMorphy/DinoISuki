package hr.go2.play.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.DayType;

@Repository
public interface DayTypeRepository extends JpaRepository<DayType, Long>{

	public Optional<DayType> findByType(String type);
}
