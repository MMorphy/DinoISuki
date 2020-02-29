package hr.go2.play.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.Camera;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Long> {
	
	public Optional<Camera> findByName(String name);
	
	public void deleteByName(String name);
	
	public Optional<Camera> findByVideos_Location(String location);

	public Optional<Camera> findByVideos_Id(Long id);

}
