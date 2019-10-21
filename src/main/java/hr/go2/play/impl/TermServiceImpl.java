package hr.go2.play.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.Term;
import hr.go2.play.repositories.TermRepository;
import hr.go2.play.services.TermService;

@Service
public class TermServiceImpl implements TermService {

	@Autowired
	private TermRepository termRepo;
	
	public TermServiceImpl(TermRepository termRepo) {
		this.termRepo = termRepo;
	}
	
	@Override
	public List<Term> findAllTerms() {
		return this.termRepo.findAll();
	}

	@Override
	public Term findTermById(Long id) {
		try {
			return this.termRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public List<Term> findTermsByDate(Date date) {
		return (List<Term>) this.termRepo.findByDate(date);
	}

	@Override
	public List<Term> findTermsByDateAndTimeFrom(Date date, Date timeFrom) {
		return (List<Term>) this.termRepo.findByDateAndTimeFrom(date, timeFrom);
	}

	@Override
	public List<Term> findTermsByDateAndTimeFromAndAvailable(Date date, Date timeFrom, boolean available) {
		return (List<Term>) this.termRepo.findByDateAndTimeFromAndAvailable(date, timeFrom, available);
	}

	@Override
	public List<Term> findTermsByDateAndTimeTo(Date date, Date timeTo) {
		return (List<Term>) this.termRepo.findByDateAndTimeTo(date, timeTo);
	}

	@Override
	public List<Term> findTermsByDateAndTimeToAndAvailable(Date date, Date timeTo, boolean available) {
		return (List<Term>) this.termRepo.findByDateAndTimeToAndAvailable(date, timeTo, available);
	}

	@Override
	public Term findTermByVideosLocation(String path) {
		try {
			return this.termRepo.findByVideos_Location(path).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}
	
	@Override
	public void deleteTermById(Long id) {
		this.termRepo.deleteById(id);	
	}

	@Override
	public void deleteAllTerms() {
		this.termRepo.deleteAll();	
	}

	@Override
	public Term saveTerm(Term term) {
		return this.termRepo.save(term);
	}

	@Override
	public Term updateTerm(Long id, Term term) {
		Optional<Term> optTerm = this.termRepo.findById(id);
		if (optTerm.isPresent()) {
			Term ter = optTerm.get();
			ter.setAvailable(term.isAvailable());
			ter.setDate(term.getDate());
			ter.setTimeFrom(term.getTimeFrom());
			ter.setTimeTo(term.getTimeTo());
			ter.setVideos(term.getVideos());
			return this.termRepo.save(ter);
		} else {
			return this.termRepo.save(term);
		}
	}

}
