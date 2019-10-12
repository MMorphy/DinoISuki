package hr.go2.play.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
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

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name = "user_id")
	private Collection<Video> paidVideos;

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name="user_id")
	private Collection<Term> reservedTerms;

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name="user_id")
	private Collection<Sports> likedSpords;

	public User() {

	}

	public User(Long id, Collection<Role> roles, Date createdAt, boolean enabled, Collection<Video> paidVideos,
			Collection<Term> reservedTerms, Collection<Sports> likedSpords) {
		super();
		this.id = id;
		this.roles = roles;
		this.createdAt = createdAt;
		this.enabled = enabled;
		this.paidVideos = paidVideos;
		this.reservedTerms = reservedTerms;
		this.likedSpords = likedSpords;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Collection<Video> getPaidVideos() {
		return paidVideos;
	}

	public void setPaidVideos(Collection<Video> paidVideos) {
		this.paidVideos = paidVideos;
	}

	public Collection<Term> getReservedTerms() {
		return reservedTerms;
	}

	public void setReservedTerms(Collection<Term> reservedTerms) {
		this.reservedTerms = reservedTerms;
	}

	public Collection<Sports> getLikedSpords() {
		return likedSpords;
	}

	public void setLikedSpords(Collection<Sports> likedSpords) {
		this.likedSpords = likedSpords;
	}

}
