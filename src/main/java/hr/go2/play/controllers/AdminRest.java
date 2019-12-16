package hr.go2.play.controllers;

import java.util.List;
import java.util.stream.Collectors;

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

import hr.go2.play.DTO.CameraDTO;
import hr.go2.play.DTO.LocationDTO;
import hr.go2.play.entities.Camera;
import hr.go2.play.entities.Location;
import hr.go2.play.impl.CameraServiceImpl;
import hr.go2.play.impl.FieldServiceImpl;
import hr.go2.play.impl.LocationServiceImpl;
import hr.go2.play.impl.SubscriptionServiceImpl;
import hr.go2.play.impl.SubscriptionTypeServiceImpl;
import hr.go2.play.impl.UserServiceImpl;
import hr.go2.play.impl.WorkingHoursServiceImpl;
import hr.go2.play.repositories.CameraRepository;

@RestController
@RequestMapping(path = "/admin", produces = "application/json", method = {RequestMethod.GET, RequestMethod.PUT})
public class AdminRest {

    Logger logger = LoggerFactory.getLogger(getClass());
	
	ModelMapper mapper = new ModelMapper();
	
	@Autowired
	private CameraServiceImpl camService;
	
	@Autowired
	private CameraRepository camRepo;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private WorkingHoursServiceImpl whService;
	
	@Autowired
	private SubscriptionServiceImpl subService;

	@Autowired
	private SubscriptionTypeServiceImpl subTypeService;

	@Autowired
	private FieldServiceImpl fieldService;

	@Autowired 
	private LocationServiceImpl locationService;
	

	//Dodavanje novih rola userima
	//CRUD kamera
	//CRUD Lokacija
	//CRUD terena (field)
	//CRUD working hours-a
	//CRUD subscription
	//CRUD subscription type
	//Vezanje usera na lokaciju (contact info za lokaciju

	/**
	 *
	 * @param camDto
	 * JSON body example:
	 * { 
   "id":"",
   "name":"camName",
   "videos":[ 
      { 
         "id":null,
         "location":""
      }
   ]
}
	 * @return
	 */
	@PostMapping("/create/camera")
	public ResponseEntity<String> createCamera (@RequestBody CameraDTO camDto) {
		Camera cam = mapper.map(camDto, Camera.class);
		camService.saveCamera(cam);
		
		return new ResponseEntity<String>("{\"message\":\"Camera created!\"}", HttpStatus.CREATED);
	}

	@GetMapping("/get/cameras")
	public ResponseEntity<?> getAllCameras () {
		List<Camera> cameras = (List<Camera>) camService.findAllCameras();
        List<CameraDTO> camDTOList = cameras.stream().map(camera -> mapper.map(camera, CameraDTO.class)).collect(Collectors.toList());
        
        return new ResponseEntity<>(camDTOList, HttpStatus.OK);
	}

	/**
	 * 
	 * @param camDto
	 * {
        "id": 1,
        "name": "camUp",
        "videos": [
            {
                "id": 1,
                "location": "/path1"
            }
        ]
    }
	 * @return
	 */
	@PostMapping("/update/camera")
	public ResponseEntity<String> updateCamera (@RequestBody CameraDTO camDto) {
		Camera cam = mapper.map(camDto, Camera.class);
		camService.updateCamera(cam.getId(), cam);
		
		return new ResponseEntity<String>("{\"message\":\"Camera updated!\"}", HttpStatus.CREATED);
	}
	
	/**
	 * 
	 * @param id
	 * JSON body example - just sent camera id which you want to delete:
	 * 4
	 * @return
	 */
	@PostMapping("/delete/camera")
	public ResponseEntity<String> deleteCamera (@RequestBody String id) {
		if (!camRepo.existsById(Long.parseLong(id))) {
			return new ResponseEntity<String>("{\"message\":\"Camera doesn't exist!\"}", HttpStatus.BAD_REQUEST);
		}
		camService.deleteCameraById(Long.parseLong(id));
		return new ResponseEntity<String>("{\"message\":\"Camera deleted!\"}", HttpStatus.CREATED);
	}

	@PostMapping("/create/location")
	public ResponseEntity<String> createLocation (@RequestBody LocationDTO locationDto) {
		Location loc = mapper.map(locationDto, Location.class);
		locationService.saveLocation(loc);
		
		return new ResponseEntity<String>("{\"message\":\"Location created!\"}", HttpStatus.CREATED);
	}
}
