package hr.go2.play.DTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WorkingHoursDTO {

	private Long id;

	@JsonFormat(pattern="hh:mm:ss")
	private String fromTime;

	@JsonFormat(pattern="hh:mm:ss")
	private String toTime;

	public WorkingHoursDTO() {
	}

	public WorkingHoursDTO(Long id, String fromTime, String toTime) {
		super();
		this.id = id;
		this.fromTime = fromTime;
		this.toTime = toTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFromTime() {
		Date timeFrom = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
			timeFrom = format.parse(this.fromTime);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return timeFrom;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public Date getToTime() {
		Date timeTo = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
			timeTo = format.parse(this.fromTime);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return timeTo;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
}
