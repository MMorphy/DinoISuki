package hr.go2.play.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import hr.go2.play.entities.Subscription;
import hr.go2.play.entities.SubscriptionType;
import hr.go2.play.repositories.SubscriptionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionServiceImplTests {
	
	@Autowired
	private SubscriptionServiceImpl subscriptionService;
	
	private Subscription subscription = new Subscription();
    private SubscriptionType subscriptionType = new SubscriptionType();
	
	@Before
	public void init() {
        subscription.setValidFrom(new Date());
        subscription.setValidTo(new Date());
        
        subscriptionType.setName("sub");
        subscription.setSubscriptionType(subscriptionType);
		
		subscriptionService.saveSubscription(subscription);
	}
	
	@Test
	@Order(1)
	public void findByValidFrom() {
		List<Subscription> subscriptionTest = (List<Subscription>) subscriptionService.findSubscriptionByValidFrom(subscription.getValidFrom());
		assertThat(subscriptionTest.get(0).getId()).isEqualTo(subscription.getId());
	}
	
	@Test
	@Order(2)
	public void findByValidTo() {
		List<Subscription> subscriptionTest = (List<Subscription>) subscriptionService.findSubscriptionByValidTo(subscription.getValidTo());
		assertThat(subscriptionTest.get(0).getId()).isEqualTo(subscription.getId());
	}
	
	@Test
	@Order(3)
	public void findBySubscriptionType_Name() {
		List<Subscription> subscriptionTest = (List<Subscription>) subscriptionService.findSubscriptionBySubTypeName(subscriptionType.getName());
		assertThat(subscriptionTest.get(0).getId()).isEqualTo(subscription.getId());
	}
	
	@After
	public void deleteAll() {
		subscriptionService.deleteAllSubscriptions();
	}

}
