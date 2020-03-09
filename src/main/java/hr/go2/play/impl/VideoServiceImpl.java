package hr.go2.play.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.Video;
import hr.go2.play.repositories.VideoRepository;
import hr.go2.play.services.VideoService;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideoRepository videoRepo;

	public VideoServiceImpl(VideoRepository videoRepo) {
		this.videoRepo = videoRepo;
	}

	@Override
	public List<Video> findAllVideos() {
		return this.videoRepo.findAll();
	}

	@Override
	public Video findVideoById(Long id) {
		try {
			return this.videoRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public Video findVideoByLocation(String location) {
		try {
			return this.videoRepo.findByLocation(location).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public void deleteVideoById(Long id) {
		this.videoRepo.deleteById(id);
	}

	@Override
	public void deleteAllVideos() {
		this.videoRepo.deleteAll();
	}

	@Override
	public Video saveVideo(Video video) {
		return this.videoRepo.save(video);
	}

	@Override
	public Video updateVideo(Long id, Video video) {
		Optional<Video> optVideo = this.videoRepo.findById(id);
		if (optVideo.isPresent()) {
			Video videoNew = optVideo.get();
			videoNew.setLocation(video.getLocation());
			videoNew.setStartedAt(video.getStartedAt());
			return this.videoRepo.save(videoNew);
		} else {
			return this.videoRepo.save(video);
		}
	}

	@Override
	public Video findVideoByTimestamp(Date timestamp) {
		try {
			return this.videoRepo.findByStartedAt(timestamp).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public List<Video> findVideosOnDate(Date date) {
		return (List<Video>) this.videoRepo.findByDate(date);
	}

	@Override
	public void deleteVideo(Video video) {
		this.videoRepo.delete(video);
	}

	@Override
	public List<Video> findVideosOlderThanDate(Date date) {
		return (List<Video>) this.videoRepo.findVideosOlderThanDate(date);
	}

}
