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

import hr.go2.play.entities.Term;
import hr.go2.play.entities.Video;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TermRepositoryTests {
	
	@Autowired
	private TermRepository termRepository;
	
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
		
		term.setId(termRepository.save(term).getId());
	}
	
	@Test
	@Order(1)
	public void findByDate() {
		List<Term> termTest = (List<Term>) termRepository.findByDate(term.getDate());
		assertThat(termTest.get(0).getId()).isEqualTo(term.getId());
	}
	
	@Test
	@Order(2)
	public void findByDateAndTimeFrom() {
		List<Term> termTest = (List<Term>) termRepository.findByDateAndTimeFrom(term.getDate(), term.getTimeFrom());
		assertThat(termTest.get(0).getId()).isEqualTo(term.getId());
	}
	
	@Test
	@Order(3)
	public void findByDateAndTimeFromAndAvailable() {
		List<Term> termTest = (List<Term>) termRepository.findByDateAndTimeFromAndAvailable(term.getDate(), term.getTimeFrom(), term.isAvailable());
		assertThat(termTest.get(0).getId()).isEqualTo(term.getId());
	}
	
	@Test
	@Order(4)
	public void findByDateAndTimeTo() {
		List<Term> termTest = (List<Term>) termRepository.findByDateAndTimeTo(term.getDate(), term.getTimeTo());
		assertThat(termTest.get(0).getId()).isEqualTo(term.getId());
	}
	
	@Test
	@Order(5)
	public void findByDateAndTimeToAndAvailable() {
		List<Term> termTest = (List<Term>) termRepository.findByDateAndTimeToAndAvailable(term.getDate(), term.getTimeTo(), term.isAvailable());
		assertThat(termTest.get(0).getId()).isEqualTo(term.getId());
	}
	
	@Test
	@Order(6)
	public void findByVideos_Location() {
		Optional<Term> termTest = termRepository.findByVideos_Location(video.getLocation());
		assertThat(termTest.get().getId()).isEqualTo(term.getId());
	}
	
	@After
	public void deleteAll() {
		termRepository.deleteAll();
	}


}
