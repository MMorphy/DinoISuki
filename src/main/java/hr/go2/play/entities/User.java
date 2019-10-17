package hr.go2.play.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, updatable = false)
	@CreatedDate
	private Date createdAt;

	@Column(nullable = false)
	private boolean enabled;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "user_id")
	private Collection<Video> paidVideos;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "user_id")
	private Collection<Term> reservedTerms;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "user_id")
	private Collection<Sports> likedSports;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	public User() {

	}

	public User(Long id, Collection<Role> roles, Date createdAt, boolean enabled, Collection<Video> paidVideos,
			Collection<Term> reservedTerms, Collection<Sports> likedSpords, String username, String password,
			Date dateOfBirth) {
		super();
		this.id = id;
		this.roles = roles;
		this.createdAt = createdAt;
		this.enabled = enabled;
		this.paidVideos = paidVideos;
		this.reservedTerms = reservedTerms;
		this.likedSports = likedSpords;
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
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

	public Collection<Sports> getLikedSports() {
		return likedSports;
	}

	public void setLikedSports(Collection<Sports> likedSpords) {
		this.likedSports = likedSpords;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<String> stringRoles = new HashSet<>();
		for (Role role : roles) {
			stringRoles.add(role.getName());
		}
		List<GrantedAuthority> auths = new ArrayList<>();
		for (String role : stringRoles) {
			auths.add(new SimpleGrantedAuthority(role));
		}
		return auths;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

}
