package hr.go2.play.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import hr.go2.play.DTO.AdminStatisticsDTO;
import hr.go2.play.DTO.AdminUploadedVideoDTO;
import hr.go2.play.DTO.ApplicationPropertiesDTO;
import hr.go2.play.DTO.CameraDTO;
import hr.go2.play.DTO.DiskSpaceInfo;
import hr.go2.play.DTO.LocationDTO;
import hr.go2.play.DTO.LocationWithWorkingHoursDTO;
import hr.go2.play.DTO.QuizDTO;
import hr.go2.play.DTO.WorkingHoursDTO;
import hr.go2.play.entities.ApplicationProperties;
import hr.go2.play.entities.Camera;
import hr.go2.play.entities.DayType;
import hr.go2.play.entities.Field;
import hr.go2.play.entities.Location;
import hr.go2.play.entities.QuizAnswers;
import hr.go2.play.entities.QuizQuestions;
import hr.go2.play.entities.QuizStatus;
import hr.go2.play.entities.SubscriptionStatistics;
import hr.go2.play.entities.UploadedVideo;
import hr.go2.play.entities.User;
import hr.go2.play.entities.WorkingHours;
import hr.go2.play.impl.CameraServiceImpl;
import hr.go2.play.services.ApplicationPropertiesService;
import hr.go2.play.services.CameraService;
import hr.go2.play.services.DayTypeService;
import hr.go2.play.services.FieldService;
import hr.go2.play.services.LocationService;
import hr.go2.play.services.QuizAnswersService;
import hr.go2.play.services.QuizQuestionsService;
import hr.go2.play.services.SubscriptionService;
import hr.go2.play.services.UploadedVideoService;
import hr.go2.play.services.UserService;
import hr.go2.play.services.VideoService;
import hr.go2.play.services.WorkingHoursService;
import hr.go2.play.util.Commons;

@RestController
@RequestMapping(path = "/api/admin", produces = "application/json", method = {RequestMethod.GET, RequestMethod.PUT})
public class AdminRest {

    Logger logger = LoggerFactory.getLogger(getClass());

	ModelMapper mapper = new ModelMapper();

	@Autowired
	private CameraServiceImpl camService;

	@Autowired
	private UserService userService;

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private FieldService fieldService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private CameraService cameraService;

	@Autowired
	private VideoService videoService;

	@Autowired
	private Commons commons;

	@Autowired
	private ApplicationPropertiesService applicationPropertiesService;

	@Autowired
	private QuizQuestionsService quizQuestionsService;

	@Autowired
	private QuizAnswersService quizAnswersService;

	@Autowired
	private WorkingHoursService workingHoursService;

	@Autowired
	private DayTypeService dayTypeService;

	@Autowired
	private UploadedVideoService uploadedVideoService;

	@Value("${application.admin.uploaded-video-location}")
	String uploadedVideoLocationTemp;

//	@Value("${application.admin.uploaded-video-location}")
	String uploadedVideoLocation;

	@PostConstruct
	private void initVariables() {
		uploadedVideoLocation = commons.getProperty("application_admin_uploadedVideoLocation", String.class);
	}

	//CRUD kamera
	//CRUD Lokacija
	//CRUD working hours-a
	//CRUD subscription
	//CRUD subscription type

	/**
	 *
	 * @param camDto
	 * JSON body example:
	 *
	 * {
     * "id":"",
     * "name":"camName",
     * "videos":
     * 	[
     *     {
     *        "id":null,
     *        "location":""
     *     }
     *  ]
     *}
	 * @return
	 */
	@PostMapping("/addCamera")
	public ResponseEntity<String> createCamera (@RequestBody CameraDTO camDto) {
		Camera cam = mapper.map(camDto, Camera.class);
		cam.setVideos(null);
		camService.saveCamera(cam);
		return new ResponseEntity<String>(commons.JSONfyReturnMessage("Camera created!"), HttpStatus.CREATED);
	}

