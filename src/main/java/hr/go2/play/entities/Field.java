package hr.go2.play.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
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

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	@JoinColumn(name = "sport_id")
	private Sports sport;

	@OneToMany(cascade = {CascadeType.MERGE})
	@JoinColumn(name = "field_id")
	private Collection<Term> terms;

	@OneToMany(cascade = {CascadeType.MERGE})
	@JoinColumn(name = "field_id")
	private Collection<Camera> cameras;

	public Field() {

	}

	public Field(Long id, Sports sport, Collection<Term> terms, Collection<Camera> cameras) {
		super();
		this.id = id;
		this.sport = sport;
		this.terms = terms;
		this.cameras = cameras;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Sports getSport() {
		return sport;
	}

	public void setSport(Sports sport) {
		this.sport = sport;
	}

	public Collection<Term> getTerms() {
		return terms;
	}

	public void setTerms(Collection<Term> terms) {
		this.terms = terms;
	}

	public Collection<Camera> getCameras() {
		return cameras;
	}

	public void setCameras(Collection<Camera> cameras) {
		this.cameras = cameras;
	}

}
