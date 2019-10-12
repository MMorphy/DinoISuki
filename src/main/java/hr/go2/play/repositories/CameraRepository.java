package hr.go2.play.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.Camera;

public interface CameraRepository extends JpaRepository<Camera, Long> {
	
	public Collection<Camera> findByName(String name);
	
	public void deleteByName(String name);
	
	public Collection<Camera> findByVideos_Location(String location);

}
