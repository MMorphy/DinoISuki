package hr.go2.play.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>{

	Optional<Video> findByLocation(String location); 
}
