package hr.go2.play.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.Video;

public interface VideoRepository extends JpaRepository<Video, Long>{

}
