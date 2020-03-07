package hr.go2.play.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/*
 * Possible entities will be:
 * 		WORKDAY
 * 		HOLYDAY
 * 		WEEKEND_SATURDAY
 * 		WEEKEND_SUNDAY
 * 
 * If there are no working hours for a day type it means it is closed
 */
@Entity
@Table(name="day_types")
public class DayType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String type;

	public DayType() {	}

	public DayType(Long id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
