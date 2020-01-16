package hr.go2.play.controllers;

import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.go2.play.configuration.MultipartFileSender;

@RestController
@RequestMapping("/api/video")
@Secured("ROLE_USER")
public class TestVideoController {

	@GetMapping("/getVideo")
	public void getUser(HttpServletRequest httprequest, HttpServletResponse httpresponse) {

		try {
			MultipartFileSender.fromPath(Paths.get("C:\\videos\\test")).with(httprequest).with(httpresponse)
					.serveResource();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
