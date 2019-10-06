package hr.go2.play.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany
    @JoinTable( 
            name = "user_roles", 
            joinColumns = @JoinColumn(
              name = "user_id", referencedColumnName = "id"), 
            inverseJoinColumns = @JoinColumn(
              name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, updatable = false)
	@CreatedDate
	private Date createdAt;

	@Column(nullable = false)
	private boolean enabled;

	@OneToMany
	@JoinColumn(name = "user_id")
	private Collection<Video> paidVideos;

	@OneToMany
	@JoinColumn(name="user_id")
	private Collection<Term> reservedTerms;

	@OneToMany
	@JoinColumn(name="user_id")
	private Collection<Sports> likedSpords;
}
