package hr.go2.play.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hr.go2.play.DTO.NotificationDTO;
import hr.go2.play.entities.Notification;
import hr.go2.play.entities.NotificationStatus;
import hr.go2.play.entities.User;
import hr.go2.play.services.NotificationService;
import hr.go2.play.services.NotificationStatusService;
import hr.go2.play.services.UserService;
import hr.go2.play.util.Commons;

@RestController
@RequestMapping(path = "/api/notifications")
public class NotificationRest {

	Logger logger = LoggerFactory.getLogger(getClass());

	ModelMapper mapper = new ModelMapper();

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private UserService userService;

	@Autowired
	private Commons commons;

	@Autowired
	private NotificationStatusService notificationStatusService;

	/*
	 * Description: Fetch notifications
	 * Filtering options: by source user, destination user or both
	 * Call example:
	 * https://localhost:8443/api/notifications/getNotifications?srcUser=test6&destUser=test5
	 *
	 */
	@GetMapping("/getNotifications")
	public ResponseEntity<?> getNotifications(@RequestParam(name = "srcUser", required = false) String srcUserString, @RequestParam(name = "destUser", required = false) String destUserString) {
		logger.debug("/api/notifications/getNotifications Started");
		User srcUser = null;
		if (srcUserString != null && !srcUserString.isEmpty()) {
			srcUser = userService.findUserByUsername(srcUserString);
		}
		User destUser = null;
		if (destUserString != null && !destUserString.isEmpty()) {
			destUser = userService.findUserByUsername(destUserString);
		}

		List<Notification> notifications = notificationService.findNotificationsBySourceUserAndDestUser(srcUser, destUser);
		logger.debug("/api/notifications/getNotifications Found " + notifications.size() + " notifications");
		List<NotificationDTO> notificationsDTOList = notifications.stream().map(notification -> new NotificationDTO(notification.getId(), notification.getCreatedAt(), notification.getDestUser() != null ? notification.getDestUser().getUsername() : null, notification.getSourceUser() != null ? notification.getSourceUser().getUsername() : null, notification.getSubject(), notification.getMessage(), notification.getStatus() != null ? notification.getStatus().getType() : null)).collect(Collectors.toList());
		logger.debug("/api/notifications/getNotifications Finished");
		return new ResponseEntity<>(notificationsDTOList, HttpStatus.OK);
	}

	/*
	 * Description: Add notification
	 * Input params: NotificationDTO ( id is ignored )
	 * {
	 * 		"id" : null,
	 * 		"createdAt": "2020/01/02 00:00:00",
	 * 		"destUser": "test4",
	 * 		"srcUser": "test4",
	 * 		"subject": "bbsubject4",
	 * 		"message": "nnmess",
	 * 		"notificationType": "test"
	 * }
	 * Call example:
	 * https://localhost:8443/api/notifications/addNotification
	 *
	 */
	@Transactional
	@PostMapping("/addNotification")
	public ResponseEntity<?> addNotification(@RequestBody NotificationDTO notificationDTO) {
		logger.debug("/api/notifications/addNotification Started");
		Notification notification = getNotificationFromNotificationDTO(notificationDTO);
		notification.setId(null);
		notificationService.saveNotification(notification);
		logger.debug("/api/notifications/addNotification Finished");
		return new ResponseEntity<>(commons.JSONfyReturnMessage("Notification added"), HttpStatus.OK);
	}

	/*
	 * Description: Add notification
	 * Input params: NotificationDTO ( updated by id )
	 * {
	 * 		"id" : 1,
	 * 		"createdAt": "2020/01/02 00:00:00",
	 * 		"destUser": "test4",
	 * 		"srcUser": "test4",
	 * 		"subject": "bbsubject4",
	 * 		"message": "nnmess",
	 * 		"notificationType": "test"
	 * }
	 * Call example:
	 * https://localhost:8443/api/notifications/updateNotification
	 *
	 */
	@Transactional
	@PostMapping("/updateNotification")
	public ResponseEntity<?> updateNotification(@RequestBody NotificationDTO notificationDTO) {
		logger.debug("/api/notifications/updateNotification Started");
		Notification notification = getNotificationFromNotificationDTO(notificationDTO);
		notificationService.updateNotification(notification);
		logger.debug("/api/notifications/updateNotification Finished");
		return new ResponseEntity<>(commons.JSONfyReturnMessage("Notification updated"), HttpStatus.OK);
	}

	private Notification getNotificationFromNotificationDTO(NotificationDTO notificationDTO) {
		User srcUser = null;
		if (notificationDTO.getSrcUser() != null && !notificationDTO.getSrcUser().isEmpty()) {
			srcUser = userService.findUserByUsername(notificationDTO.getSrcUser());
		}
		User destUser = null;
		if (notificationDTO.getDestUser() != null && !notificationDTO.getDestUser().isEmpty()) {
			destUser = userService.findUserByUsername(notificationDTO.getDestUser());
		}
		Date createdAt = notificationDTO.getCreatedAt();
		if (createdAt == null) {
			createdAt = new Date();
		}
		List<NotificationStatus> notificationStatusList = notificationStatusService.findNotificationsStatusesByType(notificationDTO.getNotificationType());

		Notification notification = new Notification(notificationDTO.getId(), createdAt, srcUser, destUser, notificationDTO.getSubject(), notificationDTO.getMessage(), notificationStatusList.isEmpty() ? null : notificationStatusList.get(0));
		return notification;
	}

	/*
	 * Description: Delete notifications
	 * Input params: list of notification ids
	 * Call example:
	 * https://localhost:8443/api/notifications/deleteNotifications?notificationIdList=1,2
	 *
	 */
	@Transactional
	@GetMapping("/deleteNotifications")
	public ResponseEntity<?> deleteNotifications(@RequestParam(name = "notificationIdList") List<Long> notificationIdList) {
		logger.debug("/api/notifications/deleteNotifications Started");
		notificationService.deleteMultipleNotificationById(notificationIdList);
		logger.debug("/api/notifications/deleteNotifications Finished");
		return new ResponseEntity<>(commons.JSONfyReturnMessage("Notifications deleted"), HttpStatus.OK);
	}

}
