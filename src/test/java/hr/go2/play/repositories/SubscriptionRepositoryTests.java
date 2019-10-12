package hr.go2.play.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
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

import hr.go2.play.entities.Location;
import hr.go2.play.entities.Role;
import hr.go2.play.entities.Subscription;
import hr.go2.play.entities.SubscriptionType;
import hr.go2.play.entities.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionRepositoryTests {
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	private Subscription subscription = new Subscription();
    private SubscriptionType subscriptionType = new SubscriptionType();
	
	@Before
	public void init() {
        subscription.setValidFrom(new Date());
        subscription.setValidTo(new Date());
        
        subscriptionType.setName("sub");
        subscription.setSubscriptionType(subscriptionType);
		
		subscriptionRepository.save(subscription);
	}
	
	@Test
	@Order(1)
	public void findByValidFrom() {
		List<Subscription> subscriptionTest = (List<Subscription>) subscriptionRepository.findByValidFrom(subscription.getValidFrom());
		assertThat(subscriptionTest.get(0).getId()).isEqualTo(subscription.getId());
	}
	
	@Test
	@Order(2)
	public void findByValidTo() {
		List<Subscription> subscriptionTest = (List<Subscription>) subscriptionRepository.findByValidTo(subscription.getValidTo());
		assertThat(subscriptionTest.get(0).getId()).isEqualTo(subscription.getId());
	}
	
	@Test
	@Order(3)
	public void findBySubscriptionType_Name() {
		List<Subscription> subscriptionTest = (List<Subscription>) subscriptionRepository.findBySubscriptionType_Name(subscriptionType.getName());
		assertThat(subscriptionTest.get(0).getId()).isEqualTo(subscription.getId());
	}
	
	@After
	public void deleteAll() {
		subscriptionRepository.deleteAll();
	}

}
