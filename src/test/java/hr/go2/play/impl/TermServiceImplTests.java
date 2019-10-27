package hr.go2.play.impl;

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

import hr.go2.play.entities.Term;
import hr.go2.play.entities.Video;
import hr.go2.play.repositories.TermRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TermServiceImplTests {
	
	@Autowired
	private TermServiceImpl termService;
	
	private Term term = new Term();
	private Video video = new Video();
	private List<Video> videos = new ArrayList<>();
	
	@Before
	public void init() {
		term.setDate(new Date());
		term.setTimeFrom(new Date());
		term.setAvailable(true);
		
		video.setLocation("Zagreb");
		videos.add(video);
		term.setVideos(videos);
		
		term.setId(termService.saveTerm(term).getId());
	}
	
	@Test
	@Order(1)
	public void findByDate() {
		List<Term> termTest = (List<Term>) termService.findTermsByDate(term.getDate());
		assertThat(termTest.get(0).getId()).isEqualTo(term.getId());
	}
	
	@Test
	@Order(2)
	public void findByDateAndTimeFrom() {
		List<Term> termTest = (List<Term>) termService.findTermsByDateAndTimeFrom(term.getDate(), term.getTimeFrom());
		assertThat(termTest.get(0).getId()).isEqualTo(term.getId());
	}
	
	@Test
	@Order(3)
	public void findByDateAndTimeFromAndAvailable() {
		List<Term> termTest = (List<Term>) termService.findTermsByDateAndTimeFromAndAvailable(term.getDate(), term.getTimeFrom(), term.isAvailable());
		assertThat(termTest.get(0).getId()).isEqualTo(term.getId());
	}
	
	@Test
	@Order(4)
	public void findByDateAndTimeTo() {
		List<Term> termTest = (List<Term>) termService.findTermsByDateAndTimeTo(term.getDate(), term.getTimeTo());
		assertThat(termTest.get(0).getId()).isEqualTo(term.getId());
	}
	
	@Test
	@Order(5)
	public void findByDateAndTimeToAndAvailable() {
		List<Term> termTest = (List<Term>) termService.findTermsByDateAndTimeToAndAvailable(term.getDate(), term.getTimeTo(), term.isAvailable());
		assertThat(termTest.get(0).getId()).isEqualTo(term.getId());
	}
	
	@Test
	@Order(6)
	public void findByAvailable() {
		List<Term> termTest = (List<Term>) termService.findTermsByAvailable(term.isAvailable());
		assertThat(termTest.get(0).getId()).isEqualTo(term.getId());
	}

	@Test
	@Order(7)
	public void findByVideos_Location() {
		Term termTest = termService.findTermByVideosLocation(video.getLocation());
		assertThat(termTest.getId()).isEqualTo(term.getId());
	}
	
	@After
	public void deleteAll() {
		termService.deleteAllTerms();
	}

}
