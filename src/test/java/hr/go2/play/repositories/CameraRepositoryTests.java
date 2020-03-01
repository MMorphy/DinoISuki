package hr.go2.play.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import hr.go2.play.entities.Camera;
import hr.go2.play.entities.Video;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CameraRepositoryTests {

	@Autowired
	private CameraRepository cameraRepository;

	private List<Video> videos = new ArrayList<>();
	private Camera camera = new Camera();
	private Video video = new Video();

	@Before
	public void init() {
		video.setLocation("path/to/video");
		videos.add(video);

		camera.setVideos(videos);
		camera.setName("cam");

		cameraRepository.save(camera);
	}

//	@Test
//	@Order(1)
//	public void findByName() {
//		List<Camera> cameras = (List<Camera>) cameraRepository.findByName(camera.getName());
//		assertThat(cameras.get(0).getName()).isEqualTo(camera.getName());
//	}
//
//	@Test
//	@Order(2)
//	public void findByVideos_Location() {
//		List<Camera> cameras = (List<Camera>) cameraRepository.findByVideos_Location(video.getLocation());
//		assertThat(cameras.get(0).getName()).isEqualTo(camera.getName());
//	}

	@Transactional
	@Test
	@Order(3)
	public void deleteByName() {
		cameraRepository.deleteByName(camera.getName());
		assertThat(cameraRepository.count()).isEqualTo(0);
	}

	@After
	public void clean() {
		cameraRepository.delete(camera);
	}

}