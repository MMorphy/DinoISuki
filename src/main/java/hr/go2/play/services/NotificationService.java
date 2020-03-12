package hr.go2.play.services;

import java.util.Date;
import java.util.List;

import hr.go2.play.entities.Notification;
import hr.go2.play.entities.NotificationStatus;
import hr.go2.play.entities.User;

public interface NotificationService {

	public abstract List<Notification> findAllNotifications();

	public abstract Notification findNotificationById(Long id);

	public abstract List<Notification> findNotificationsByCreatedAt(Date createdAt);

	public abstract List<Notification> findNotificationsBySourceUser(User srcUser);

	public abstract List<Notification> findNotificationsByDestUser(User destUser);

	public abstract List<Notification> findNotificationsByMessage(String message);

	public abstract List<Notification> findNotificationsByStatus(NotificationStatus status);

	public abstract List<Notification> findNotificationsBySourceUserAndDestUser(User srcUser, User destUser);

	public abstract List<Notification> findNotificationsBySourceUserAndStatus(User srcUser, NotificationStatus status);

	public abstract List<Notification> findNotificationsBySubject(String subject);

	public abstract void deleteNotificationById(Long id);

	public abstract void deleteMultipleNotificationById(List<Long> idList);

	public abstract Notification saveNotification(Notification notification);

	public abstract Notification updateNotification(Notification notification);

	public abstract void deleteAllNotifications();

	public abstract List<Notification> searchBySubjectOrMessage(String subject, String message);

}
