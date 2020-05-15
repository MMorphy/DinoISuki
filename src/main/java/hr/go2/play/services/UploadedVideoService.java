package hr.go2.play.services;

import java.util.List;

import hr.go2.play.entities.UploadedVideo;

public interface UploadedVideoService {

	public abstract UploadedVideo findUploadedVideoByLocation(String location);

	public abstract UploadedVideo saveVideo(UploadedVideo video);

	public abstract UploadedVideo findByVideoName(String videoName);

	public abstract List<UploadedVideo> findAll();

}
