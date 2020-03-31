package hr.go2.play.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import hr.go2.play.entities.SubscriptionType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionTypeRepositoryTests {

	@Autowired
	private SubscriptionTypeRepository subscriptionTypeRepository;

	private SubscriptionType subscriptionType = new SubscriptionType();

	@Before
	public void init() {
		subscriptionType.setName("kosarka");
		subscriptionType.setPrice((float) 10);
		subscriptionTypeRepository.save(subscriptionType);

	}

	@Test
	@Order(1)
	public void findByName() {
		List<SubscriptionType> subscriptionTypeTest = (List<SubscriptionType>) subscriptionTypeRepository.findByName(subscriptionType.getName());
		assertThat(subscriptionTypeTest.get(0).getId()).isEqualTo(subscriptionType.getId());
	}

	@After
	public void deleteAll() {
		subscriptionTypeRepository.deleteAll();
	}

}
