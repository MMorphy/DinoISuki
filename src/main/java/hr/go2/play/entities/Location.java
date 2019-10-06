package hr.go2.play.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "locations")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "working_hours_id")
	private WorkingHours hours;

	@OneToMany()
	@JoinColumn(name = "location_id")
	private Collection<Field> fields;

	@Column(nullable = false)
	private String address;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contact_user_id")
	private User contactUser;
}
