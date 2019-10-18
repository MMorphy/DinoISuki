package hr.go2.play.DTO;

public class VideoDTO {

	private Long id;

	private String location;

	public VideoDTO() {
	}

	public VideoDTO(Long id, String location) {
		super();
		this.id = id;
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
