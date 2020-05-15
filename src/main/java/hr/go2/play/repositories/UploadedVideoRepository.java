package hr.go2.play.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.UploadedVideo;

@Repository
public interface UploadedVideoRepository extends JpaRepository<UploadedVideo, Long> {

	Optional<UploadedVideo> findByLocation(String location);

	Optional<UploadedVideo> findByVideoName(String videoName);

}
