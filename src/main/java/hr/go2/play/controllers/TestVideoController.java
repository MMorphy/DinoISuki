package hr.go2.play.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/api/video")
@Secured("ROLE_USER")
public class TestVideoController {

//	@GetMapping("/getVideo")
//	public void getUser(HttpServletRequest httprequest, HttpServletResponse httpresponse) {
//
//		try {
//			MultipartFileSender.fromPath(Paths.get("C:\\videos\\test1.mp4")).with(httprequest).with(httpresponse)
//					.serveResource();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}


//	@GetMapping("/getVideo")
//	public ResponseEntity<UrlResource> getFullVideo() throws MalformedURLException {
//	    String filename = "C:\\videos\\test1.mp4";
//	    File file = new File(filename);
//
//	        final UrlResource video = new UrlResource(String.format("file:%s", filename));
//
//	        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
//	                .contentType(MediaTypeFactory.getMediaType(video)
//	                        .orElse(MediaType.APPLICATION_OCTET_STREAM))
//	                .body(video);
//	}
	@GetMapping("/getVideo")
	public StreamingResponseBody stream()
			throws FileNotFoundException {
	    String filename = "C:\\videos\\test1.mp4";
	    File videoFile = new File(filename);
		final InputStream videoFileStream = new FileInputStream(videoFile);
		return (os) -> {
			readAndWrite(videoFileStream, os);
		};
	}
	private void readAndWrite(final InputStream is, OutputStream os)
			throws IOException {
		byte[] data = new byte[2048];
		int read = 0;
		while ((read = is.read(data)) > 0) {
			os.write(data, 0, read);
		}
		os.flush();
	}
}
