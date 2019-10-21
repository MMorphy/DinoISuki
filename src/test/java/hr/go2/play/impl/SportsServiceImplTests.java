package hr.go2.play.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import hr.go2.play.entities.Sports;
import hr.go2.play.repositories.SportsRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SportsServiceImplTests {
	
	@Autowired
	private SportsServiceImpl sportsService;
	
	private Sports sports = new Sports();
	
	@Before
	public void init() {
		sports.setName("kosarka");
		
		sportsService.saveSports(sports);

	}
	
	@Test
	@Order(1)
	public void findByName() {
		Sports sportsTest = sportsService.findSportsByName(sports.getName());
		assertThat(sportsTest.getId()).isEqualTo(sports.getId());
	}
	
	@After
	public void deleteAll() {
		sportsService.deleteAllSports();
	}

}
