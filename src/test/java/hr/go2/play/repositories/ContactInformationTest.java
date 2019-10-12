package hr.go2.play.repositories;

import static org.assertj.core.api.Assertions.assertThat;

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

import hr.go2.play.entities.ContactInformation;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactInformationTest {

	@Autowired
	private ContactInformationRepository contactInfoRepo;

	private ContactInformation contactInfo = new ContactInformation();

	@Before
	public void initContactInfo() {
		contactInfo.setEmail("info@email.com");
		contactInfo.setTelephoneNumber("0981234567");
		contactInfo.setId(contactInfoRepo.save(contactInfo).getId());
	}
	
	@Test
	@Order(1)
	public void findByIdTest() {
		Optional<ContactInformation> testInfoOpt = contactInfoRepo.findById(contactInfo.getId());
		assertThat(testInfoOpt.isPresent()).isTrue();
		assertThat(testInfoOpt.get().getId()).isEqualTo(contactInfo.getId());
	}

	@Test
	@Order(2)
	public void findByEmail() {
		Optional<ContactInformation> testInfoOpt = contactInfoRepo.findByEmail("info@email.com");
		assertThat(testInfoOpt.isPresent()).isTrue();
		assertThat(testInfoOpt.get().getEmail()).isEqualTo(contactInfo.getEmail());

	}

	@Test
	@Order(3)
	public void findByEmailLike() {
		List<ContactInformation> contactInfos = (List)contactInfoRepo.findByEmailLike("%email%");
		assertThat(contactInfos.size()).isEqualTo(1);
		assertThat(contactInfos.get(0).getId()).isEqualTo(contactInfo.getId());
	}

	@Test
	@Order(4)
	public void findByEmailLikeFail() {
		List<ContactInformation> contactInfos = (List)contactInfoRepo.findByEmailLike("%gmail%");
		assertThat(contactInfos.size()).isEqualTo(0);
	}

	@Test
	@Order(5)
	public void findByEmailFail() {
		Optional<ContactInformation> testInfoOpt = contactInfoRepo.findByEmail("mail");
		assertThat(testInfoOpt.isPresent()).isFalse();
	}

	@Test
	@Order(6)
	public void findByTelephoneNumber() {
		Optional<ContactInformation> testInfoOpt = contactInfoRepo.findByTelephoneNumber("0981234567");
		assertThat(testInfoOpt.isPresent()).isTrue();
		assertThat(testInfoOpt.get().getTelephoneNumber()).isEqualTo(contactInfo.getTelephoneNumber());
	}

	@Test
	@Order(7)
	public void findByTelephoneNumberFail() {
		Optional<ContactInformation> testInfoOpt = contactInfoRepo.findByTelephoneNumber("0000000000");
		assertThat(testInfoOpt.isPresent()).isFalse();
	}

	@Test
	@Order(8)
	public void findByTelephoneNumberLike() {
		List<ContactInformation> testInfoOpt = (List)contactInfoRepo.findByTelephoneNumberLike("098%");
		assertThat(testInfoOpt.size()).isEqualTo(1);
		assertThat(testInfoOpt.get(0).getId()).isEqualTo(contactInfo.getId());
	}

	@Test
	@Order(9)
	public void findByTelephoneNumberLikeFail() {
		List<ContactInformation> testInfoOpt = (List)contactInfoRepo.findByTelephoneNumberLike("091%");
		assertThat(testInfoOpt.size()).isEqualTo(0);
	}
	
	@After
	public void deleteAll() {
		contactInfoRepo.deleteAll();
	}
	
}
