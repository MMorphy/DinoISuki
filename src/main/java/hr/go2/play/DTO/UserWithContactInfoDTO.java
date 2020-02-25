package hr.go2.play.DTO;

public class UserWithContactInfoDTO {

	private UserDTO userDto;
	private ContactInformationDTO contactInfoDto;
	
	public UserWithContactInfoDTO() {
	}
	
	public UserWithContactInfoDTO(UserDTO userDto, ContactInformationDTO contactInfoDto) {
		this.userDto = userDto;
		this.contactInfoDto = contactInfoDto;
	}
	
	public UserDTO getUserDto() {
		return userDto;
	}
	
	public void setUserDto(UserDTO userDto) {
		this.userDto = userDto;
	}
	
	public ContactInformationDTO getContactInfoDto() {
		return contactInfoDto;
	}
	
	public void setContactInfoDto(ContactInformationDTO contactInfoDto) {
		this.contactInfoDto = contactInfoDto;
	}
	
}
