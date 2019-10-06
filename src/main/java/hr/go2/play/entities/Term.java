package hr.go2.play.entities;

import java.util.Collection;
import java.util.Date;

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

	@OneToMany
	@JoinColumn(name="term_id", nullable = true)
	private Collection<Video> videos;
}
