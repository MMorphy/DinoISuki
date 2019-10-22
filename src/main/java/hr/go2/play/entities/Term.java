package hr.go2.play.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name="terms")
public class Term {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Temporal(TemporalType.TIME)
	@Column(name = "time_from")
	private Date timeFrom;

	@Temporal(TemporalType.TIME)
	@Column(name="time_to")
	private Date timeTo;

	@ColumnDefault(value = "false")
	private boolean available;

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name="term_id", nullable = true)
	private Collection<Video> videos;

	public Term() {

	}

	public Term(Long id, Date date, Date timeFrom, Date timeTo, boolean available, Collection<Video> videos) {
		super();
		this.id = id;
		this.date = date;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
		this.available = available;
		this.videos = videos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(Date timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Date getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(Date timeTo) {
		this.timeTo = timeTo;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Collection<Video> getVideos() {
		return videos;
	}

	public void setVideos(Collection<Video> videos) {
		this.videos = videos;
	}

}
