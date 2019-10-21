package hr.go2.play.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import hr.go2.play.entities.Video;
import hr.go2.play.repositories.VideoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoServiceImplTests {
	
	@Autowired
	private VideoServiceImpl videoService;
	
	private Video video = new Video();
	
	@Before
	public void init() {
        video.setLocation("Split");

        video.setId(videoService.saveVideo(video).getId());
	}
	
	@Test
	@Order(1)
	public void findByLocation() {
		Video videoTest = videoService.findVideoByLocation(video.getLocation());
		assertThat(videoTest.getId()).isEqualTo(video.getId());
	}
	
	@After
	public void deleteAll() {
		videoService.deleteAllVideos();
	}

}
