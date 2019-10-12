package hr.go2.play.repositories;

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

import hr.go2.play.entities.WorkingHours;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkingHoursRepositoryTests {
	
	@Autowired
	private WorkingHoursRepository whRepository;
	
	private WorkingHours wh = new WorkingHours();
	
	@Before
	public void init() {
        wh.setFromTime(new Date());
        wh.setToTime(new Date());

        wh.setId(whRepository.save(wh).getId());
	}
	
	@Test
	@Order(1)
	public void findByFromTime() {
		List<WorkingHours> whTest = (List<WorkingHours>) whRepository.findByFromTime(wh.getFromTime());
		assertThat(whTest.get(0).getId()).isEqualTo(wh.getId());
	}
	
	@Test
	@Order(2)
	public void findByToTime() {
		List<WorkingHours> whTest = (List<WorkingHours>) whRepository.findByToTime(wh.getToTime());
		assertThat(whTest.get(0).getId()).isEqualTo(wh.getId());
	}
	
	@Test
	@Order(3)
	public void findByFromTimeAndToTime() {
		List<WorkingHours> whTest = (List<WorkingHours>) whRepository.findByFromTimeAndToTime(wh.getFromTime(), wh.getToTime());
		assertThat(whTest.get(0).getId()).isEqualTo(wh.getId());
	}
	
	@After
	public void deleteAll() {
		whRepository.deleteAll();
	}

}
