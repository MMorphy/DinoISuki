package hr.go2.play.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "subscription_types")
public class SubscriptionType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Float price;

	public SubscriptionType() {

	}

	public SubscriptionType(Long id, String name, Float price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
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

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
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
		SubscriptionType subscriptionType = (SubscriptionType) obj;
		if (this.id == null) {
			if (subscriptionType.id != null) {
				return false;
			}
		} else if (this.id != subscriptionType.id) {
			return false;
		}
		if (this.name == null) {
			if (subscriptionType.name != null) {
				return false;
			}
		} else if (!this.name.equals(subscriptionType.name)) {
			return false;
		}
		if (this.price == null) {
			if (subscriptionType.price != null) {
				return false;
			}
		} else if (this.price.compareTo(subscriptionType.price) != 0) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 11;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}
}
