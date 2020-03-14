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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import hr.go2.play.DTO.ContactInformationDTO;
import hr.go2.play.DTO.PasswordDTO;
import hr.go2.play.DTO.UserDTO;
import hr.go2.play.DTO.UserRoleDTO;
import hr.go2.play.entities.ContactInformation;
import hr.go2.play.entities.Role;
import hr.go2.play.entities.User;
import hr.go2.play.impl.UserServiceImpl;
import hr.go2.play.jwt.JwtTokenProvider;
import hr.go2.play.repositories.ContactInformationRepository;
import hr.go2.play.repositories.RoleRepository;
import hr.go2.play.repositories.UserRepository;
import hr.go2.play.services.ContactInformationService;
import hr.go2.play.services.RoleService;
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
    private UserServiceImpl userService;

    @Autowired
    private Commons commons;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private ContactInformationService contactInformationService;

	@Autowired
	private RoleService roleService;

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
			logger.debug("/api/user/login Started");
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
		String checkUserDetails = checkUserDetails(userDto);
		if (checkUserDetails != null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage(checkUserDetails), HttpStatus.BAD_REQUEST);
		}

		userService.saveUser(user);
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
			String checkUserDetails = checkUserDetails(userDto);
			if (checkUserDetails != null) {
				return new ResponseEntity<String>(commons.JSONfyReturnMessage(checkUserDetails), HttpStatus.BAD_REQUEST);
			}
			User updatedUser = mapper.map(userDto, User.class);
			updatedUser.setPassword(user.getPassword());
			updatedUser.setId(user.getId());
			updatedUser.setContactInformation(user.getContactInformation());
			updatedUser.setSubscriptions(user.getSubscriptions());
			updatedUser.setRoles(user.getRoles());
			updatedUser.setProfilePhoto(user.getProfilePhoto());

			userService.updateUser(updatedUser);

			return new ResponseEntity<String>(commons.JSONfyReturnMessage("User updated!"), HttpStatus.CREATED);
    	} catch (HttpMessageNotReadableException | org.modelmapper.MappingException | NullPointerException | DataIntegrityViolationException e) {
    		logger.error("Invalid JSON", e);
        	return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid JSON"), HttpStatus.BAD_REQUEST);
    	}
	}

	private String checkUserDetails(UserDTO userDTO) {
		Date now = new Date();
		if (userDTO.getCreatedAt() == null || userDTO.getCreatedAt().after(now)) {
			return "Invalid value for createdAt";
		}
		if (userDTO.getDateOfBirth() == null || userDTO.getDateOfBirth().after(now)) {
			return "Invalid value for dateOfBirth";
		}
		return null;
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
			userService.deleteUserById(user.getId());
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
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Unable to find user"), HttpStatus.NOT_ACCEPTABLE);
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
			userService.updateUser(user);
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Password updated!"), HttpStatus.CREATED);
		} catch (HttpMessageNotReadableException | org.modelmapper.MappingException | NullPointerException | DataIntegrityViolationException e) {
			logger.error("Invalid JSON", e);
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid JSON"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Desc: Create new contact info
	 * JSON body example:
	 * {
			"username": "test1",
			"telephoneNumber": "0800 091 092",
			"email": "my.email2 @email.com"
		}
	 * @param ContactInformationDTO
	 * @return
	 */
	@PostMapping("/saveContactInfo")
	public ResponseEntity<String> saveContactInfo(@RequestBody ContactInformationDTO contactInformationDTO) {
		if (contactInformationDTO == null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("No info provided"), HttpStatus.BAD_REQUEST);
		}
		// removing whitespaces
		if (contactInformationDTO.getTelephoneNumber() != null) {
			contactInformationDTO.setTelephoneNumber(contactInformationDTO.getTelephoneNumber().replaceAll("\\s+", ""));
		}
		if (contactInformationDTO.getEmail() != null) {
			contactInformationDTO.setEmail(contactInformationDTO.getEmail().replaceAll("\\s+", ""));
		}

		User user = userService.findUserByUsername(contactInformationDTO.getUsername());
		if (user == null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Unable to find user"), HttpStatus.BAD_REQUEST);
		}
		if (user.getContactInformation() != null) {
			// fill in the missing info with existing data
			if (contactInformationDTO.getEmail() == null || contactInformationDTO.getEmail().isEmpty()) {
				contactInformationDTO.setEmail(user.getContactInformation().getEmail());
			}
			if (contactInformationDTO.getTelephoneNumber() == null || contactInformationDTO.getTelephoneNumber().isEmpty()) {
				contactInformationDTO.setTelephoneNumber(user.getContactInformation().getTelephoneNumber());
			}
		}

		String checkContactInformation = checkContactInformation(contactInformationDTO);
		if (checkContactInformation != null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage(checkContactInformation), HttpStatus.BAD_REQUEST);
		}

		ContactInformation contactInformationExisting = contactInformationService.findContactInformationByEmail(contactInformationDTO.getEmail());
		if (contactInformationExisting != null) {
			// does this existing contactInformation belong to this user or someone else?
			User userExisting = userService.findByContactInformation(contactInformationExisting);
			if (userExisting != null && !userExisting.getUsername().equals(contactInformationDTO.getUsername())) {
				return new ResponseEntity<String>(commons.JSONfyReturnMessage("Email already registered to another user"), HttpStatus.BAD_REQUEST);
			}
		}

		ContactInformation contactInformation = new ContactInformation();
		try {
			contactInformation = mapper.map(contactInformationDTO, ContactInformation.class);
		} catch (HttpMessageNotReadableException | org.modelmapper.MappingException | NullPointerException e) {
			logger.error("Invalid JSON", e);
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid JSON"), HttpStatus.BAD_REQUEST);
		}
		if (user.getContactInformation() != null) {
			contactInformation.setId(user.getContactInformation().getId());
		}
		contactInformation = contactInformationService.saveContactInformation(contactInformation);
		user.setContactInformation(contactInformation);
		userService.updateUser(user);

		return new ResponseEntity<String>(commons.JSONfyReturnMessage("Contact Information saved successfully"), HttpStatus.CREATED);
	}

	private String checkContactInformation(ContactInformationDTO contactInformationDTO) {
		if (contactInformationDTO.getUsername() == null || contactInformationDTO.getUsername().isEmpty()) {
			return "No username provided";
		}
		// fetch existing contact info

		if (contactInformationDTO.getEmail() == null || contactInformationDTO.getEmail().isEmpty()) {
			return "No email provided";
		} else {
			Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
			Matcher matcher = pattern.matcher(contactInformationDTO.getEmail());
			if (!matcher.matches()) {
				return "Not a valid email provided";
			}
		}
		if (contactInformationDTO.getTelephoneNumber() != null && !contactInformationDTO.getTelephoneNumber().isEmpty()) {
			Pattern pattern = Pattern.compile("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$");
			Matcher matcher = pattern.matcher(contactInformationDTO.getTelephoneNumber());
			if (!matcher.matches()) {
				return "Not a valid telephone number provided";
			}
		}
		return null;
	}

	/**
	 * Desc: Delete existing contact info (telephoneNumber and email are ignored)
	 * JSON body example:
	 * {
			"username": "test1",
			"telephoneNumber": "0800 091 092",
			"email": "my.email2 @email.com"
		}
	 * @param ContactInformationDTO
	 * @return
	 */
	@PostMapping("/deleteContactInfo")
	public ResponseEntity<String> deleteContactInfo(@RequestBody ContactInformationDTO contactInformationDTO) {
		User user = userService.findUserByUsername(contactInformationDTO.getUsername());
		if (user == null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Unable to find user"), HttpStatus.BAD_REQUEST);
		}
		if (user.getContactInformation() != null) {
			Long contactInformationId = user.getContactInformation().getId();
			user.setContactInformation(null);
			userService.updateUser(user);
			contactInformationService.deleteContactInformationById(contactInformationId);
		}

		return new ResponseEntity<String>(commons.JSONfyReturnMessage("Contact Information deleted successfully"), HttpStatus.CREATED);
	}

	@GetMapping("/getContactInfo/{username}")
	public ResponseEntity<?> getContactInfo(@PathVariable String username) {
		User user = userService.findUserByUsername(username);
		if (user != null) {
			ContactInformation contactInformation = user.getContactInformation();
			if (contactInformation != null) {
				ContactInformationDTO contactInformationDTO = mapper.map(contactInformation, ContactInformationDTO.class);
				contactInformationDTO.setUsername(username);
				return new ResponseEntity<ContactInformationDTO>(contactInformationDTO, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(commons.JSONfyReturnMessage("No contact information found"), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Unable to find user"), HttpStatus.NOT_ACCEPTABLE);
		}
	}

	// profile photo - add
	@PostMapping(value = "/uploadProfilePhoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadProfilePhoto(@RequestParam(name = "profilePhoto", required = false) MultipartFile profilePhoto, @RequestParam(name = "username") String username) {
		logger.debug("/api/user/uploadProfilePhoto Started");

		if (username == null || username.isEmpty()) {
            return new ResponseEntity<String>(commons.JSONfyReturnMessage("No username provided"), HttpStatus.BAD_REQUEST);
        }

		if (profilePhoto.getSize() > 8388608) {
			logger.debug("Profile foto file size:" + profilePhoto.getSize());
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Profile photo file too large"), HttpStatus.BAD_REQUEST);
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
			userService.updateUser(user);
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
	@GetMapping(value = "/getProfilePhoto", produces = MediaType.IMAGE_PNG_VALUE)
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

				InputStreamResource resource = null;
				try {
					resource = new InputStreamResource(new FileInputStream(profilePhoto));
				} catch (FileNotFoundException e) {
					logger.error("Unable to find profile photo", e);
					return new ResponseEntity<String>(commons.JSONfyReturnMessage("Unable to find profile photo"), HttpStatus.BAD_REQUEST);
				}

				return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
			} else {
				ClassLoader classLoader = new UserManagement().getClass().getClassLoader();
				File defaultProfilePhoto = new File(classLoader.getResource("default/default_profile_photo.jpg").getFile());
				if (defaultProfilePhoto.exists()) {
					HttpHeaders headers = new HttpHeaders();
					headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
					headers.add("Pragma", "no-cache");
					headers.add("Expires", "0");
					headers.setContentLength(defaultProfilePhoto.length());

					InputStreamResource resource = null;
					try {
						resource = new InputStreamResource(new FileInputStream(defaultProfilePhoto));
					} catch (FileNotFoundException e) {
						logger.error("Unable to find profile photo", e);
						return new ResponseEntity<String>(commons.JSONfyReturnMessage("Unable to find profile photo"), HttpStatus.BAD_REQUEST);
					}
					return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
				}

				return new ResponseEntity<String>(commons.JSONfyReturnMessage("No profile photo found"), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Unable to find user"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Desc: Update user roles
	 * JSON body example:
	 * {
			"username": "test2",
			"role": "role_user"
		}
	 * @param UserRoleDTO
	 * @return
	 */
	@PostMapping("/addUserRole")
	public ResponseEntity<String> addUserRole(@RequestBody UserRoleDTO userRoleDTO) {
		if (userRoleDTO == null || userRoleDTO.getUsername() == null || userRoleDTO.getUsername().isEmpty()) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Username not provided"), HttpStatus.BAD_REQUEST);
		}
		if (userRoleDTO.getRole() == null || userRoleDTO.getRole().isEmpty()) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("User role not provided"), HttpStatus.BAD_REQUEST);
		}
		User user = userService.findUserByUsername(userRoleDTO.getUsername());
		if (user == null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("User doesn't exist!"), HttpStatus.BAD_REQUEST);
		}
		Role role = roleService.findRoleByName(userRoleDTO.getRole());
		if (role == null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid role provided"), HttpStatus.BAD_REQUEST);
		}

		if (user.getRoles().contains(role)) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("User already has provided role"), HttpStatus.OK);
		}

		user.getRoles().add(role);
		userService.updateUser(user);
		return new ResponseEntity<String>(commons.JSONfyReturnMessage("User roles updated!"), HttpStatus.OK);
	}

	/**
	 * Desc: Revoke user role
	 * JSON body example:
	 * {
			"username": "test2",
			"role": "role_user"
		}
	 * @param UserRoleDTO
	 * @return
	 */
	@PostMapping("/revokeUserRole")
	public ResponseEntity<String> revokeUserRole(@RequestBody UserRoleDTO userRoleDTO) {
		if (userRoleDTO == null || userRoleDTO.getUsername() == null || userRoleDTO.getUsername().isEmpty()) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Username not provided"), HttpStatus.BAD_REQUEST);
		}
		if (userRoleDTO.getRole() == null || userRoleDTO.getRole().isEmpty()) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("User role not provided"), HttpStatus.BAD_REQUEST);
		}
		User user = userService.findUserByUsername(userRoleDTO.getUsername());
		if (user == null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("User doesn't exist!"), HttpStatus.BAD_REQUEST);
		}
		Role role = roleService.findRoleByName(userRoleDTO.getRole());
		if (role == null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid role provided"), HttpStatus.BAD_REQUEST);
		}

		if (!user.getRoles().contains(role)) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("User does not have provided role"), HttpStatus.OK);
		}

		user.getRoles().remove(role);
		userService.updateUser(user);
		return new ResponseEntity<String>(commons.JSONfyReturnMessage("User roles updated!"), HttpStatus.OK);
	}

}