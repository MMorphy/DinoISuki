package hr.go2.play.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "subscriptions")
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private boolean valid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "valid_from")
	private Date validFrom;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "valid_to")
	private Date validTo;

	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name = "subscription_type_id")
	private SubscriptionType subscriptionType;

	public Subscription() {

	}

	public Subscription(Long id, boolean valid, Date validFrom, Date validTo, SubscriptionType subscriptionType) {
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

	public boolean isValid() {
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

	public SubscriptionType getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(SubscriptionType subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

}
