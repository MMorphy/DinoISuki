package hr.go2.play.entities;

import java.util.Date;

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

	@ManyToOne()
	@JoinColumn(name = "subscription_type_id")
	private SubscriptionType subscriptionType;

}
