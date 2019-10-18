package hr.go2.play.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/reservations", produces = "application/json")
public class ReservationRest {

	Logger logger = LoggerFactory.getLogger(getClass());
	
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
}
