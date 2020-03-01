package hr.go2.play.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class VideoDetailsDTO {

	private Long id;

	private Date startedTimestamp;

	private String locationName;

	public VideoDetailsDTO() {
	}

	public VideoDetailsDTO(Long id, Date startedTimestamp, String locationName) {
		super();
		this.id = id;
		this.startedTimestamp = startedTimestamp;
		this.locationName = locationName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartedTimestamp() {
		return startedTimestamp;
	}

	public void setStartedTimestamp(Date startedTimestamp) {
		this.startedTimestamp = startedTimestamp;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}
