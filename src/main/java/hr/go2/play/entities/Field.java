package hr.go2.play.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "fields")
public class Field {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sport_id")
	private Sports sport;

	@OneToMany()
	@JoinColumn(name = "field_id")
	private Collection<Term> terms;

	@OneToMany()
	@JoinColumn(name = "field_id")
	private Collection<Camera> cameras;
}
