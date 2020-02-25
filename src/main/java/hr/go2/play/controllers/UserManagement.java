package hr.go2.play.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.go2.play.DTO.UserDTO;
import hr.go2.play.DTO.UserWithContactInfoDTO;
import hr.go2.play.entities.ContactInformation;
import hr.go2.play.entities.Role;
import hr.go2.play.entities.User;
import hr.go2.play.impl.ContactInformationServiceImpl;
import hr.go2.play.impl.UserDetailsService;
import hr.go2.play.impl.UserServiceImpl;
import hr.go2.play.jwt.JwtTokenProvider;
import hr.go2.play.repositories.ContactInformationRepository;
import hr.go2.play.repositories.RoleRepository;
import hr.go2.play.repositories.UserRepository;
import hr.go2.play.util.Commons;

@RestController
@RequestMapping("/api/user")
@Secured("ROLE_USER")
public class UserManagement {

	//Login
	//Logout
	//Register
	ModelMapper mapper = new ModelMapper();
	Logger logger = LoggerFactory.getLogger(UserManagement.class);
	
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
    
    @Autowired
    private ContactInformationServiceImpl contactInfoService;

    @Autowired
    private Commons commons;
 
    /**
     * Desc: User login
     * { 
          "username":"test4",
          "password":"test4"
       }
     * @param userDto
     * @return
     */
    // in db for "test4" pw needs to be value: $2a$10$Z8d8RpMSb4sL7bengKNIBOHHVn/wYRgOfzS4vKnmeUtDAGEYxwre2
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDto) {
        try {
        	if (!userRepo.existsByUsername(userDto.getUsername())) {
        		logger.error("Invalid username");
            	return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid username supplied"), HttpStatus.BAD_REQUEST);
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
        	return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid password supplied"), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Desc: Create new user
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
    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody UserWithContactInfoDTO userContactDto) {
    	User user = new User();
    	try {
    		user = mapper.map(userContactDto.getUserDto(), User.class);
    	} catch (HttpMessageNotReadableException | org.modelmapper.MappingException | NullPointerException e) {
    		logger.error("Invalid JSON", e);
        	return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid JSON"), HttpStatus.BAD_REQUEST);
    	}
    	ContactInformation contactInfo = mapper.map(userContactDto.getContactInfoDto(), ContactInformation.class);

    	if (contactInfoRepo.existsByEmail(contactInfo.getEmail())) {
    		return new ResponseEntity<String>(commons.JSONfyReturnMessage("Email address already registered " + contactInfo.getEmail()) , HttpStatus.BAD_REQUEST);
    	}

        if (userRepo.existsByUsername(user.getUsername())) {
       	return new ResponseEntity<String>(commons.JSONfyReturnMessage("User with username " + user.getUsername() + " already exists!"), HttpStatus.BAD_REQUEST);
        }

        userDetailsService.saveUser(user, contactInfo);
        return new ResponseEntity<String>(commons.JSONfyReturnMessage("User created successfully"), HttpStatus.CREATED);
    }
    
    /**
     * Desc: Update existing user
     * JSON body example:
     * {
		  "userDto": {
		    "dateOfBirth": "1995/10/10",
		    "username": "Leo",
		    "password": "1234"
		  },
		  "contactInfoDto": {
		    "telephoneNumber": "060222",
		    "email": "krivi.mail@mail.com"
		  }
		}
     * @param userContactInfoDto
     * @return
     */
	@PostMapping("/updateUser")
	public ResponseEntity<String> updateUser(@RequestBody UserWithContactInfoDTO userContactInfoDto) {
    	User user = new User();
    	String username = userContactInfoDto.getUserDto().getUsername();
    	try {
    		if (!userRepo.existsByUsername(username)) {
    			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Username doesn't exist!"), HttpStatus.BAD_REQUEST);
    		}
    		user = userService.findUserByUsername(username);
    	} catch (HttpMessageNotReadableException | org.modelmapper.MappingException | NullPointerException | DataIntegrityViolationException e) {
    		logger.error("Invalid JSON", e);
        	return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid JSON"), HttpStatus.BAD_REQUEST);
    	}
		ContactInformation contactInfo = user.getContactInfo();
		
		ContactInformation updatedContactInfo = new ContactInformation();
		updatedContactInfo.setEmail(userContactInfoDto.getContactInfoDto().getEmail());
		updatedContactInfo.setTelephoneNumber(userContactInfoDto.getContactInfoDto().getTelephoneNumber());
		
		User updatedUser = new User();
		updatedUser.setCreatedAt(user.getCreatedAt());
		updatedUser.setDateOfBirth(userContactInfoDto.getUserDto().getDateOfBirth());
		updatedUser.setEnabled(user.isEnabled());
		updatedUser.setPassword(userContactInfoDto.getUserDto().getPassword());
		updatedUser.setRoles(user.getRoles());
		updatedUser.setContactInfo(updatedContactInfo);
		updatedUser.setUsername(username);
		
		try {
			contactInfoService.updateContactInformation(contactInfo.getId(), updatedContactInfo);
			userDetailsService.updateUser(user.getId(), updatedUser, updatedContactInfo);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid JSON"), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(commons.JSONfyReturnMessage("User updated!"), HttpStatus.CREATED);
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

}
