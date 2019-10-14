package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.Camera;

public interface CameraService {
	
	public abstract List<Camera> findAllCameras();
	
	public abstract Camera findCameraById(Long id);

	public abstract List<Camera> findCameraByName(String name);
	
	public abstract List<Camera> findCameraByVideoLocation(String location);
	
	public abstract void deleteCameraByName(String name);
	
	public abstract void deleteCameraById(Long id);
	
	public abstract Camera saveCamera(Camera camera);
	
	public abstract Camera updateCamera(Long id, Camera camera);
}
