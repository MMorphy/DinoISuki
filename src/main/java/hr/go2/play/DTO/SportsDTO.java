package hr.go2.play.DTO;

public class SportsDTO {

	private Long id;

	private String name;

	public SportsDTO() {
	}

	public SportsDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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
}
