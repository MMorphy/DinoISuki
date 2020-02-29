package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.Camera;
import hr.go2.play.entities.Video;

public interface CameraService {
	
	public abstract List<Camera> findAllCameras();
	
	public abstract Camera findCameraById(Long id);

	public abstract Camera findCameraByName(String name);
	
	public abstract Camera findCameraByVideoLocation(String location);

	public abstract Camera findCameraByVideoId(Long id);
	
	public abstract void deleteCameraByName(String name);
	
	public abstract void deleteCameraById(Long id);
	
	public abstract Camera saveCamera(Camera camera);
	
	public abstract Camera updateCamera(Long id, Camera camera);

	public abstract void deleteAllCameras();

	public abstract Boolean existsById(Long id);

	public abstract Boolean addVideo (Camera cam, Video vid);

	public abstract Boolean removeVideo (Camera cam, Video vid);
}
