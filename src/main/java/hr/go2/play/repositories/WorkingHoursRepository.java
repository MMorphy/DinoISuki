package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.WorkingHours;

public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long>{

	Collection<WorkingHours> findByFromTime(Date fromTime);

	Collection<WorkingHours> findByToTime(Date toTime);

	Collection<WorkingHours> findByFromTimeAndToTime(Date fromTime, Date toTime);
}
