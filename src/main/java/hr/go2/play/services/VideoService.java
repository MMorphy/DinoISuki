package hr.go2.play.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import hr.go2.play.entities.Video;

public interface VideoService {

	public abstract List<Video> findAllVideos();

	public abstract Video findVideoById(Long id);

	public abstract Video findVideoByLocation(String location);

	public abstract Video findVideoByTimestamp(Date timestamp);

	public abstract Collection<Video> findVideosOnDate(Date date);

	public abstract void deleteVideoById(Long id);

	public abstract Video saveVideo(Video video);

	public abstract Video updateVideo(Long id, Video video);

	public abstract void deleteAllVideos();

	public abstract void deleteVideo(Video video);

	public abstract List<Video> findVideosOlderThanDate(Date date);

	public abstract int countActiveVideos();

}
