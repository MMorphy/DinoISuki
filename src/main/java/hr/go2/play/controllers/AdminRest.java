package hr.go2.play.controllers;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
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

import hr.go2.play.DTO.AdminStatisticsDTO;
import hr.go2.play.DTO.CameraDTO;
import hr.go2.play.DTO.DiskSpaceInfo;
import hr.go2.play.DTO.LocationDTO;
import hr.go2.play.entities.Camera;
import hr.go2.play.entities.Field;
import hr.go2.play.entities.Location;
import hr.go2.play.entities.SubscriptionStatistics;
import hr.go2.play.impl.CameraServiceImpl;
import hr.go2.play.services.CameraService;
import hr.go2.play.services.FieldService;
import hr.go2.play.services.LocationService;
import hr.go2.play.services.SubscriptionService;
import hr.go2.play.services.UserService;
import hr.go2.play.services.VideoService;
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
}
