package hr.go2.play.DTO;

import java.util.ArrayList;
import java.util.List;

public class FieldDTO {

	private Long id;

	private SportsDTO sport;

	private List<TermDTO> terms;

	private List<CameraDTO> cameras;

	public FieldDTO() {
		this.terms = new ArrayList<>();
		this.cameras = new ArrayList<>();
	}

	public FieldDTO(Long id, SportsDTO sport, List<TermDTO> terms, List<CameraDTO> cameras) {
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

	public SportsDTO getSport() {
		return sport;
	}

	public void setSport(SportsDTO sport) {
		this.sport = sport;
	}

	public List<TermDTO> getTerms() {
		return terms;
	}

	public void setTerms(List<TermDTO> terms) {
		this.terms = terms;
	}

	public List<CameraDTO> getCameras() {
		return cameras;
	}

	public void setCameras(List<CameraDTO> cameras) {
		this.cameras = cameras;
	}
}
