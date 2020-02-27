package hr.go2.play.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.NotificationStatus;

@Repository
public interface NotificationStatusRepository extends JpaRepository<NotificationStatus, Long> {

	public List<NotificationStatus> findByType(String type);

}
