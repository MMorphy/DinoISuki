package hr.go2.play.services;

import java.util.Date;
import java.util.List;

import hr.go2.play.entities.Term;

public interface TermService {
	
	public abstract List<Term> findAllTerms();
	
	public abstract Term findTermById(Long id);
	
	public abstract List<Term> findTermsByDate(Date date);
	
	public abstract List<Term> findTermsByDateAndTimeFrom(Date date, Date timeFrom);
	
	public abstract List<Term> findTermsByDateAndTimeFromAndAvailable(Date date, Date timeFrom, boolean available);
	
	public abstract List<Term> findTermsByDateAndTimeTo(Date date, Date timeTo);
	
	public abstract List<Term> findTermsByDateAndTimeToAndAvailable(Date date, Date timeTo, boolean available);
	
	public abstract Term findTermByVideosLocation(String path);
	
	public abstract void deleteTermById(Long id);

	public abstract Term saveTerm(Term term);
	
	public abstract Term updateTerm(Long id, Term term);

}
