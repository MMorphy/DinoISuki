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
	
	@Before
	public void initCamera() {
		camera.setName("cam");
		cameraRepository.save(camera);
	}
	
	@Test
	public void findByName() {
		List<Camera> cameras = cameraRepository.findByName(camera.getName());
		assertThat(cameras.get(0).getName()).isEqualTo(camera.getName());
	}
	
	@Transactional
	@Test
	public void deleteByName() {
		cameraRepository.deleteByName(camera.getName());
		assertThat(cameraRepository.count()).isEqualTo(0);
	}
	
	@After
	public void deleteCamera() {
		cameraRepository.delete(camera);
	}

}