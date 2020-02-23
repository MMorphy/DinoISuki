package hr.go2.play.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.hibernate.boot.MappingException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.go2.play.DTO.ContactInformationDTO;
import hr.go2.play.DTO.UserDTO;
import hr.go2.play.entities.ContactInformation;
import hr.go2.play.entities.Role;
import hr.go2.play.entities.User;
import hr.go2.play.impl.UserDetailsService;
import hr.go2.play.impl.UserServiceImpl;
import hr.go2.play.jwt.JwtTokenProvider;
import hr.go2.play.repositories.ContactInformationRepository;
import hr.go2.play.repositories.RoleRepository;
import hr.go2.play.repositories.UserRepository;

@RestController
@RequestMapping("/api/user")
@Secured("ROLE_USER")
public class UserAAARest {

	//Login
	//Logout
	//Register
	ModelMapper mapper = new ModelMapper();
	Logger logger = LoggerFactory.getLogger(UserAAARest.class);
	
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepo;

    @Autowired
    ContactInformationRepository contactInfoRepo;
    
    @Autowired
    RoleRepository roleRepo;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private UserServiceImpl userService;
 
    /**
     * { 
          "username":"test4",
          "password":"test4"
       }
     * @param userDto
     * @return
     */
    // in db for "test4" pw needs to be value: $2a$10$Z8d8RpMSb4sL7bengKNIBOHHVn/wYRgOfzS4vKnmeUtDAGEYxwre2
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDTO userDto) {
        try {
        	if (!userRepo.existsByUsername(userDto.getUsername())) {
        		logger.error("Invalid username");
            	return new ResponseEntity<String>("{\"message\": \"Invalid username supplied\"}", HttpStatus.BAD_REQUEST);
        	}
        	User user = userService.findUserByUsername(userDto.getUsername());
            String username = user.getUsername();
            Set<String> roles = new HashSet<>();
            for (Role role : user.getRoles()) {
				roles.add(role.getName());
			}
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, userDto.getPassword()));
            String token = jwtTokenProvider.createToken(username, roles);
            List<Object> model = new ArrayList<>();
            model.add("{\"username\":" +  "\"" + username + "\"}");
            model.add("{\"token\":" + "\"" + token  + "\"}" );
            return new ResponseEntity<String>(model.toString(), HttpStatus.CREATED);
        } catch (AuthenticationException e) {
        	logger.error("Invalid password supplied", e);
        	return new ResponseEntity<String>("{\"message\": \"Invalid password supplied\"}", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * JSON body example:
     * {
  "userDto": {
    "createdAt": "2019/11/19 00:00:00",
    "dateOfBirth": "1995/10/10",
    "username": "test4",
    "password": "test4"
  },
  "contactInfoDto": {
    "telephoneNumber": "0800 091 091",
    "email": "ivo.ivic@gmail.com"
  }
}
     * @param userDto
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserWithContactInfoDTO userContactDto) {
    	User user = new User();
    	try {
    		user = mapper.map(userContactDto.getUserDto(), User.class);
    	} catch (HttpMessageNotReadableException | org.modelmapper.MappingException | NullPointerException e) {
    		logger.error("Invalid JSON", e);
        	return new ResponseEntity<String>("{\"message\": \"Invalid JSON\"}", HttpStatus.BAD_REQUEST);
    	}
    	ContactInformation contactInfo = mapper.map(userContactDto.getContactInfoDto(), ContactInformation.class);

    	if (contactInfoRepo.existsByEmail(contactInfo.getEmail())) {
    		return new ResponseEntity<String>("{\"message\": \"Email address already registered: " + contactInfo.getEmail() + "\"}", HttpStatus.BAD_REQUEST);
    	}

        if (userRepo.existsByUsername(user.getUsername())) {
       	return new ResponseEntity<String>("{\"message\": \"User with username: " + user.getUsername() + " already exists!\"}", HttpStatus.BAD_REQUEST);
        }

        userDetailsService.saveUser(user, contactInfo);
        List<Object> model = new ArrayList<>();
        model.add("\"User registered successfully\"");
        return new ResponseEntity<String>(model.toString(), HttpStatus.CREATED);
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
