package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>{

	Optional<Video> findByLocation(String location);

	Optional<Video> findByStartedAt(Date timestamp);

	// Ovdje bi mogao biti issue s formatom input parametra
	@Query("SELECT v "
		+ "FROM Video v "
		+ "WHERE DATE(v.startedAt) = DATE(?1)")
	Collection<Video> findByDate(Date date);

	@Query("SELECT v " + "FROM Video v " + "WHERE DATE(v.startedAt) < DATE(?1) AND v.archived = FALSE")
	Collection<Video> findVideosOlderThanDate(Date date);

	@Query("SELECT COUNT(v.id) FROM Video v WHERE v.archived = FALSE") // skipping archived videos
	int countActiveVideos();
}
