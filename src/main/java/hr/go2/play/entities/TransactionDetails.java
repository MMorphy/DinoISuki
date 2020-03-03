package hr.go2.play.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "transaction_details")
public class TransactionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false, unique = true)
	private String transactionId;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Column(nullable = false)
	private TransactionStatus transactionStatus;

	public TransactionDetails() {
	}

	public TransactionDetails(Long id, String username, String transactionId, Date timestamp, TransactionStatus transactionStatus) {
		this.id = id;
		this.username = username;
		this.transactionId = transactionId;
		this.timestamp = timestamp;
		this.transactionStatus = transactionStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

}
