package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.Video;

public interface VideoService {
	
	public abstract List<Video> findAllVideos();
	
	public abstract Video findVideoById(Long id);
	
	public abstract Video findVideoByLocation(String location);
	
	public abstract void deleteVideoById(Long id);
	
	public abstract Video saveVideo(Video video);
	
	public abstract Video updateVideo(Long id, Video video);

}
