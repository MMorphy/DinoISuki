package hr.go2.play.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
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

import hr.go2.play.entities.Camera;
import hr.go2.play.entities.Field;
import hr.go2.play.entities.Sports;
import hr.go2.play.entities.Term;
import hr.go2.play.repositories.FieldRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FieldServiceImplTests {
	
	@Autowired
	private FieldServiceImpl fieldService;

	private Field field = new Field();
	private Sports sport = new Sports();
	private Camera camera = new Camera();
	private Term term = new Term();

	@Before
	public void initContactInfo() {
		sport.setName("fudbal");
		camera.setName("camera");
		term.setAvailable(true);
		
		List<Camera> cameras = new ArrayList<>();
		List<Term> terms = new ArrayList<>();
		
		cameras.add(camera);
		terms.add(term);
		
		field.setSport(sport);
		field.setCameras(cameras);
		field.setTerms(terms);
		field.setId(fieldService.saveField(field).getId());
	}
	
	@Test
	@Order(1)
	public void findBySport() {
		List<Field> fields = (List<Field>) fieldService.findFieldBySportName(sport.getName());
		assertThat(fields.get(0).getId()).isEqualTo(field.getId());
	}
	
	@Test
	@Order(2)
	public void findByCameras_Name() {
		Field testField = fieldService.findFieldByCamerasName(camera.getName());
		assertThat(testField.getId()).isEqualTo(field.getId());
	}
	
	@Test
	@Order(3)
	public void findByTerms_Available() {
		List<Field> fields = (List<Field>) fieldService.findFieldByTermsAvail(term.isAvailable());
		assertThat(fields.get(0).getId()).isEqualTo(field.getId());
	}
	
	@Test
	@Order(4)
	public void updateContactInformation() {
		sport.setName("sah");
		field.setSport(sport);
		
		fieldService.updateField(field.getId(), field);
		assertThat(fieldService.findFieldById(field.getId()).getSport().getName()).isEqualTo(sport.getName());
	}
	
	@After
	public void deleteAll() {
		fieldService.deleteAllFields();
	}

}
