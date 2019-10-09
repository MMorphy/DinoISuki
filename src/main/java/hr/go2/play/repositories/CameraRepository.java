package hr.go2.play.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.Camera;

public interface CameraRepository extends JpaRepository<Camera, Long> {

}
