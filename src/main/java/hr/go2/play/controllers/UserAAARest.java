package hr.go2.play.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.go2.play.DTO.UserDTO;
import hr.go2.play.entities.Role;
import hr.go2.play.entities.User;
import hr.go2.play.impl.UserDetailsService;
import hr.go2.play.jwt.JwtTokenProvider;
import hr.go2.play.repositories.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserAAARest {

	//Login
	//Logout
	//Register
	ModelMapper mapper = new ModelMapper();
	
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepo;

    @Autowired
    private UserDetailsService userDetailsService;
 
    /**
     * { 
          "createdAt":"2019/11/19 00:00:00",
          "dateOfBirth":"1995/10/10",
          "username":"test4",
          "password":"test4"
       }
     * @param userDto
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDTO userDto) {
        try {
        	User user = mapper.map(userDto, User.class);
            String username = user.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, user.getPassword()));
            String token = jwtTokenProvider.createToken(username, (Set<Role>) user.getRoles());
            List<Object> model = new ArrayList<>();
            model.add("{\"username\":" +  "\"" + username + "\"}");
            model.add("{\"token\":" + "\"" + token  + "\"}" );
            return new ResponseEntity<String>(model.toString(), HttpStatus.CREATED);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    /**
     * JSON body example:
     * { 
          "createdAt":"2019/11/19 00:00:00",
          "dateOfBirth":"1995/10/10",
          "username":"test4",
          "password":"test4"
       }
     * @param userDto
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDTO userDto) {
    	User user = mapper.map(userDto, User.class);
        if (userRepo.existsByUsername(user.getUsername())) {
       	return new ResponseEntity<String>("User with username: " + user.getUsername() + " already exists", HttpStatus.BAD_REQUEST);
        }
        userDetailsService.saveUser(user);
        List<Object> model = new ArrayList<>();
        model.add("\"User registered successfully\"");
        return new ResponseEntity<String>(model.toString(), HttpStatus.CREATED);
    }
}
