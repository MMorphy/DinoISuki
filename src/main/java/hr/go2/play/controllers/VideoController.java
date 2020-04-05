package hr.go2.play.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import hr.go2.play.DTO.VideoDetailsDTO;
import hr.go2.play.entities.Video;
import hr.go2.play.impl.LocationServiceImpl;
import hr.go2.play.impl.VideoServiceImpl;
import hr.go2.play.util.Commons;

@RestController
@RequestMapping("/api/video")
@Secured("ROLE_USER")
public class VideoController {

	Logger logger = LoggerFactory.getLogger(VideoController.class);

	@Autowired
	private VideoServiceImpl videoService;

	@Autowired
	private LocationServiceImpl locationService;

	@Autowired
	private Commons commons;


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

	@GetMapping("/getVideo1/{id}")
	public ResponseEntity<?> stream1(@PathVariable(name = "id", required = true) Long id) throws FileNotFoundException {
		logger.debug("getVideo1 Started");
		Video video = videoService.findVideoById(id);
		File videoFile = new File(video.getLocation());
		logger.debug("videoFile:" + videoFile.getPath());

		if (videoFile.equals(null)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		InputStream inputStream = new FileInputStream(videoFile);
		InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("video/mp4"));
		try {
			headers.setContentLength(Files.size(Paths.get(video.getLocation())));
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(commons.JSONfyReturnMessage("Error while streaming"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		headers.setConnection("Keep-Alive");
		headers.set("Accept-Ranges", "bytes");
		headers.set("Cache-Control", "max-age=604800, no-transform");
		headers.set("Keep-Alive", "timeout=5, max=384");

		return new ResponseEntity<InputStreamResource>(inputStreamResource, headers, HttpStatus.OK);

	}

	@GetMapping("/getVideo2/{id}")
	public ResponseEntity<StreamingResponseBody> stream2(@PathVariable(name = "id", required = true) Long id) throws FileNotFoundException {
		Video video = videoService.findVideoById(id);
		File videoFile = new File(video.getLocation());
		final InputStream videoFileStream = new FileInputStream(videoFile);
		StreamingResponseBody stream = (os) -> {
			readAndWrite(videoFileStream, os);
			videoFileStream.close();
		};
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("video/mp4"));
		try {
			headers.setContentLength(Files.size(Paths.get(video.getLocation())));
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<StreamingResponseBody>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// headers.setConnection("Keep-Alive");
		headers.set("Accept-Ranges", "bytes");
		return new ResponseEntity<StreamingResponseBody>(stream, headers, HttpStatus.OK);
	}

	private void readAndWrite(final InputStream is, OutputStream os) throws IOException {
		byte[] data = new byte[1024];
		int read = 0;
		while ((read = is.read(data)) > 0) {
			os.write(data, 0, read);
		}
		os.flush();
	}
}
