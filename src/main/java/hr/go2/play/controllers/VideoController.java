package hr.go2.play.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import hr.go2.play.DTO.VideoDetailsDTO;
import hr.go2.play.entities.Video;
import hr.go2.play.impl.LocationServiceImpl;
import hr.go2.play.impl.VideoServiceImpl;
import hr.go2.play.services.LocationService;

@RestController
@RequestMapping("/api/video")
@Secured("ROLE_USER")
public class VideoController {

	@Autowired
	private VideoServiceImpl videoService;

	@Autowired
	private LocationServiceImpl locationService;

	
	@GetMapping("/getAllVideos")
	public ResponseEntity<List<VideoDetailsDTO>> getAllVideoDetails(){
		return new ResponseEntity<List<VideoDetailsDTO>>(locationService.findAllVideoDetails(), HttpStatus.OK);
	}

	@GetMapping("/getVideo/{id}")
	public StreamingResponseBody stream(@PathVariable(name = "id", required = true) Long id) throws FileNotFoundException {
		Video video = videoService.findVideoById(id);
		File videoFile = new File(video.getLocation());
		final InputStream videoFileStream = new FileInputStream(videoFile);
		return (os) -> {
			readAndWrite(videoFileStream, os);
		};
	}

	private void readAndWrite(final InputStream is, OutputStream os) throws IOException {
		byte[] data = new byte[2048];
		int read = 0;
		while ((read = is.read(data)) > 0) {
			os.write(data, 0, read);
		}
		os.flush();
	}
}
