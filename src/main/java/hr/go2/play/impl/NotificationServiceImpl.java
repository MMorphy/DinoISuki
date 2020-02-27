package hr.go2.play.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.Notification;
import hr.go2.play.entities.NotificationStatus;
import hr.go2.play.entities.User;
import hr.go2.play.repositories.NotificationRepository;
import hr.go2.play.services.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private NotificationRepository notificationRepo;

	@Override
	public List<Notification> findAllNotifications() {
		return this.notificationRepo.findAll();
	}

	@Override
	public Notification findNotificationById(Long id) {
		try {
			return this.notificationRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public List<Notification> findNotificationsByCreatedAt(Date createdAt) {
		return (List<Notification>) this.notificationRepo.findByCreatedAt(createdAt);
	}

	@Override
	public List<Notification> findNotificationsBySourceUser(User srcUser) {
		return (List<Notification>) this.notificationRepo.findBySourceUser(srcUser);
	}

	@Override
	public List<Notification> findNotificationsByDestUser(User destUser) {
		return (List<Notification>) this.notificationRepo.findByDestUser(destUser);
	}

	@Override
	public List<Notification> findNotificationsByMessage(String message) {
		return (List<Notification>) this.notificationRepo.findByMessage(message);
	}

	@Override
	public List<Notification> findNotificationsByStatus(NotificationStatus status) {
		return (List<Notification>) this.notificationRepo.findByStatus(status);
	}

	@Override
	public List<Notification> findNotificationsBySourceUserAndDestUser(User srcUser, User destUser) {
		List<Notification> notifications = null;
		if (srcUser != null && destUser != null) {
			notifications = (List<Notification>) this.notificationRepo.findBySourceUserAndDestUser(srcUser, destUser);
		} else if (srcUser != null) {
			logger.debug("Dest user not found. Searching by src user.");
			notifications = (List<Notification>) this.notificationRepo.findBySourceUser(srcUser);
		} else if (destUser != null) {
			logger.debug("Source user not found. Searching by dest user.");
			notifications = (List<Notification>) this.notificationRepo.findByDestUser(destUser);
		} else {
			logger.debug("Source or dest user not found. Returning all notifications.");
			notifications = this.notificationRepo.findAll();
		}
		return notifications;
	}

	@Override
	public List<Notification> findNotificationsBySourceUserAndStatus(User srcUser, NotificationStatus status) {
		return (List<Notification>) this.notificationRepo.findBySourceUserAndStatus(srcUser, status);
	}

	@Override
	public List<Notification> findNotificationsBySubject(String subject) {
		return (List<Notification>) this.notificationRepo.findBySubject(subject);
	}

	@Override
	public void deleteNotificationById(Long id) {
		this.notificationRepo.deleteById(id);
	}

	@Override
	public void deleteMultipleNotificationById(List<Long> idList) {
		this.notificationRepo.deleteMultipleNotificationById(idList);
	}

	@Override
	public Notification saveNotification(Notification notification) {
		return this.notificationRepo.save(notification);
	}

	@Override
	public Notification updateNotification(Notification notification) {
		Optional<Notification> optNotification = this.notificationRepo.findById(notification.getId());
		if (optNotification.isPresent()) {
			Notification notif = optNotification.get();
			notif.setDestUser(notification.getDestUser());
			notif.setMessage(notification.getMessage());
			notif.setSourceUser(notification.getSourceUser());
			notif.setStatus(notification.getStatus());
			notif.setSubject(notification.getSubject());
			return this.notificationRepo.save(notif);
		} else {
			return this.notificationRepo.save(notification);
		}
	}

	@Override
	public void deleteAllNotifications() {
		this.notificationRepo.deleteAll();

	}

}
