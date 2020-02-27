package hr.go2.play.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NotificationDTO {
	private Long id;
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	private Date createdAt;
	private String destUser;
	private String srcUser;
	private String subject;
	private String message;
	private String notificationType;

	public NotificationDTO() {
	}

	public NotificationDTO(Long id, Date createdAt, String destUser, String srcUser, String subject, String message, String notificationType) {
		this.id = id;
		this.createdAt = createdAt;
		this.destUser = destUser;
		this.srcUser = srcUser;
		this.subject = subject;
		this.message = message;
		this.notificationType = notificationType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getDestUser() {
		return destUser;
	}

	public void setDestUser(String destUser) {
		this.destUser = destUser;
	}

	public String getSrcUser() {
		return srcUser;
	}

	public void setSrcUser(String srcUser) {
		this.srcUser = srcUser;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

}
