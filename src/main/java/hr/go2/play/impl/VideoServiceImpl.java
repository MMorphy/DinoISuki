package hr.go2.play.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import hr.go2.play.entities.Video;
import hr.go2.play.repositories.VideoRepository;
import hr.go2.play.services.VideoService;

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
	public Video saveVideo(Video video) {
		return this.videoRepo.save(video);
	}

	@Override
	public Video updateVideo(Long id, Video video) {
		Optional<Video> optVideo = this.videoRepo.findById(id);
		if (optVideo.isPresent()) {
			Video videoNew = optVideo.get();
			videoNew.setLocation(video.getLocation());
			return this.videoRepo.save(videoNew);
		} else {
			return this.videoRepo.save(video);
		}
	}

}
