package hr.go2.play.DTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	public Date getDate() {
		Date date = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			date = format.parse(this.date);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Date getTimeFrom() {
		Date timeFrom = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("hh:mm");
			timeFrom = format.parse(this.timeFrom);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return timeFrom;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Date getTimeTo() {
		Date timeTo = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("hh:mm");
			timeTo = format.parse(this.timeTo);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return timeTo;
	}

	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}

	public boolean getAvailable() {
		return Boolean.parseBoolean(available);
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
