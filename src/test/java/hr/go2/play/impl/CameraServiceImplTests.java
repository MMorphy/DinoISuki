package hr.go2.play.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import hr.go2.play.entities.Camera;
import hr.go2.play.entities.Video;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CameraServiceImplTests {

	@Autowired
	private CameraServiceImpl camService;

	private List<Video> videos = new ArrayList<>();
	private Camera camera = new Camera();
	private Video video = new Video();

	@Before
	public void init() {
		video.setLocation("path/to/video");
		videos.add(video);

		camera.setVideos(videos);
		camera.setName("cam");

		camService.saveCamera(camera);
	}

//	@Test
//	@Order(1)
//	public void findByName() {
//		List<Camera> cameras = camService.findCameraByName(camera.getName());
//		assertThat(cameras.get(0).getName()).isEqualTo(camera.getName());
//	}
//
//	@Test
//	@Order(2)
//	public void findByVideos_Location() {
//		List<Camera> cameras = camService.findCameraByVideoLocation(video.getLocation());
//		assertThat(cameras.get(0).getName()).isEqualTo(camera.getName());
//	}
//
//	@Test
//	@Order(3)
//	public void updateCamera() {
//		camera.setName("camera");
//		camService.updateCamera(camera.getId(), camera);
//		assertThat(camService.findCameraByName(camera.getName()).get(0).getName()).isEqualTo("camera");
//	}
//
//	@Test
//	@Order(4)
//	public void deleteByName() {
//		camService.deleteCameraByName(camera.getName());
//		assertThat(camService.findCameraByName(camera.getName())).doesNotContain(camera);
//	}
//
//	@After
//	public void clean() {
//		camService.deleteAllCameras();
//	}

}
