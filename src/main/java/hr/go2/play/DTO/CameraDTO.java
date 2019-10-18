package hr.go2.play.DTO;

import java.util.ArrayList;
import java.util.List;

public class CameraDTO {

	private Long id;

	private String name;

	private List<VideoDTO> videos;

	public CameraDTO() {
		super();
		this.videos = new ArrayList<>();
	}

	public CameraDTO(Long id, String name, List<VideoDTO> videos) {
		super();
		this.id = id;
		this.name = name;
		this.videos = videos;
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

	public List<VideoDTO> getVideos() {
		return videos;
	}

	public void setVideos(List<VideoDTO> videos) {
		this.videos = videos;
	}
}
