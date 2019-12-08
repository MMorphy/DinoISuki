package hr.go2.play.DTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserDTO {

	private Long id;

	private List<RoleDTO> roles;

	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private String createdAt;

	private String enabled;

	private List<VideoDTO> paidVideos;

	private List<TermDTO> reservedTerms;

	private List<SportsDTO> likedSports;

	private String username;

	private String password;

	@JsonFormat(pattern="dd/MM/yyyy")
	private String dateOfBirth;

	public UserDTO() {
		this.paidVideos = new ArrayList<>();
		this.reservedTerms = new ArrayList<>();
		this.likedSports = new ArrayList<>();
	}
	
	public UserDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public UserDTO(Long id, List<RoleDTO> roles, String createdAt, String enabled, List<VideoDTO> paidVideos,
			List<TermDTO> reservedTerms, List<SportsDTO> likedSports, String username, String password,
			String dateOfBirth) {
		super();
		this.id = id;
		this.roles = roles;
		this.createdAt = createdAt;
		this.enabled = enabled;
		this.paidVideos = paidVideos;
		this.reservedTerms = reservedTerms;
		this.likedSports = likedSports;
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

	public List<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}

	public Date getCreatedAt() {
		Date createdAt = null;
		try {
			//2019/10/24 13:13:30.183
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			createdAt = format.parse(this.createdAt);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public boolean getEnabled() {
		return Boolean.parseBoolean(enabled);
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public List<VideoDTO> getPaidVideos() {
		return paidVideos;
	}

	public void setPaidVideos(List<VideoDTO> paidVideos) {
		this.paidVideos = paidVideos;
	}

	public List<TermDTO> getReservedTerms() {
		return reservedTerms;
	}

	public void setReservedTerms(List<TermDTO> reservedTerms) {
		this.reservedTerms = reservedTerms;
	}

	public List<SportsDTO> getLikedSports() {
		return likedSports;
	}

	public void setLikedSports(List<SportsDTO> likedSports) {
		this.likedSports = likedSports;
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
		Date dateOfBirth = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			dateOfBirth = format.parse(this.dateOfBirth);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}
