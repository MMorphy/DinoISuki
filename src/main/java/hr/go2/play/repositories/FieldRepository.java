package hr.go2.play.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.Field;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long>{

	Optional<Field> findByCameras_Name(String name);
}
