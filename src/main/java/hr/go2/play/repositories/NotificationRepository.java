package hr.go2.play.repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hr.go2.play.entities.Notification;
import hr.go2.play.entities.NotificationStatus;
import hr.go2.play.entities.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	Collection<Notification> findByCreatedAt(Date createdAt);

	Collection<Notification> findBySourceUser(User srcUser);

	Collection<Notification> findByDestUserOrderByCreatedAtDesc(User destUser);

	Collection<Notification> findByMessage(String message);

	Collection<Notification> findByStatus(NotificationStatus status);

	Collection<Notification> findBySourceUserAndDestUser(User srcUser, User destUser);

	Collection<Notification> findBySourceUserAndStatus(User srcUser, NotificationStatus status);

	Collection<Notification> findBySubject(String subject);

	@Modifying
	@Query(value = "DELETE FROM Notification notification WHERE notification.id IN :idList")
	void deleteMultipleNotificationById(List<Long> idList);

	@Query(value = "SELECT n FROM Notification n WHERE (n.subject IS NOT NULL AND n.subject LIKE ?1) OR (n.message IS NOT NULL AND n.message LIKE ?2)")
	Collection<Notification> searchBySubjectOrMessage(String subject, String message);

}
