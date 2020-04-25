package hr.go2.play.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

@Entity
@Table(name = "quiz_questions")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class QuizQuestions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Date createdAt = new Date();

	@Column(nullable = false)
	private int noOfQuestions;

	@Column(nullable = false)
	private QuizStatus status;

	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb", nullable = false)
	private Object questions;

	public QuizQuestions() {

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

}
