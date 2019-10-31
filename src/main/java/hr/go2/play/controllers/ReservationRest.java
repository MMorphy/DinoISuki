package hr.go2.play.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import hr.go2.play.entities.Sports;
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

	//TODO: Reponse date and time doesn't match with date and time in db - wrong timezones?
	@GetMapping("/")
	public ResponseEntity<?> getAllReserved(){
		 List<Term> terms = (List<Term>) termService.findTermsByAvailable(false);
         List<TermDTO> termDTOList = terms.stream().map(term -> mapper.map(term, TermDTO.class)).collect(Collectors.toList());
         
         return new ResponseEntity<>(termDTOList, HttpStatus.OK);
	}
	
	/**
	 * @param frDto
	 * id is field id
	 * {
   "date":"2020-03-23",
   "available":"false",
   "id":"3"
}
	 * @return
	 */
	@GetMapping("/field/reservations/byDate")
	public ResponseEntity<?> getReservationsForFieldByDate(@RequestBody FieldReservationDTO frDto){
		 List<Term> terms = (List<Term>) termService.findTermsByDateAndAvailableAndFieldId(frDto.getDate(), frDto.getAvailable(), frDto.getId());

         List<TermDTO> termDTOList = terms.stream().map(term -> mapper.map(term, TermDTO.class)).collect(Collectors.toList());
         return new ResponseEntity<>(termDTOList, HttpStatus.OK);
	}

	/**
	 * @param frDto
	 * id is sport id
	 * {
   "date":"2020-03-23",
   "available":"false",
   "id":"3"
}
	 * @return
	 */
	@GetMapping("/field/reservations/bySport")
	public ResponseEntity<?> getReservationsForSport(@RequestBody FieldReservationDTO frDto){
		 Sports sport = sportsService.findSportsById(frDto.getId());
		 List<Field> fields = (List<Field>) fieldService.findFieldBySportName(sport.getName());

		 List<Term> terms = new ArrayList<Term>();
		 for (Field field : fields) {
			 terms.addAll((List<Term>)termService.findTermsByAvailableAndField_Id(frDto.getAvailable(), field.getId()));
		 }

         List<TermDTO> termDTOList = terms.stream().map(term -> mapper.map(term, TermDTO.class)).collect(Collectors.toList());
         return new ResponseEntity<>(termDTOList, HttpStatus.OK);
	}

	/**
	 *
	 * @param reservationDto
	 * JSON body example:
{ 
   "termId":"1",
   "userId":"1",
   "fieldId":"1"
}
	 * @return
	 */
	@PostMapping("/reserve")
	public ResponseEntity<String> addReservation (@RequestBody ReservationDTO reservationDto) {
		User user = new User();
		
		if (userService.existsUserById(reservationDto.getUserId())) {
			 user = userService.findUserById(reservationDto.getUserId());
		} else { //create anonymous user
			user.setCreatedAt(new Date());
			user.setEnabled(false);
			user.setUsername("user");
			user.setPassword("Opatija1");
			user.setDateOfBirth(new Date());
			userService.saveUser(user);
		}

		Term term = termService.findTermById(reservationDto.getTermId());
		if (!term.isAvailable()) {
			return new ResponseEntity<String>("Term already reserved!", HttpStatus.BAD_REQUEST);
		}
		
		List<Term> userTerms = (List<Term>) user.getReservedTerms();
		userTerms.add(term);
		
		user.setReservedTerms(userTerms);

		userService.updateUser(user.getId(), user);
		return new ResponseEntity<String>("Term reserved!", HttpStatus.CREATED);
	}

	/**
	 * @param reservationDto
	 * JSON body example:
{ 
   "termId":"1",
   "userId":"1",
   "fieldId":"1"
}
	 * @return
	 */
	@PostMapping("/update")
	public ResponseEntity<String> updateReservation (@RequestBody ReservationDTO reservationDto) {
		User user = userService.findUserById(reservationDto.getUserId());
		Term term = termService.findTermById(reservationDto.getTermId());
		
		List<Term> userTerms = (List<Term>) user.getReservedTerms();
		userTerms.add(term);
		
		user.setReservedTerms(userTerms);

		userService.updateUser(user.getId(), user);
		return new ResponseEntity<String>("Term updated!", HttpStatus.CREATED);
	}

	/**
	 *
	 * @param id
	 * JSON body example:
    { 
   "termId":"1",
   "userId":"1",
   "fieldId":"1"
    }
	 * @return
	 */
	@PostMapping("/delete")
	public ResponseEntity<String> deleteReservation (@RequestBody ReservationDTO reservationDto) {
		if (termService.existsTermById(reservationDto.getTermId())) {
			Term term = termService.findTermById(reservationDto.getTermId());
			User user = userService.findUserById(reservationDto.getUserId());

			List<Term> userTerms = (List<Term>) user.getReservedTerms();
			
			userTerms.removeIf(t -> t.getId().equals(term.getId()));
			term.setAvailable(true);
			user.setReservedTerms(null);
			user.setReservedTerms(userTerms);
			
			userService.updateUser(user.getId(), user);
			
			return new ResponseEntity<String>("Reservation deleted!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Term id doesn't exist!", HttpStatus.BAD_REQUEST);
		}
	}

	private static class ReservationDTO {
		private long termId;
		private long userId;
		private long fieldId;
		
		public ReservationDTO(long termId, long userId, long fieldId) {
			super();
			this.termId = termId;
			this.userId = userId;
			this.fieldId = fieldId;
		}

		public long getTermId() {
			return termId;
		}

		public void setTermDto(long termId) {
			this.termId = termId;
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

	private static class FieldReservationDTO {
		private String date;
		private String available;
		private long id;
		public FieldReservationDTO(String date, String available, long id) {
			super();
			this.date = date;
			this.available = available;
			this.id = id;
		}
		public Date getDate() {
			Date date = null;
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				date = format.parse(this.date);
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public boolean getAvailable() {
			return Boolean.parseBoolean(available);
		}

		public void setAvailable(String available) {
			this.available = available;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
	}
}
