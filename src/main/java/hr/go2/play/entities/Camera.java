package hr.go2.play.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cameras")
public class Camera {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "camera_id")
	private Collection<Video> videos;

	@Column(nullable = false)
	private boolean available = true;

	public Camera() {
	}

	public Camera(Long id, String name, Collection<Video> videos, boolean available) {
		super();
		this.id = id;
		this.name = name;
		this.videos = videos;
		this.available = available;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Video> getVideos() {
		return videos;
	}

	public void setVideos(Collection<Video> videos) {
		this.videos = videos;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean addVideo(Video video) {
		return this.videos.add(video);
	}

	public boolean removeVideo(Video video) {
		return this.videos.remove(video);
	}
}
