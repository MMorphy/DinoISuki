package hr.go2.play.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.Camera;
import hr.go2.play.repositories.CameraRepository;
import hr.go2.play.services.CameraService;

@Service
@Transactional
public class CameraServiceImpl implements CameraService{
	
	@Autowired
	private CameraRepository cameraRepo;
	
	public  CameraServiceImpl(CameraRepository cameraRepo) {
		this.cameraRepo = cameraRepo;
	}

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
	public List<Camera> findCameraByName(String name) {
		return (List<Camera>) this.cameraRepo.findByName(name);
	}

	@Override
	public List<Camera> findCameraByVideoLocation(String location) {
		return (List<Camera>) this.cameraRepo.findByVideos_Location(location);
	}

	@Override
	public void deleteCameraByName(String name) {
		this.cameraRepo.deleteByName(name);
	}

	@Override
	public void deleteCameraById(Long id) {
		this.cameraRepo.deleteById(id);
	}

	@Override
	public void deleteAllCameras() {
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
			cam.setVideos(camera.getVideos());
			return this.cameraRepo.save(cam);
		} else {
			return this.cameraRepo.save(camera);
		}
	}

}
