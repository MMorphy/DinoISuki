package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.DayType;
import hr.go2.play.entities.WorkingHours;

@Repository
public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long>{

	Collection<WorkingHours> findByFromTime(Date fromTime);

	Collection<WorkingHours> findByToTime(Date toTime);

	Collection<WorkingHours> findByFromTimeAndToTime(Date fromTime, Date toTime);

	Collection<WorkingHours> findByDayType(DayType type);

	Collection<WorkingHours> findByDayType_Type(String name);
}