	@GetMapping("/getCameras")
	public ResponseEntity<?> getAllCameras () {
		List<Camera> cameras = camService.findAllCameras();
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
	@PostMapping("/updateCamera")
	public ResponseEntity<String> updateCamera (@RequestBody CameraDTO camDto) {
		Camera cam = mapper.map(camDto, Camera.class);
		camService.updateCamera(cam.getId(), cam);
		return new ResponseEntity<String>(commons.JSONfyReturnMessage("Camera updated!"), HttpStatus.CREATED);
	}

	/**
	 *
	 * @param id
	 * JSON body example - just sent camera id which you want to delete:
	 * 4
	 * @return
	 */
	@PostMapping("/deleteCamera")
	public ResponseEntity<String> deleteCamera (@RequestBody Long id) {
		if (!camService.existsById(id)) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Camera doesn't exist!"), HttpStatus.BAD_REQUEST);
		}
		camService.deleteCameraById(id);
		return new ResponseEntity<String>(commons.JSONfyReturnMessage("Camera deleted!"), HttpStatus.CREATED);
	}

	@PostMapping("/createLocation")
	public ResponseEntity<String> createLocation (@RequestBody LocationDTO locationDto) {
		Location loc = mapper.map(locationDto, Location.class);
		locationService.saveLocation(loc);

		return new ResponseEntity<String>(commons.JSONfyReturnMessage("Location created!"), HttpStatus.CREATED);
	}

	/*** Admin statistics ***/
	/*
	 * Call example: https://localhost:8443/api/admin/getAdminStatistics
	 */
	@GetMapping("/getAdminStatistics")
	public ResponseEntity<AdminStatisticsDTO> getAdminStatistics() {
		AdminStatisticsDTO adminStatisticsDTO = new AdminStatisticsDTO();
		// number of users
		adminStatisticsDTO.setNumberOfUsers(userService.countActiveUsers());
		// number of locations
		List<Location> locations = locationService.findAllLocations();
		if (locations != null) {
			adminStatisticsDTO.setNumberOfLocations(locations.size());
		}
		// number of fields
		List<Field> fields = fieldService.findAllFields();
		if (fields != null) {
			adminStatisticsDTO.setNumberOfFields(fields.size());
		}
		// number of cameras
		List<Camera> cameras = cameraService.findAllCameras();
		if (cameras != null) {
			adminStatisticsDTO.setNumberOfCameras(cameras.size());
		}
		// number of videos
		int activeVideos = videoService.countActiveVideos();
		adminStatisticsDTO.setNumberOfActiveVideos(activeVideos);
		// subscription statistics
		List<SubscriptionStatistics> subscriptionStatisticsList = subscriptionService.getSubscriptionStatistics();
		adminStatisticsDTO.setSubscriptionStatistics(subscriptionStatisticsList);
		// disk space
		List<DiskSpaceInfo> diskSpaceInfo = new ArrayList<DiskSpaceInfo>();
		for (Path root : FileSystems.getDefault().getRootDirectories()) {
			DiskSpaceInfo dsi = new DiskSpaceInfo();
			dsi.setPartition(root.toString());
			try {
				FileStore store = Files.getFileStore(root);
				long available = store.getUsableSpace();
				long total = store.getTotalSpace();
				long used = total - available;
				dsi.setAvailable(FileUtils.byteCountToDisplaySize(available));
				dsi.setTotal(FileUtils.byteCountToDisplaySize(total));
				dsi.setUsed(FileUtils.byteCountToDisplaySize(used));
				diskSpaceInfo.add(dsi);
			} catch (IOException e) {
				logger.warn("Error querying space: " + e.toString());
			}
		}
		adminStatisticsDTO.setDiskSpaceInfo(diskSpaceInfo);

		return new ResponseEntity<>(adminStatisticsDTO, HttpStatus.OK);
	}

