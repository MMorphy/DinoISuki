package hr.go2.play.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.Camera;
import hr.go2.play.entities.Video;
import hr.go2.play.repositories.CameraRepository;
import hr.go2.play.services.CameraService;

@Service
@Transactional
public class CameraServiceImpl implements CameraService {

	@Autowired
	private CameraRepository cameraRepo;

	@Autowired
	private VideoServiceImpl videoService;

	@Override
	public List<Camera> findAllCameras() {
		return this.cameraRepo.findAll();
	}

	@Override
	public Camera findCameraById(Long id) {
		try {
			return this.cameraRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public Camera findCameraByName(String name) {
		try {
			return this.cameraRepo.findByName(name).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public Camera findCameraByVideoLocation(String location) {
		try {
			return this.cameraRepo.findByVideos_Location(location).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public void deleteCameraByName(String name) {
		Camera cam = this.findCameraByName(name);
		for (Video vid : cam.getVideos()) {
			videoService.deleteVideoById(vid.getId());
		}
		this.cameraRepo.delete(cam);	}

	@Override
	public void deleteCameraById(Long id) {
		Camera cam = this.findCameraById(id);
		for (Video vid : cam.getVideos()) {
			videoService.deleteVideoById(vid.getId());
		}
		this.cameraRepo.delete(cam);
	}

	@Override
	public void deleteAllCameras() {
		List<Camera> cameras = findAllCameras();
		for (Camera cam : cameras) {
			for (Video vid : cam.getVideos()) {
				videoService.deleteVideoById(vid.getId());
			}
		}
		this.cameraRepo.deleteAll();
	}

	@Override
	public Camera saveCamera(Camera camera) {
		return this.cameraRepo.save(camera);
	}

	@Override
	public Camera updateCamera(Long id, Camera camera) {
		Optional<Camera> optCamera = this.cameraRepo.findById(id);
		if (optCamera.isPresent()) {
			Camera cam = optCamera.get();
			cam.setName(camera.getName());
			return this.cameraRepo.save(cam);
		} else {
			return this.cameraRepo.save(camera);
		}
	}

	@Override
	public Camera findCameraByVideoId(Long id) {
		try {
			return this.cameraRepo.findByVideos_Id(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public Boolean existsById(Long id) {
		return this.cameraRepo.existsById(id);
	}

	@Override
	public Boolean addVideo(Camera cam, Video vid) {
		cam.addVideo(vid);
		videoService.saveVideo(vid);
		updateCamWithVideos(cam.getId(), cam);
		return null;
	}

	@Override
	public Boolean removeVideo(Camera cam, Video vid) {
		cam.removeVideo(vid);
		videoService.deleteVideo(vid);
		updateCamWithVideos(cam.getId(), cam);
		return null;
	}

	private Camera updateCamWithVideos(Long id, Camera camera) {
		Optional<Camera> optCamera = this.cameraRepo.findById(id);
		if (optCamera.isPresent()) {
			Camera cam = optCamera.get();
			cam.setName(camera.getName());
			cam.setVideos(camera.getVideos());
			return this.cameraRepo.save(cam);
		} else {
			return this.cameraRepo.save(camera);
		}
	}

}
