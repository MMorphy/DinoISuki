package hr.go2.play.controllers;

import java.util.Date;
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

import hr.go2.play.DTO.TermDTO;
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

	//TODO 
	@GetMapping("/")
	public ResponseEntity<?> getAllReserved(){
		 List<Term> terms = (List<Term>) termService.findTermsByAvailable(false);
         List<TermDTO> termDTOList = terms.stream().map(term -> mapper.map(term, TermDTO.class)).collect(Collectors.toList());
         
         return new ResponseEntity<>(termDTOList, HttpStatus.OK);
	}
	
	/**
	 *
	 * @param reservationDto
	 * JSON body example:
{ 
   "termDto":{ 
      "id":null,
      "date":"2019-10-28",
      "timeFrom":"15:00:00",
      "timeTo":"16:00:00",
      "available":"false",
      "videos":[ 
         { 
            "id":null,
            "location":""
         }
      ]
   },
   "userId":"1",
   "fieldId":"3"
}
	 * @return
	 */
	@PostMapping("/reserve")
	public ResponseEntity<String> addReservation (@RequestBody ReservationDTO reservationDto) {
		Term term = mapper.map(reservationDto.getTermDto(), Term.class);
		User user = userService.findUserById(reservationDto.getUserId());
		Field field = fieldService.findFieldById(reservationDto.getFieldId());
		
		user.setReservedTerms(Stream.of(term).collect(Collectors.toList()));
		field.setTerms(Stream.of(term).collect(Collectors.toList()));
		
		//create anonymous user
		if (user.getId() == null) {
			user.setCreatedAt(new Date());
			user.setEnabled(false);
			user.setUsername("user");
			user.setPassword("Opatija1");
			user.setDateOfBirth(new Date());
			userService.saveUser(user);
		}
		else {
			userService.updateUser(user.getId(), user);
		}

		termService.saveTerm(term);
		fieldService.updateField(field.getId(), field);
		
		return new ResponseEntity<String>("Term reserved!", HttpStatus.CREATED);
	}

	@PostMapping("/update")
	public ResponseEntity<String> updateReservation (@RequestBody ReservationDTO reservationDto) {
		Term term = mapper.map(reservationDto.getTermDto(), Term.class);
		User user = userService.findUserById(reservationDto.getUserId());
		Field field = fieldService.findFieldById(reservationDto.getFieldId());
		
		userService.updateUser(user.getId(), user);
		termService.updateTerm(term.getId(), term);
		fieldService.updateField(field.getId(), field);
		
		return new ResponseEntity<String>("Term updated!", HttpStatus.CREATED);
	}

	/**
	 *
	 * @param id
	 * JSON body example:
	 * 12
	 *
	 * @return
	 */
	@PostMapping("/delete")
	public ResponseEntity<String> deleteReservation (@RequestBody String id) {
		if (termService.existsTermById(Long.parseLong(id))) {
			termService.deleteTermById(Long.parseLong(id));
			return new ResponseEntity<String>("Term deleted!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Term id doesn't exist!", HttpStatus.BAD_REQUEST);
		}
	}

	private static class ReservationDTO {
		private TermDTO termDto;
		private long userId;
		private long fieldId;
		
		public ReservationDTO(TermDTO termDto, long userId, long fieldId) {
			super();
			this.termDto = termDto;
			this.userId = userId;
			this.fieldId = fieldId;
		}

		public TermDTO getTermDto() {
			return termDto;
		}

		public void setTermDto(TermDTO termDto) {
			this.termDto = termDto;
		}

		public long getUserId() {
			return userId;
		}

		public void setUserId(long userId) {
			this.userId = userId;
		}

		public long getFieldId() {
			return fieldId;
		}

		public void setFieldId(long fieldId) {
			this.fieldId = fieldId;
		}

	}
}
