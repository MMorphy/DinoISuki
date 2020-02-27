package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.NotificationStatus;

public interface NotificationStatusService {

	public abstract List<NotificationStatus> findAllNotificationStatuses();

	public abstract NotificationStatus findNotificationStatusById(Long id);

	public abstract List<NotificationStatus> findNotificationsStatusesByType(String type);

	public abstract NotificationStatus saveNotificationStatus(NotificationStatus notificationStatus);

	public abstract NotificationStatus updateNotificationStatus(Long id, String type);

	public abstract void deleteNotificationStatusById(Long id);

	public abstract void deleteAllNotificationStatuses();

}
