package hr.go2.play.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SubscriptionDTO {

	private Long id;

	private boolean valid;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "CET")
	private Date validFrom;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "CET")
	private Date validTo;

	private String subscriptionTypeName;

	private String username;

	public SubscriptionDTO() {
	}

	public SubscriptionDTO(Long id, boolean valid, Date validFrom, Date validTo, String subscriptionTypeName, String username) {
		super();
		this.id = id;
		this.valid = valid;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.subscriptionTypeName = subscriptionTypeName;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public String getSubscriptionTypeName() {
		return subscriptionTypeName;
	}

	public void setSubscriptionTypeName(String subscriptionTypeName) {
		this.subscriptionTypeName = subscriptionTypeName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
