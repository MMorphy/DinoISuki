package hr.go2.play.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class VideoDTO {

	private Long id;

	private String location;

	@JsonFormat(pattern = "mm:hh dd/MM/yyyy", timezone = "CET")
	private Date startedTimestamp;

	public VideoDTO() {
	}

	public VideoDTO(Long id, String location, Date startedTimestamp) {
		super();
		this.id = id;
		this.location = location;
		this.startedTimestamp = startedTimestamp;
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

	public Date getStartedTimestamp() {
		return startedTimestamp;
	}

	public void setStartedTimestamp(Date startedTimestamp) {
		this.startedTimestamp = startedTimestamp;
	}
}
