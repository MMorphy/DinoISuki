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

import hr.go2.play.entities.WorkingHours;
import hr.go2.play.repositories.WorkingHoursRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkingHoursServiceImplTests {
	
	@Autowired
	private WorkingHoursServiceImpl whService;
	
	private WorkingHours wh = new WorkingHours();
	
	@Before
	public void init() {
        wh.setFromTime(new Date());
        wh.setToTime(new Date());

        wh.setId(whService.saveWorkingHours(wh).getId());
	}
	
	@Test
	@Order(1)
	public void findByFromTime() {
		List<WorkingHours> whTest = (List<WorkingHours>) whService.findWorkingHoursByFromTime(wh.getFromTime());
		assertThat(whTest.get(0).getId()).isEqualTo(wh.getId());
	}
	
	@Test
	@Order(2)
	public void findByToTime() {
		List<WorkingHours> whTest = (List<WorkingHours>) whService.findWorkingHoursByToTime(wh.getToTime());
		assertThat(whTest.get(0).getId()).isEqualTo(wh.getId());
	}
	
	@Test
	@Order(3)
	public void findByFromTimeAndToTime() {
		List<WorkingHours> whTest = (List<WorkingHours>) whService.findWorkingHoursByFromTimeAndToTime(wh.getFromTime(), wh.getToTime());
		assertThat(whTest.get(0).getId()).isEqualTo(wh.getId());
	}
	
	@After
	public void deleteAll() {
		whService.deleteAllWorkingHours();
	}

}
