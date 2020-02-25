package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.Term;

@Repository
public interface TermRepository extends JpaRepository<Term, Long>{

	Collection<Term> findByDate(Date date);

	Collection<Term> findByAvailable(boolean available);

	Collection<Term> findByDateAndTimeFrom(Date date, Date timeFrom);

	Collection<Term> findByDateAndTimeFromAndAvailable(Date date, Date timeFrom, boolean available);

	Collection<Term> findByDateAndTimeTo(Date date, Date timeTo);

	Collection<Term> findByDateAndTimeToAndAvailable(Date date, Date timeTo, boolean available);

	@Query(value = "SELECT t FROM Term t WHERE date = ?1 AND available = ?2 AND field_id = ?3")
	Collection<Term> findByDateAndAvailableAndField_Id(Date date, boolean available, long id);

	@Query(value = "SELECT t FROM Term t WHERE available = ?1 AND field_id = ?2")
	Collection<Term> findByAvailableAndField_Id(boolean available, long id);

	Optional<Term> findByVideos_Location(String path);
}
