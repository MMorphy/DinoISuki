package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.Camera;

public interface CameraService {
	
	public abstract List<Camera> findAllCameras();
	
	public abstract List<Camera> findCameraById();

	public abstract List<Camera> findCameraByName();
	
	public abstract List<Camera> findCameraByVideoLocation();
	
	public abstract void deleteCameraByName();
	
	public abstract Camera saveCamera(Camera camera);
	
	public abstract Camera updateCamera(Long id, Camera camera);
}
