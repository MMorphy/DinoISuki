package hr.go2.play.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.go2.play.entities.WorkingHours;

public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long>{

}
