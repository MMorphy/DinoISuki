package hr.go2.play.DTO;

public class SubscriptionDTO {

	private Long id;

	private String valid;

	private String validFrom;

	private String validTo;

	private SubscriptionTypeDTO subscriptionType;

	public SubscriptionDTO() {
	}

	public SubscriptionDTO(Long id, String valid, String validFrom, String validTo,
			SubscriptionTypeDTO subscriptionType) {
		super();
		this.id = id;
		this.valid = valid;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.subscriptionType = subscriptionType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	public SubscriptionTypeDTO getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(SubscriptionTypeDTO subscriptionType) {
		this.subscriptionType = subscriptionType;
	}
}
