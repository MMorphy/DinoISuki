package hr.go2.play.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

	@ManyToOne(fetch=FetchType.EAGER)
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Subscription subscription = (Subscription) obj;
		if (this.id == null) {
			if (subscription.id != null) {
				return false;
			}
		} else if (this.id != subscription.id) {
			return false;
		}
		if (this.valid != subscription.valid) {
			return false;
		}
		if (this.validFrom == null) {
			if (subscription.validFrom != null) {
				return false;
			}
		} else if (!this.validFrom.equals(subscription.validFrom)) {
			return false;
		}
		if (this.validTo == null) {
			if (subscription.validTo != null) {
				return false;
			}
		} else if (!this.validTo.equals(subscription.validTo)) {
			return false;
		}
		if (this.subscriptionType == null) {
			if (subscription.subscriptionType != null) {
				return false;
			}
		} else if (!this.subscriptionType.equals(subscription.subscriptionType)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 11;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((validFrom == null) ? 0 : validFrom.hashCode());
		result = prime * result + ((validTo == null) ? 0 : validTo.hashCode());
		result = prime * result + ((subscriptionType == null) ? 0 : subscriptionType.hashCode());
		return result;
	}

}
