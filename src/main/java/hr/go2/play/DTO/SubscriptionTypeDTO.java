package hr.go2.play.DTO;

public class SubscriptionTypeDTO {

	private String id;

	private String name;

	public SubscriptionTypeDTO() {
	}

	public SubscriptionTypeDTO(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
