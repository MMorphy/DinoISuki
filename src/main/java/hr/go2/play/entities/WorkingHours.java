package hr.go2.play.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "working_hours")
public class WorkingHours {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIME)
	@Column(nullable = false, name = "from_time")
	private Date fromTime;

	@Temporal(TemporalType.TIME)
	@Column(nullable = false, name = "to_time")
	private Date toTime;

	public WorkingHours() {

	}

	public WorkingHours(Long id, Date fromTime, Date toTime) {
		super();
		this.id = id;
		this.fromTime = fromTime;
		this.toTime = toTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFromTime() {
		return fromTime;
	}

	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}

	public Date getToTime() {
		return toTime;
	}

	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}

}
