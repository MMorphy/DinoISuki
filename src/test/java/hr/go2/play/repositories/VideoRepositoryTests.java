package hr.go2.play.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import hr.go2.play.entities.Location;
import hr.go2.play.entities.Role;
import hr.go2.play.entities.User;
import hr.go2.play.entities.Video;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoRepositoryTests {
	
	@Autowired
	private VideoRepository videoRepository;
	
	private Video video = new Video();
	
	@Before
	public void init() {
        video.setLocation("Split");

        video.setId(videoRepository.save(video).getId());
	}
	
	@Test
	@Order(1)
	public void findByLocation() {
		Optional<Video> videoTest = videoRepository.findByLocation(video.getLocation());
		assertThat(videoTest.isPresent()).isTrue();
		assertThat(videoTest.get().getId()).isEqualTo(video.getId());
	}
	
	@After
	public void deleteAll() {
		videoRepository.deleteAll();
	}

}
