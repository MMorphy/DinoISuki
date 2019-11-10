package hr.go2.play.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "notifications")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "src_user_id")
	User sourceUser;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "dest_user_id")
	User destUser;

	@Column(nullable = false)
	String message;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "notification_status_id")
	NotificationStatus status;

	public Notification() {
	}

	public Notification(Long id, User sourceUser, User destUser, String message, NotificationStatus status) {
		super();
		this.id = id;
		this.sourceUser = sourceUser;
		this.destUser = destUser;
		this.message = message;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getSourceUser() {
		return sourceUser;
	}

	public void setSourceUser(User sourceUser) {
		this.sourceUser = sourceUser;
	}

	public User getDestUser() {
		return destUser;
	}

	public void setDestUser(User destUser) {
		this.destUser = destUser;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public NotificationStatus getStatus() {
		return status;
	}

	public void setStatus(NotificationStatus status) {
		this.status = status;
	}

	
}
