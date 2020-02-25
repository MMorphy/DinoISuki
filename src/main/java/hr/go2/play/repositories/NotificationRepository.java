package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.Notification;
import hr.go2.play.entities.NotificationStatus;
import hr.go2.play.entities.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
	Collection<Notification> findByCreatedAt(Date createdAt);
	
	Collection<Notification> findBySourceUser(User srcUser);
	
	Collection<Notification> findByDestUser(User destUser);
	
	Collection<Notification> findByMessage(String  message);
	
	Collection<Notification> findByStatus(NotificationStatus status);
	
	Collection<Notification> findBySourceUserAndDestUser(User srcUser, User destUser);
	
	Collection<Notification> findBySourceUserAndStatus(User srcUser, NotificationStatus status);

}