	/*** Admin actions ***/
	/**
	 * Desc: add/update application property
	 * JSON body example:
	 * {
			"username": "test3",
			"key": "myKey1",
			"value": "myValue3"
		}
	 * @param ApplicationPropertiesDTO
	 * @return
	 */
	@PostMapping("/addApplicationProperty")
	public ResponseEntity<String> addApplicationProperty(@RequestBody ApplicationPropertiesDTO applicationPropertiesDTO) {
		if (applicationPropertiesDTO == null || applicationPropertiesDTO.getUsername() == null || applicationPropertiesDTO.getUsername().isEmpty()) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Username not provided"), HttpStatus.BAD_REQUEST);
		}
		if (applicationPropertiesDTO.getKey() == null || applicationPropertiesDTO.getKey().isEmpty()) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Key not provided"), HttpStatus.BAD_REQUEST);
		}
		User user = userService.findUserByUsername(applicationPropertiesDTO.getUsername());
		if (user == null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("User doesn't exist!"), HttpStatus.BAD_REQUEST);
		}
		Optional<ApplicationProperties> applicationPropertiesOptional = applicationPropertiesService.findByKey(applicationPropertiesDTO.getKey());
		ApplicationProperties applicationProperties = new ApplicationProperties();
		if (applicationPropertiesOptional.isPresent()) {
			applicationProperties.setId(applicationPropertiesOptional.get().getId());
		}
		applicationProperties.setKey(applicationPropertiesDTO.getKey());
		applicationProperties.setValue(applicationPropertiesDTO.getValue());
		applicationProperties.setUserId(user);
		applicationProperties.setUpdatedAt(new Date());

		applicationPropertiesService.saveApplicationProperty(applicationProperties);

		return new ResponseEntity<String>(commons.JSONfyReturnMessage("Application property added/updated!"), HttpStatus.OK);
	}

	/*
	 * Call example: https://localhost:8443/api/admin/getApplicationProperty
	 */
	@GetMapping("/getApplicationProperty")
	public ResponseEntity<?> getApplicationProperty(@RequestParam(name = "key") String key) {
		ApplicationPropertiesDTO applicationPropertiesDTO = new ApplicationPropertiesDTO();
		if (key == null || key.isEmpty()) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("No property key provided"), HttpStatus.BAD_REQUEST);
		}
		Object valueObj = commons.getProperty(key, String.class);
		String value = valueObj != null ? valueObj.toString() : null;
		applicationPropertiesDTO.setKey(key);
		applicationPropertiesDTO.setValue(value);

		return new ResponseEntity<>(applicationPropertiesDTO, HttpStatus.OK);
	}

	/*** Admin quizes ***/
	/**
	 * Desc: add new quiz
	 * JSON body example:
	 * {
		  "name": "poll1",
		  "noOfQuestions": 3,
		  "status": "NOT_PUBLISHED",
		  "questions": [
		    {
		      "q": "q1",
		      "a": [
		        "a1a",
		        "a1b",
		        "a1c"
		      ],
		      "ca": "a1a"
		    },
		    {
		      "q": "q2",
		      "a": [
		        "a2a",
		        "a2b",
		        "a2c"
		      ],
		      "ca": "a2b"
		    },
		    {
		      "q": "q3",
		      "a": [
		        "a3a",
		        "a3b",
		        "a3c"
		      ],
		      "ca": "a3c"
		    }
		  ]
		}
	 * @param QuizDTO
	 * @return
	 */
	@PostMapping("/addNewQuiz")
	public ResponseEntity<String> addNewQuiz(@RequestBody QuizDTO quizDTO) {
		logger.debug("/api/admin/addNewQuiz Started");
		if (quizDTO == null || quizDTO.getNoOfQuestions() <= 0 || quizDTO.getName() == null || quizDTO.getName().isEmpty() || quizDTO.getQuestions() == null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid quiz data"), HttpStatus.BAD_REQUEST);
		}
		Optional<QuizQuestions> optionalQuizQuestions = quizQuestionsService.findByName(quizDTO.getName());
		if (optionalQuizQuestions.isPresent()) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Quiz with this name already exists"), HttpStatus.BAD_REQUEST);
		}
		if (quizDTO.getCreatedAt() == null) {
			quizDTO.setCreatedAt(new Date());
		}
		if (quizDTO.getStatus() == null) {
			quizDTO.setStatus(QuizStatus.NOT_PUBLISHED);
		}
		quizDTO.setId(0);

		QuizQuestions quizQuestions = mapper.map(quizDTO, QuizQuestions.class);
		quizQuestionsService.saveQuizQuestions(quizQuestions);

		logger.debug("/api/admin/addNewQuiz Finished");

		return new ResponseEntity<String>(commons.JSONfyReturnMessage("New quiz saved!"), HttpStatus.OK);
	}

	/*
	 * Description: Fetches quiz by quiz name or all quizes if no name provided
	 * Input params: quiz name (string)
	 * Call example:
	 * 	https://localhost:8443/api/admin/getQuiz?name=poll2
	 *
	 */
	@Transactional
	@GetMapping("/getQuiz")
	public ResponseEntity<?> getQuiz(@RequestParam(name = "name", required = false) String name) {
		logger.debug("/api/admin/getQuiz Started");
		List<QuizDTO> quizes = new ArrayList<QuizDTO>();
		if (name == null || name.isEmpty()) {
			quizes = quizQuestionsService.findAll().stream().map(quizQuestion -> mapper.map(quizQuestion, QuizDTO.class)).collect(Collectors.toList());
			for (QuizDTO quiz : quizes) {
				int noOfUsersParticipatedInQuiz = quizQuestionsService.getNoOfUsersParticipatedInQuiz(quiz.getName());
				quiz.setUsersParticipated(noOfUsersParticipatedInQuiz);
			}
		} else {
			Optional<QuizQuestions> optionalQuizQuestions = quizQuestionsService.findByName(name);
			if (optionalQuizQuestions.isPresent()) {
				QuizDTO quizDTO = mapper.map(optionalQuizQuestions.get(), QuizDTO.class);
				int noOfUsersParticipatedInQuiz = quizQuestionsService.getNoOfUsersParticipatedInQuiz(quizDTO.getName());
				quizDTO.setUsersParticipated(noOfUsersParticipatedInQuiz);
				quizes.add(quizDTO);
			}
		}
		logger.debug("/api/admin/getQuiz Finished");
		return new ResponseEntity<>(quizes, HttpStatus.OK);
	}

	/**
	 * Desc: modify existing quiz
	 * JSON body example:
	 * {
		  "name": "poll1",
		  "noOfQuestions": 3,
		  "status": "NOT_PUBLISHED",
		  "questions": [
		    {
		      "q": "q1",
		      "a": [
		        "a1a",
		        "a1b",
		        "a1c"
		      ],
		      "ca": "a1a"
		    },
		    {
		      "q": "q2",
		      "a": [
		        "a2a",
		        "a2b",
		        "a2c"
		      ],
		      "ca": "a2b"
		    },
		    {
		      "q": "q3",
		      "a": [
		        "a3a",
		        "a3b",
		        "a3c"
		      ],
		      "ca": "a3c"
		    }
		  ]
		}
	 * @param QuizDTO
	 * @return
	 */
	@PostMapping("/updateQuiz")
	public ResponseEntity<String> updateQuiz(@RequestBody QuizDTO quizDTO) {
		logger.debug("/api/admin/updateQuiz Started");
		if (quizDTO == null || quizDTO.getNoOfQuestions() <= 0 || quizDTO.getName() == null || quizDTO.getName().isEmpty() || quizDTO.getQuestions() == null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid quiz data"), HttpStatus.BAD_REQUEST);
		}
		Optional<QuizQuestions> optionalQuizQuestions = quizQuestionsService.findByName(quizDTO.getName());
		if (optionalQuizQuestions.isEmpty()) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Not found quiz with name: " + quizDTO.getName()), HttpStatus.NOT_FOUND);
		}
		if (quizDTO.getCreatedAt() == null) {
			quizDTO.setCreatedAt(new Date());
		}

		QuizQuestions quizQuestions = optionalQuizQuestions.get();
		quizQuestions.setNoOfQuestions(quizDTO.getNoOfQuestions());
		quizQuestions.setQuestions(quizDTO.getQuestions());
		quizQuestions.setStatus(quizDTO.getStatus());
		quizQuestionsService.saveQuizQuestions(quizQuestions);

		logger.debug("/api/admin/updateQuiz Finished");

		return new ResponseEntity<String>(commons.JSONfyReturnMessage("Quiz updated!"), HttpStatus.OK);
	}

	/*
	 * Description: Fetches all quizes that are PUBLISHED the user has not taken yet
	 * Input params: username (string) Call example:
	 * https://localhost:8443/api/admin/getNewQuizesForUser?username=test2
	 *
	 */
	@Transactional
	@GetMapping("/getNewQuizesForUser")
	public ResponseEntity<?> getNewQuizesForUser(@RequestParam(name = "username") String username) {
		logger.debug("/api/admin/getNewQuizesForUser Started");

		if (username == null || username.isEmpty()) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Username not provided"), HttpStatus.BAD_REQUEST);
		}

		User user = userService.findUserByUsername(username);
		if (user == null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("User with username: " + username + " not found"), HttpStatus.BAD_REQUEST);
		}

		List<QuizDTO> quizes = new ArrayList<QuizDTO>();
		quizes = quizQuestionsService.getNewQuizesForUser(user).stream().map(quizQuestion -> mapper.map(quizQuestion, QuizDTO.class)).collect(Collectors.toList());

		logger.debug("/api/admin/getNewQuizesForUser Finished");
		return new ResponseEntity<>(quizes, HttpStatus.OK);
	}

	/*
	 * Description: Fetches all quizes that are PUBLISHED already taken by user
	 * Input params: username (string) Call example:
	 * https://localhost:8443/api/admin/getQuizesTakenByUser?username=test2
	 *
	 */
	@Transactional
	@GetMapping("/getQuizesTakenByUser")
	public ResponseEntity<?> getQuizesTakenByUser(@RequestParam(name = "username") String username) {
		logger.debug("/api/admin/getQuizesTakenByUser Started");

		if (username == null || username.isEmpty()) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Username not provided"), HttpStatus.BAD_REQUEST);
		}

		User user = userService.findUserByUsername(username);
		if (user == null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("User with username: " + username + " not found"), HttpStatus.BAD_REQUEST);
		}

		List<QuizDTO> quizes = new ArrayList<QuizDTO>();
		// fetching quizes taken by user
		List<QuizQuestions> listQuizQuestions = quizQuestionsService.getQuizesTakenByUser(user);
		for (QuizQuestions listQuizQuestion : listQuizQuestions) {
			// for each found quiz, fetching answers
			QuizDTO quizDTO = mapper.map(listQuizQuestion, QuizDTO.class);
			Optional<QuizAnswers> optionalQuizAnswers = quizAnswersService.findByUserIdAndQuizId(user, listQuizQuestion);
			if (optionalQuizAnswers.isPresent()) {
				quizDTO.setAnswers(optionalQuizAnswers.get().getAnswers());
				quizDTO.setCorrectAnswers(optionalQuizAnswers.get().getCorrectAnswers());
				quizDTO.setUsername(username);
			}
			quizes.add(quizDTO);
		}

		logger.debug("/api/admin/getQuizesTakenByUser Finished");
		return new ResponseEntity<>(quizes, HttpStatus.OK);
	}


	/**
	 * Desc: store quiz answers
	 * JSON body example:
	 * {
			"name": "Moj novi kviz3",
			"noOfQuestions": 4,
			"status": "NOT_PUBLISHED",
			"answers": [
				{
				"q": "qzzzzY",
				"a": "a1a"
				},
				{
				"q": "q2",
				"a": "a2a"
				},
				{
				"q": "q3",
				"a": "a3a"
				}
				],
			"correctAnswers": 1,
			"username": "test2"
		}
	 * @param QuizDTO
	 * @return
	 */
	@PostMapping("/addQuizAnswers")
	public ResponseEntity<String> addQuizAnswers(@RequestBody QuizDTO quizDTO) {
		logger.debug("/api/admin/addQuizAnswers Started");
		if (quizDTO == null || quizDTO.getNoOfQuestions() <= 0 || quizDTO.getName() == null || quizDTO.getName().isEmpty() || quizDTO.getAnswers() == null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid quiz data"), HttpStatus.BAD_REQUEST);
		}

		Optional<QuizQuestions> optionalQuizQuestions = quizQuestionsService.findByName(quizDTO.getName());
		if (optionalQuizQuestions.isEmpty()) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Could not find a quiz with name:" + quizDTO.getName()), HttpStatus.BAD_REQUEST);
		}

		User user = userService.findUserByUsername(quizDTO.getUsername());
		if (user == null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Could not find user with username:" + quizDTO.getUsername()), HttpStatus.BAD_REQUEST);
		}

		quizDTO.setId(0);

		QuizAnswers quizAnswers = mapper.map(quizDTO, QuizAnswers.class);
		quizAnswers.setTakenAt(new Date());
		quizAnswers.setUserId(user);
		quizAnswers.setQuizId(optionalQuizQuestions.get());

		quizAnswersService.saveQuizAnswers(quizAnswers);

		logger.debug("/api/admin/addQuizAnswers Finished");

		return new ResponseEntity<String>(commons.JSONfyReturnMessage("Quiz answers saved!"), HttpStatus.OK);
	}

	/*
	 * Description: Fetches all answers by all users that have taken the quiz by quiz name
	 * Input params: quizname (string)
	 * Call example:
	 * https://localhost:8443/api/admin/getAllAnswersForQuiz?quizname=Some quiz
	 *
	 */
	@Transactional
	@GetMapping("/getAllAnswersForQuiz")
	public ResponseEntity<?> getAllAnswersForQuiz(@RequestParam(name = "quizname") String quizname) {
		logger.debug("/api/admin/getAllAnswersForQuiz Started");

		if (quizname == null || quizname.isEmpty()) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Quiz name not provided"), HttpStatus.BAD_REQUEST);
		}

		List<QuizDTO> quizes = new ArrayList<QuizDTO>();
		List<QuizAnswers> quizAnswersForQuizes = quizAnswersService.getAllAnswersForQuiz(quizname);
		for (QuizAnswers quizAnswers : quizAnswersForQuizes) {
			QuizDTO quizDTO = mapper.map(quizAnswers, QuizDTO.class);
			quizDTO.setCreatedAt(quizAnswers.getTakenAt());
			quizDTO.setUsername(quizAnswers.getUserId().getUsername());
			quizDTO.setName(quizname);
			quizes.add(quizDTO);
		}

		logger.debug("/api/admin/getAllAnswersForQuiz Finished");
		return new ResponseEntity<>(quizes, HttpStatus.OK);
	}

	/*** Admin working hours management ***/

	/*
	 * Description: Fetch location(s) with working hours
	 * Input parameters: String name (optional)
	 * Call example:
	 * https://localhost:8443/api/admin/getLocationWithWorkingHours?name=Lokacija1
	 *
	 */
	@GetMapping("/getLocationWithWorkingHours")
	public ResponseEntity<?> getLocationWithWorkingHours(@RequestParam(name = "name", required = false) String name) {
		logger.debug("/api/admin/getLocationWithWorkingHours Started");

		List<LocationWithWorkingHoursDTO> locationWithWorkingHoursDTOList = new ArrayList<LocationWithWorkingHoursDTO>();

		List<Location> locations = new ArrayList<Location>();
		if (name != null && !name.isEmpty()) {
			Location location = locationService.findLocationByName(name);
			if (location != null) {
				locations.add(location);
			}
		} else {
			locations = locationService.findAllLocations();
		}
		for (Location location : locations) {
			LocationWithWorkingHoursDTO locationWithWorkingHoursDTO = mapper.map(location, LocationWithWorkingHoursDTO.class);
			if (location.getContactInformation() != null) {
				locationWithWorkingHoursDTO.setContactTel(location.getContactInformation().getTelephoneNumber());
				locationWithWorkingHoursDTO.setContactEmail(location.getContactInformation().getEmail());
			}
			List<WorkingHours> wos = (List<WorkingHours>) location.getHours();
			List<WorkingHoursDTO> workingHoursDTOList = new ArrayList<WorkingHoursDTO>();
			for (WorkingHours wo : wos) {
				WorkingHoursDTO workingHoursDTO = mapper.map(wo, WorkingHoursDTO.class);
				workingHoursDTO.setDayType(wo.getDayType().getType());
				workingHoursDTOList.add(workingHoursDTO);
			}
			locationWithWorkingHoursDTO.setWorkingHours(workingHoursDTOList);
			locationWithWorkingHoursDTOList.add(locationWithWorkingHoursDTO);
		}
		logger.debug("/api/admin/getLocationWithWorkingHours Finished");
		return new ResponseEntity<>(locationWithWorkingHoursDTOList, HttpStatus.OK);
	}

	/**
	 * Desc: store working hours for given location
	 * JSON body example:
	 * {
	        "name": "Skrivena lokacja",
	        "workingHours": [
	        	{
	                "id": 1,
	                "fromTime": "06:00:00",
	                "toTime": "21:00:00",
	                "dayType": "WORKDAY"
	            },
	            {
	                "id": 2,
	                "fromTime": "08:00:00",
	                "toTime": "20:00:00",
	                "dayType": "WEEKEND"
	            },
	            {
	                "id": 3,
	                "fromTime": "09:00:00",
	                "toTime": "20:00:00",
	                "dayType": "HOLIDAY"
	            }
	        	]
	    }
	 */
	@PostMapping("/saveLocationWorkingHours")
	public ResponseEntity<String> saveLocationWorkingHours(@RequestBody LocationWithWorkingHoursDTO locationWithWorkingHoursDTO) {
		logger.debug("/api/admin/saveLocationWorkingHours Started");
		if (locationWithWorkingHoursDTO == null || locationWithWorkingHoursDTO.getName() == null || locationWithWorkingHoursDTO.getName().isEmpty() || locationWithWorkingHoursDTO.getWorkingHours() == null || locationWithWorkingHoursDTO.getWorkingHours().size() == 0) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid working hours data"), HttpStatus.BAD_REQUEST);
		}

		Location location = locationService.findLocationByName(locationWithWorkingHoursDTO.getName());
		if (location == null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Location with name: " + locationWithWorkingHoursDTO.getName() + " not found"), HttpStatus.BAD_REQUEST);
		}

		List<WorkingHours> wos = new ArrayList<WorkingHours>();
		for (WorkingHoursDTO workingHoursDTO : locationWithWorkingHoursDTO.getWorkingHours()) {
			DayType dayType = dayTypeService.findDayTypeByType(workingHoursDTO.getDayType());
			if (dayType == null) {
				return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid working hours day type: " + workingHoursDTO.getDayType()), HttpStatus.BAD_REQUEST);
			}

			List<WorkingHours> storedWos = workingHoursService.findWorkingHoursByFromTimeAndToTime(workingHoursDTO.getFromTime(), workingHoursDTO.getToTime());
			boolean foundAlreadyStoredWo = false;
			for (WorkingHours swo : storedWos) {
				if (swo.getDayType().getType().equals(workingHoursDTO.getDayType())) {
					foundAlreadyStoredWo = true;
					wos.add(swo);
					break;
				}
			}

			if (foundAlreadyStoredWo == false) {
				WorkingHours wo = new WorkingHours();
				wo.setFromTime(workingHoursDTO.getFromTime());
				wo.setToTime(workingHoursDTO.getToTime());
				wo.setDayType(dayType);
				wo = workingHoursService.saveWorkingHours(wo);
				wos.add(wo);
			}

		}
		location.setHours(wos);

		locationService.saveLocation(location);

		logger.debug("/api/admin/saveLocationWorkingHours Finished");
		return new ResponseEntity<String>(commons.JSONfyReturnMessage("Working hours saved!"), HttpStatus.OK);
	}

	// upload video
	@PostMapping(value = "/adminUploadVideo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> adminUploadVideo(@RequestParam(name = "uploadVideo") MultipartFile uploadVideo, @RequestParam(name = "name") String name) {
		logger.debug("/api/user/adminUploadVideo Started");

		if (name == null || name.isEmpty()) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Video name not provided"), HttpStatus.BAD_REQUEST);
		}

		try {
			// store the file
			byte[] bytes = uploadVideo.getBytes();
			createUploadedVideosFolder();
			Path path = Paths.get(uploadedVideoLocation + "/" + uploadVideo.getOriginalFilename());
			Files.write(path, bytes);

			// store the data about the file info to database
			UploadedVideo uploadedVideo = new UploadedVideo();
			uploadedVideo.setLocation(uploadVideo.getOriginalFilename());
			uploadedVideo.setUploadedAt(new Date());
			uploadedVideo.setVideoName(name);
			uploadedVideoService.saveVideo(uploadedVideo);

		} catch (IOException e) {
			logger.error("Unable to save file", e);
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Unable to save file"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Unable to store file", e);
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Unable to store file. " + e.getMessage()), HttpStatus.BAD_REQUEST);
		}

		logger.debug("/api/user/adminUploadVideo Finished");
		return new ResponseEntity<String>(commons.JSONfyReturnMessage("Successfully uploaded - " + uploadVideo.getOriginalFilename()), HttpStatus.OK);
	}

	private boolean createUploadedVideosFolder() {
		boolean created = true;
		File folder = new File(uploadedVideoLocation);
		if (!folder.exists() || !folder.isDirectory()) {
			try {
				folder.mkdir();
			} catch (SecurityException e) {
				logger.error("Unable to create folder: " + uploadedVideoLocation, e);
				created = false;
			}
		}
		return created;
	}

	/*
	 * Description: Fetch uploaded videos by admin
	 * Input parameters: Video name (optional)
	 * Call example: https://localhost:8443/api/admin/getUploadedVideo?name=test
	 *
	 */
	@GetMapping("/getUploadedVideo")
	public ResponseEntity<?> getUploadedVideo(@RequestParam(name = "name", required = false) String name) {
		logger.debug("/api/admin/getUploadedVideo Started");

		List<AdminUploadedVideoDTO> adminUploadedVideoDTOList = new ArrayList<AdminUploadedVideoDTO>();

		if (name == null || name.isEmpty()) {
			List<UploadedVideo> uploadedVideoList = uploadedVideoService.findAll();
			adminUploadedVideoDTOList = uploadedVideoList.stream().map(uploadedVideo -> mapper.map(uploadedVideo, AdminUploadedVideoDTO.class)).collect(Collectors.toList());
		} else {
			UploadedVideo uploadedVideo = uploadedVideoService.findByVideoName(name);
			AdminUploadedVideoDTO adminUploadedVideoDTO = mapper.map(uploadedVideo, AdminUploadedVideoDTO.class);
			adminUploadedVideoDTOList.add(adminUploadedVideoDTO);
		}

		logger.debug("/api/admin/getUploadedVideo Finished");
		return new ResponseEntity<>(adminUploadedVideoDTOList, HttpStatus.OK);
	}

	/*
	 * Description: Update uploaded video by admin - change archived
	 * Input parameters: Video name (optional)
	 * Call example: https://localhost:8443/api/admin/updateUploadedVideo
	 *
	 */
	@PostMapping("/updateUploadedVideo")
	public ResponseEntity<?> updateUploadedVideo(@RequestBody AdminUploadedVideoDTO adminUploadedVideoDTO) {
		logger.debug("/api/admin/updateUploadedVideo Started");
		if(adminUploadedVideoDTO == null || adminUploadedVideoDTO.getVideoName() == null || adminUploadedVideoDTO.getVideoName().isEmpty()) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Invalid input data"), HttpStatus.BAD_REQUEST);
		}
		UploadedVideo uploadedVideo = uploadedVideoService.findByVideoName(adminUploadedVideoDTO.getVideoName());
		if(uploadedVideo == null) {
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Unable to find uploaded video with name:" + adminUploadedVideoDTO.getVideoName()), HttpStatus.BAD_REQUEST);
		}
		uploadedVideo.setArchived(adminUploadedVideoDTO.isArchived());
		uploadedVideoService.saveVideo(uploadedVideo);
		logger.debug("/api/admin/updateUploadedVideo Started");
		return new ResponseEntity<String>(commons.JSONfyReturnMessage("Uploaded video updated!"), HttpStatus.CREATED);
	}

}
