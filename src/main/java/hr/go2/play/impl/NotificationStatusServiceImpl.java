package hr.go2.play.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.NotificationStatus;
import hr.go2.play.repositories.NotificationStatusRepository;
import hr.go2.play.services.NotificationStatusService;

@Service
public class NotificationStatusServiceImpl implements NotificationStatusService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private NotificationStatusRepository notificationStatusRepo;

	@Override
	public List<NotificationStatus> findAllNotificationStatuses() {
		return this.notificationStatusRepo.findAll();
	}

	@Override
	public NotificationStatus findNotificationStatusById(Long id) {
		try {
			return this.notificationStatusRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public List<NotificationStatus> findNotificationsStatusesByType(String type) {
		return this.notificationStatusRepo.findByType(type);
	}

	@Override
	public NotificationStatus saveNotificationStatus(NotificationStatus notificationStatus) {
		return this.notificationStatusRepo.save(notificationStatus);
	}

	@Override
	public NotificationStatus updateNotificationStatus(Long id, String type) {
		if (id != null && type != null && !type.isEmpty()) {
			return this.notificationStatusRepo.save(new NotificationStatus(id, type));
		} else {
			return null;
		}
	}

	@Override
	public void deleteNotificationStatusById(Long id) {
		this.notificationStatusRepo.deleteById(id);
	}

	@Override
	public void deleteAllNotificationStatuses() {
		this.notificationStatusRepo.deleteAll();
	}

}
