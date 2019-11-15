package hr.go2.play.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import hr.go2.play.entities.Location;
import hr.go2.play.entities.Notification;
import hr.go2.play.entities.NotificationStatus;
import hr.go2.play.entities.User;
import hr.go2.play.repositories.NotificationRepository;
import hr.go2.play.services.NotificationService;

public class NotificationServiceImpl implements NotificationService {

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
		return (List<Notification>) this.notificationRepo.findBySourceUserAndDestUser(srcUser, destUser);
	}

	@Override
	public List<Notification> findNotificationsBySourceUserAndStatus(User srcUser, NotificationStatus status) {
		return (List<Notification>) this.notificationRepo.findBySourceUserAndStatus(srcUser, status);
	}

	@Override
	public void deleteNotificationById(Long id) {
		this.notificationRepo.deleteById(id);
		
	}

	@Override
	public Notification saveNotification(Notification notification) {
		return this.notificationRepo.save(notification);
	}

	@Override
	public Notification updateNotification(Long id, Notification notification) {
		Optional<Notification> optNotification = this.notificationRepo.findById(id);
		if (optNotification.isPresent()) {
			Notification notif = optNotification.get();
			notif.setDestUser(notification.getDestUser());
			notif.setMessage(notification.getMessage());
			notif.setSourceUser(notification.getSourceUser());
			notif.setStatus(notification.getStatus());
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
