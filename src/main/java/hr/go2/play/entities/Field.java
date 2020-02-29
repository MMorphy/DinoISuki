package hr.go2.play.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "fields")
public class Field {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "field_id")
	private Collection<Camera> cameras;

	public Field() {}

	public Field(Long id, Collection<Camera> cameras) {
		super();
		this.id = id;
		this.cameras = cameras;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<Camera> getCameras() {
		return cameras;
	}

	public void setCameras(Collection<Camera> cameras) {
		this.cameras = cameras;
	}
}
