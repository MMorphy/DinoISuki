package hr.go2.play.DTO;

import java.util.List;

public class UserDTO {

	private Long id;

	private List<RoleDTO> roles;

	private String createdAt;

	private String enabled;

	private List<VideoDTO> paidVideos;

	private List<TermDTO> reservedTerms;

	private List<SportsDTO> likedSports;

	private String username;

	private String password;

	private String dateOfBirth;

	public UserDTO() {
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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getEnabled() {
		return enabled;
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

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}
