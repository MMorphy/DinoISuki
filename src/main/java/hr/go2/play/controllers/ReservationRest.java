package hr.go2.play.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hr.go2.play.DTO.TermDTO;
import hr.go2.play.entities.Term;
import hr.go2.play.impl.FieldServiceImpl;
import hr.go2.play.impl.SportsServiceImpl;
import hr.go2.play.impl.TermServiceImpl;
import hr.go2.play.impl.UserServiceImpl;

@RestController
@RequestMapping(path = "/api/reservations", produces = "application/json", method = {RequestMethod.GET, RequestMethod.PUT})
public class ReservationRest {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TermServiceImpl termService;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private FieldServiceImpl fieldService;
	
	@Autowired
	private SportsServiceImpl sportsService;
	
	//Create new reservation for current user
	//Create new reservation for anonymous user
	//Update reservation for current user
	//Update reservation for anonymous user
	//Delete reservation for current user
	//Delete reservation for anonymous user
	//Get all reservations for field by time
	//Get all reservations for sport
	@GetMapping("/")
	public ResponseEntity<String> getAllReserved(){
		
		return new ResponseEntity<String>("Good", HttpStatus.OK);
	}
	
	@PostMapping("/reserve")
	public void addReservation (@RequestBody TermDTO termDto) {
		
		
	}
}
