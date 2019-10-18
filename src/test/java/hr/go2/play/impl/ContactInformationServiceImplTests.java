package hr.go2.play.impl;

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
import hr.go2.play.repositories.ContactInformationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactInformationServiceImplTests {
	
	@Autowired
	private ContactInformationServiceImpl contactInfoService;

	private ContactInformation contactInfo = new ContactInformation();

	@Before
	public void initContactInfo() {
		contactInfo.setEmail("info@email.com");
		contactInfo.setTelephoneNumber("0981234567");
		contactInfo.setId(contactInfoService.saveContactInformation(contactInfo).getId());
	}
	
	@Test
	@Order(1)
	public void findByIdTest() {
		ContactInformation testInfoOpt = contactInfoService.findContactInformationById(contactInfo.getId());
		assertThat(testInfoOpt.getId()).isEqualTo(contactInfo.getId());
	}

	@Test
	@Order(2)
	public void findByEmail() {
		ContactInformation testInfoOpt = contactInfoService.findContactInformationByEmail("info@email.com");
		assertThat(testInfoOpt.getEmail()).isEqualTo(contactInfo.getEmail());

	}

	@Test
	@Order(3)
	public void findByEmailLike() {
		List<ContactInformation> contactInfos = (List)contactInfoService.findContactInformationByEmailLike("%email%");
		assertThat(contactInfos.size()).isEqualTo(1);
		assertThat(contactInfos.get(0).getId()).isEqualTo(contactInfo.getId());
	}

	@Test
	@Order(4)
	public void findByTelephoneNumber() {
		ContactInformation testInfoOpt = contactInfoService.findContactInformationByTelNumber("0981234567");
		assertThat(testInfoOpt.getTelephoneNumber()).isEqualTo(contactInfo.getTelephoneNumber());
	}

	@Test
	@Order(5)
	public void findByTelephoneNumberLike() {
		List<ContactInformation> testInfoOpt = (List)contactInfoService.findContactInformationByTelNumberLike("098%");
		assertThat(testInfoOpt.size()).isEqualTo(1);
		assertThat(testInfoOpt.get(0).getId()).isEqualTo(contactInfo.getId());
	}
	
	@Test
	@Order(6)
	public void updateContactInformation() {
		contactInfo.setEmail("test@test.com");
		contactInfo.setTelephoneNumber("08000800");
		
		contactInfoService.updateContactInformation(contactInfo.getId(), contactInfo);
		assertThat(contactInfoService.findContactInformationByEmail(contactInfo.getEmail()).getEmail()).isEqualTo("test@test.com");
	}
	
	@After
	public void deleteAll() {
		contactInfoService.deleteAllContactInformations();
	}

}
