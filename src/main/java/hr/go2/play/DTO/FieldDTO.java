package hr.go2.play.DTO;

import java.util.ArrayList;
import java.util.List;

public class FieldDTO {

	private Long id;

	private List<CameraDTO> cameras;

	public FieldDTO() {
		this.cameras = new ArrayList<>();
	}

	public FieldDTO(Long id, List<CameraDTO> cameras) {
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

	public List<CameraDTO> getCameras() {
		return cameras;
	}

	public void setCameras(List<CameraDTO> cameras) {
		this.cameras = cameras;
	}
}
