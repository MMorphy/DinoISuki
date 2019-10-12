package hr.go2.play.repositories;

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
import hr.go2.play.entities.ContactInformation;
import hr.go2.play.entities.Field;
import hr.go2.play.entities.Sports;
import hr.go2.play.entities.Term;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FieldRepositoryTests {
	
	@Autowired
	private FieldRepository fieldRepository;

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
		field.setId(fieldRepository.save(field).getId());
	}
	
	@Test
	@Order(1)
	public void findBySport() {
		List<Field> fields = (List<Field>) fieldRepository.findBySport_Name(sport.getName());
		assertThat(fields.get(0).getId()).isEqualTo(field.getId());
	}
	
	@Test
	@Order(2)
	public void findByCameras_Name() {
		Optional<Field> testField = fieldRepository.findByCameras_Name(camera.getName());
		assertThat(testField.isPresent()).isTrue();
		assertThat(testField.get().getId()).isEqualTo(field.getId());
	}
	
	@Test
	@Order(3)
	public void findByTerms_Available() {
		List<Field> fields = (List<Field>) fieldRepository.findByTerms_Available(term.isAvailable());
		assertThat(fields.get(0).getId()).isEqualTo(field.getId());
	}
	
	@After
	public void deleteAll() {
		fieldRepository.deleteAll();
	}

}
