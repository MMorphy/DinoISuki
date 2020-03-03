package hr.go2.play.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import hr.go2.play.entities.TransactionStatus;

public class TransactionDetailsDTO {

	private Long id;

	private String username;

	private String transactionId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "CET")
	private Date timestamp;

	private TransactionStatus transactionStatus;

	public TransactionDetailsDTO() {
	}

	public TransactionDetailsDTO(Long id, String username, String transactionId, Date timestamp, TransactionStatus transactionStatus) {
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
