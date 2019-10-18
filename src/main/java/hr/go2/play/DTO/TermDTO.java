package hr.go2.play.DTO;

import java.util.ArrayList;
import java.util.List;

public class TermDTO {

	private Long id;

	private String date;

	private String timeFrom;

	private String timeTo;

	private String available;

	private List<VideoDTO> videos;

	public TermDTO() {
		this.videos = new ArrayList<>();
	}

	public TermDTO(Long id, String date, String timeFrom, String timeTo, String available, List<VideoDTO> videos) {
		super();
		this.id = id;
		this.date = date;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
		this.available = available;
		this.videos = videos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public String getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public List<VideoDTO> getVideos() {
		return videos;
	}

	public void setVideos(List<VideoDTO> videos) {
		this.videos = videos;
	}
}
