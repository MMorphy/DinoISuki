package hr.go2.play.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import hr.go2.play.entities.Camera;
import hr.go2.play.entities.Field;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FieldServiceImplTests {

	@Autowired
	private FieldServiceImpl fieldService;

	@Autowired
	private CameraServiceImpl cameraService;

	private Field field = new Field();
	private Camera camera = new Camera();

	@Before
	public void initContactInfo() {
		camera.setName("camera");
		camera = cameraService.saveCamera(camera);

		List<Camera> cameras = new ArrayList<>();

		cameras.add(camera);

		field.setCameras(cameras);
		field.setId(fieldService.saveField(field).getId());
	}

	@Test
	@Order(1)
	public void findByCameras_Name() {
		Field testField = fieldService.findFieldByCamerasName(camera.getName());
		assertThat(testField.getId()).isEqualTo(field.getId());
	}

	@After
	public void deleteAll() {
		fieldService.deleteAllFields();
	}

}
