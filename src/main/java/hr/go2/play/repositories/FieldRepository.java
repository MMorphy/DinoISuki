package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.Field;

public interface FieldRepository extends JpaRepository<Field, Long>{

	Collection<Field> findBySport_Name(String sport);

	Optional<Field> findByCameras_Name(String name);

	Collection<Field> findByTerms_Available(boolean availability);
}
