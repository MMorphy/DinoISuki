package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.Term;

public interface TermRepository extends JpaRepository<Term, Long>{

	Collection<Term> findByDate(Date date);

	Collection<Term> findByDateAndTimeFrom(Date date, Date timeFrom);

	Collection<Term> findByDateAndTimeFromAndAvailable(Date date, Date timeFrom, boolean available);

	Collection<Term> findByDateAndTimeTo(Date date, Date timeTo);

	Collection<Term> findByDateAndTimeToAndAvailable(Date date, Date timeTo, boolean available);

	Optional<Term> findByVideos_Location(String path);
}
