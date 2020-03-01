package hr.go2.play.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hr.go2.play.DTO.PasswordDTO;
import hr.go2.play.DTO.UserDTO;
import hr.go2.play.entities.Role;
import hr.go2.play.entities.User;
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

	@Value("${application.users.profile-photo-location}")
	String profilePhotoLocation;

	// Login
	// Logout
	// Register
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
    private Commons commons;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

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
			logger.debug(userDto.toString());
			User user = userService.findUserByUsername(userDto.getUsername());
			if (user == null) {
        		logger.error("Invalid username");
            	return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid username supplied"), HttpStatus.BAD_REQUEST);
        	}
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
		    "createdAt": "2018-05-30T16:19:58.016Z",
		    "dateOfBirth": "2019-01-31",
		    "username": "test1",
		    "password": "test1",
		    "enabled": "false"
		}
	 * @param userDto
	 * @return
	 */
    @PostMapping("/createUser")
	public ResponseEntity<String> createUser(@RequestBody UserDTO userDto) {
    	User user = new User();
    	try {
			user = mapper.map(userDto, User.class);
    	} catch (HttpMessageNotReadableException | org.modelmapper.MappingException | NullPointerException e) {
    		logger.error("Invalid JSON", e);
        	return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid JSON"), HttpStatus.BAD_REQUEST);
    	}

		if (userService.findUserByUsername(userDto.getUsername()) != null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("User with username " + user.getUsername() + " already exists!"), HttpStatus.BAD_REQUEST);
		}

		if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("No password information provided!"), HttpStatus.BAD_REQUEST);
        }

		userDetailsService.saveUser(user);
        return new ResponseEntity<String>(commons.JSONfyReturnMessage("User created successfully"), HttpStatus.CREATED);
    }

    /**
	 * Desc: Update existing user
	 * JSON body example:
	 * {
		    "createdAt": "2018-05-30T16:19:58.016Z",
		    "dateOfBirth": "2020-01-01",
		    "username": "test2",
		    "password": "test2",
		    "enabled": "true"
		}
	 * @param UserDTO
	 * @return
	 */
	@PostMapping("/updateUser")
	public ResponseEntity<String> updateUser(@RequestBody UserDTO userDto) {
		User user = userService.findUserByUsername(userDto.getUsername());
    	try {
			if (user == null) {
				return new ResponseEntity<String>(commons.JSONfyReturnMessage("User doesn't exist!"), HttpStatus.BAD_REQUEST);
    		}
			User updatedUser = mapper.map(userDto, User.class);
			updatedUser.setPassword(user.getPassword());
			updatedUser.setId(user.getId());

			userDetailsService.updateUser(updatedUser);

			return new ResponseEntity<String>(commons.JSONfyReturnMessage("User updated!"), HttpStatus.CREATED);
    	} catch (HttpMessageNotReadableException | org.modelmapper.MappingException | NullPointerException | DataIntegrityViolationException e) {
    		logger.error("Invalid JSON", e);
        	return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid JSON"), HttpStatus.BAD_REQUEST);
    	}
	}

	/**
	 * Desc: Delete existing user
	 * JSON body example:
	 * {
		    "createdAt": "2018-05-30T16:19:58.016Z",
		    "dateOfBirth": "2020-01-01",
		    "username": "test8",
		    "password": "test8",
		    "enabled": "true"
		}
	 * @param UserDTO
	 * @return
	 */
	@PostMapping("/deleteUser")
	public ResponseEntity<String> deleteUser(@RequestBody UserDTO userDto) {
		User user = userService.findUserByUsername(userDto.getUsername());
		try {
			if (user == null) {
				return new ResponseEntity<String>(commons.JSONfyReturnMessage("User doesn't exist!"), HttpStatus.BAD_REQUEST);
			}
			userDetailsService.deleteUser(user);
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("User deleted!"), HttpStatus.CREATED);
		} catch (HttpMessageNotReadableException | org.modelmapper.MappingException | NullPointerException | DataIntegrityViolationException e) {
			logger.error("Invalid JSON", e);
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid JSON"), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getUser/{username}")
	public ResponseEntity<?> getUser(@PathVariable String username) {
		User user = userService.findUserByUsername(username);
		if (user != null) {
			user.setPassword(null);
			UserDTO userDTO = mapper.map(user, UserDTO.class);
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Unable to find user", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * Desc: Change password to existing user
	 * JSON body example:
	 * {
		    "username": "test1",
		    "oldPassword": "test1",
		    "newPassword": "test10"
		}
	 * @param PasswordDTO
	 * @return
	 */
	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody PasswordDTO passwordDTO) {
		User user = userService.findUserByUsername(passwordDTO.getUsername());
		try {
			if (user == null) {
				return new ResponseEntity<String>(commons.JSONfyReturnMessage("User doesn't exist!"), HttpStatus.BAD_REQUEST);
			}
			if (passwordDTO.getOldPassword() == null || passwordDTO.getOldPassword().isEmpty()) {
				return new ResponseEntity<String>(commons.JSONfyReturnMessage("Old password not provided!"), HttpStatus.BAD_REQUEST);
			}
			if (passwordDTO.getNewPassword() == null || passwordDTO.getNewPassword().isEmpty()) {
				return new ResponseEntity<String>(commons.JSONfyReturnMessage("New password not provided!"), HttpStatus.BAD_REQUEST);
			}
			if (!bCryptPasswordEncoder.matches(passwordDTO.getOldPassword(), user.getPassword())) {
				return new ResponseEntity<String>(commons.JSONfyReturnMessage("Incorrect old password!"), HttpStatus.BAD_REQUEST);
			}
			user.setPassword(bCryptPasswordEncoder.encode(passwordDTO.getNewPassword()));
			userDetailsService.updateUser(user);
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Password updated!"), HttpStatus.CREATED);
		} catch (HttpMessageNotReadableException | org.modelmapper.MappingException | NullPointerException | DataIntegrityViolationException e) {
			logger.error("Invalid JSON", e);
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid JSON"), HttpStatus.BAD_REQUEST);
		}
	}

	// profile photo - add
	@PostMapping(value = "/uploadProfilePhoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadProfilePhoto(@RequestParam(name = "profilePhoto", required = false) MultipartFile profilePhoto, @RequestParam(name = "username") String username) {
		logger.debug("/api/user/uploadProfilePhoto Started");

		if (username == null || username.isEmpty()) {
            return new ResponseEntity<String>(commons.JSONfyReturnMessage("No username provided"), HttpStatus.BAD_REQUEST);
        }

		// does this user already have a profile photo?
		User user = userService.findUserByUsername(username);
		if (user != null) {
			if (user.getProfilePhoto() != null) {
				// delete the photo
				try {
					Files.delete(Paths.get(profilePhotoLocation + "/" + user.getProfilePhoto()));
				} catch (NoSuchFileException e) {
					// carry on
				} catch (IOException e) {
					logger.error("Unable to delete previous profile photo", e);
					return new ResponseEntity<String>(commons.JSONfyReturnMessage("Unable to delete previous profile photo. " + e.getMessage()), HttpStatus.BAD_REQUEST);
				}
			}
		} else {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Unable to find user"), HttpStatus.BAD_REQUEST);
		}

		// if no profile photo provided, delete the existing one
		if (profilePhoto == null || profilePhoto.isEmpty()) {
			// already deleted in previous step
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Profile photo successfuly removed"), HttpStatus.OK);
		}

		try {
			// store the file
			byte[] bytes = profilePhoto.getBytes();
			Path path = Paths.get(profilePhotoLocation + "/" + username + "_" + profilePhoto.getOriginalFilename());
			Files.write(path, bytes);

			// store the data about the file info to database
			user.setProfilePhoto(username + "_" + profilePhoto.getOriginalFilename());
			userService.updateUser(user.getId(), user);
		} catch (IOException e) {
			logger.error("Unable to save file", e);
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Unable to save file"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Unable to store file", e);
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Unable to store file. " + e.getMessage()), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(commons.JSONfyReturnMessage("Successfully uploaded - " + profilePhoto.getOriginalFilename()), HttpStatus.OK);
	}

	// profile photo - fetch
	@GetMapping(value = "/getProfilePhoto")
	@ResponseBody
	public ResponseEntity<?> getProfilePhoto(@RequestParam(name = "username") String username) {
		logger.debug("/api/user/getProfilePhoto Started");
		// does this user have a profile photo?
		User user = userService.findUserByUsername(username);
		if(user != null) {
			if(user.getProfilePhoto() != null) {
			File profilePhoto = new File(profilePhotoLocation + "/" + user.getProfilePhoto());
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	        headers.add("Pragma", "no-cache");
	        headers.add("Expires", "0");
	        headers.setContentLength(profilePhoto.length());
				headers.setContentType(MediaType.IMAGE_PNG);

			InputStreamResource resource = null;
			try {
				resource = new InputStreamResource(new FileInputStream(profilePhoto));
			} catch (FileNotFoundException e) {
				logger.error("Unable to find profile photo", e);
				return new ResponseEntity<String>(commons.JSONfyReturnMessage("Unable to find profile photo"), HttpStatus.BAD_REQUEST);
			}

			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(commons.JSONfyReturnMessage("No profile photo found"), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Unable to find user"), HttpStatus.BAD_REQUEST);
		}
	}

}