package hr.go2.play.DTO;

import java.util.Date;

import hr.go2.play.entities.QuizStatus;

public class QuizDTO {

	private long id;
	private Date createdAt;
	private String name;
	private int noOfQuestions;
	private QuizStatus status;
	private Object questions;

	public QuizDTO() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getNoOfQuestions() {
		return noOfQuestions;
	}

	public void setNoOfQuestions(int noOfQuestions) {
		this.noOfQuestions = noOfQuestions;
	}

	public QuizStatus getStatus() {
		return status;
	}

	public void setStatus(QuizStatus status) {
		this.status = status;
	}

	public Object getQuestions() {
		return questions;
	}

	public void setQuestions(Object questions) {
		this.questions = questions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
