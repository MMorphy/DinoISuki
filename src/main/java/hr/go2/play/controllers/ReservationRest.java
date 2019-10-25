package hr.go2.play.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
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

import hr.go2.play.DTO.FieldDTO;
import hr.go2.play.DTO.TermDTO;
import hr.go2.play.DTO.UserDTO;
import hr.go2.play.entities.Field;
import hr.go2.play.entities.Term;
import hr.go2.play.entities.User;
import hr.go2.play.impl.FieldServiceImpl;
import hr.go2.play.impl.SportsServiceImpl;
import hr.go2.play.impl.TermServiceImpl;
import hr.go2.play.impl.UserServiceImpl;

@RestController
@RequestMapping(path = "/api/reservations", produces = "application/json", method = {RequestMethod.GET, RequestMethod.PUT})
public class ReservationRest {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	ModelMapper mapper = new ModelMapper();

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
	public ResponseEntity<String> addReservation (@RequestBody ReservationDTO reservationDto) {
		Term term = mapper.map(reservationDto.getTermDto(), Term.class);
		User user = mapper.map(reservationDto.getUserDto(), User.class);
		Field field = mapper.map(reservationDto.getFieldDto(), Field.class);
		
		user.setReservedTerms(Stream.of(term).collect(Collectors.toList()));
		field = fieldService.findFieldById(field.getId());
		
		if (user.getId() == null) {
			user.setPassword("Opatija1");
			userService.saveUser(user);
		}
		else {
			userService.updateUser(user.getId(), user);
		}

		termService.saveTerm(term);
		fieldService.updateField(field.getId(), field);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	private static class ReservationDTO {
		private TermDTO termDto;
		private UserDTO userDto;
		private FieldDTO fieldDto;
		
		public ReservationDTO(TermDTO termDto, UserDTO userDto, FieldDTO fieldDto) {
			super();
			this.termDto = termDto;
			this.userDto = userDto;
			this.fieldDto = fieldDto;
		}

		public TermDTO getTermDto() {
			return termDto;
		}

		public void setTermDto(TermDTO termDto) {
			this.termDto = termDto;
		}

		public UserDTO getUserDto() {
			return userDto;
		}

		public void setUserDto(UserDTO userDto) {
			this.userDto = userDto;
		}

		public FieldDTO getFieldDto() {
			return fieldDto;
		}

		public void setFieldDto(FieldDTO fieldDto) {
			this.fieldDto = fieldDto;
		}

	}
}
