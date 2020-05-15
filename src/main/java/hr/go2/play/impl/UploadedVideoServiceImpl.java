package hr.go2.play.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.go2.play.entities.UploadedVideo;
import hr.go2.play.repositories.UploadedVideoRepository;
import hr.go2.play.services.UploadedVideoService;

@Service
public class UploadedVideoServiceImpl implements UploadedVideoService {

	@Autowired
	private UploadedVideoRepository uploadedVideoRepo;

	@Override
	public UploadedVideo findUploadedVideoByLocation(String location) {
		try {
			return this.uploadedVideoRepo.findByLocation(location).get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	@Override
	public UploadedVideo saveVideo(UploadedVideo video) {
		return this.uploadedVideoRepo.save(video);
	}

	@Override
	public UploadedVideo findByVideoName(String videoName) {
		Optional<UploadedVideo> optionalUploadedVideo = this.uploadedVideoRepo.findByVideoName(videoName);
		if (optionalUploadedVideo.isPresent()) {
			return optionalUploadedVideo.get();
		} else {
			return null;
		}
	}

	@Override
	public List<UploadedVideo> findAll() {
		return this.uploadedVideoRepo.findAll();
	}

}
