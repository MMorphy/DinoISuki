package hr.go2.play.DTO;

public class WorkingHoursDTO {

	private Long id;

	private String fromTime;

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

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
}
