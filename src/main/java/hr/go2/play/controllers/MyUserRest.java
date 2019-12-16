package hr.go2.play.controllers;

import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.go2.play.DTO.ContactInformationDTO;
import hr.go2.play.DTO.UserDTO;
import hr.go2.play.entities.ContactInformation;
import hr.go2.play.entities.User;
import hr.go2.play.impl.ContactInformationServiceImpl;
import hr.go2.play.impl.UserDetailsService;
import hr.go2.play.impl.UserServiceImpl;
import hr.go2.play.repositories.ContactInformationRepository;
import hr.go2.play.repositories.UserRepository;

@RestController
@RequestMapping("/api/myUser")
@Secured("ROLE_USER")
public class MyUserRest {
	
	private ModelMapper mapper = new ModelMapper();
	private Logger logger = LoggerFactory.getLogger(MyUserRest.class);
	
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ContactInformationRepository contactInfoRepo;
    
    @Autowired
    private ContactInformationServiceImpl contactInfoService;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    /**
     * JSON body example:
     * {
  "userDto": {
  	"id":"2",
    "createdAt": "2019/11/19 00:00:00",
    "dateOfBirth": "1995/10/10",
    "username": "test2",
    "password": "test2"
  },
  "contactInfoDto": {
  	"id":"2",
    "telephoneNumber": "0800 091 091",
    "email": "ivan.ivanovic@gmail.com"
  }
}
     * @param userContactInfoDto
     * @return
     */
	@PostMapping("/update/user")
	public ResponseEntity<String> updateUser (@RequestBody UserWithContactInfoDTO userContactInfoDto) {
    	User user = new User();
    	try {
    		if (!userRepo.existsByUsername(userContactInfoDto.getUserDto().getUsername())) {
    			return new ResponseEntity<String>("{\"message\": \"Username doesn't exist!\"}", HttpStatus.BAD_REQUEST);
    		}
    		user = mapper.map(userContactInfoDto.getUserDto(), User.class);
    	} catch (HttpMessageNotReadableException | org.modelmapper.MappingException | NullPointerException | DataIntegrityViolationException e) {
    		logger.error("Invalid JSON", e);
        	return new ResponseEntity<String>("{\"message\": \"Invalid JSON\"}", HttpStatus.BAD_REQUEST);
    	}
		ContactInformation contactInfo = mapper.map(userContactInfoDto.getContactInfoDto(), ContactInformation.class);
		
		try {
			contactInfoService.updateContactInformation(contactInfo.getId(), contactInfo);
			userDetailsService.saveUser(user, contactInfo);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>("{\"message\": \"Invalid JSON\"}", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("User updated!", HttpStatus.CREATED);
	}
	
	@GetMapping("/getUser/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username) {
		if(userRepo.existsByUsername(username)) {
			User user = userService.findUserByUsername(username);
			user.getLikedSports();
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(new User(), HttpStatus.NOT_ACCEPTABLE);
		}
	}

    private static class UserWithContactInfoDTO {
    	private UserDTO userDto;
    	private ContactInformationDTO contactInfoDto;
		public UserWithContactInfoDTO() {
			
		}
		public UserWithContactInfoDTO(UserDTO userDto, ContactInformationDTO contactInfoDto) {
			super();
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

}
