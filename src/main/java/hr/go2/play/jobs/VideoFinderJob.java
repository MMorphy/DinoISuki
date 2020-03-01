package hr.go2.play.jobs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;

import hr.go2.play.entities.Camera;
import hr.go2.play.entities.Video;
import hr.go2.play.impl.CameraServiceImpl;
import hr.go2.play.util.Commons;

public class VideoFinderJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(VideoFinderJob.class);

	@Value("${application.job.video-finder.search-folder-location}")
	String searchFolderLocation;
	@Value("${application.job.video-finder.found-folder-location}")
	String foundFolderLocation;
	@Value("${application.job.video-finder.error-folder-location}")
	String errorFolderLocation;

	@Autowired
	private Commons commons;
	@Autowired
	private CameraServiceImpl cameraService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.debug("VideoFinderJob: Started");
		// search for *.mp4 files
		List<String> listOfFiles = new ArrayList<String>();

		try (Stream<Path> walk = Files.walk(Paths.get(searchFolderLocation), 1)) {
			// When moving to unix, need to change the lastIndex of \\ to /
			// Current format is <NAMEOFCAM(3 alpha + 1 number)>_yyyyMMdd_hhmm.mp4
			listOfFiles = walk.map(x -> x.toString()).filter(file -> file.substring(file.lastIndexOf("\\") + 1).matches(
					"^([A-Z]{3}[0-9]{1})_([2][0][2-3][0-9][0][0-9]|[1][0-2])([0][0-9]|[1][0-9]|[2][0-9]|[3][0-1])_[0-2][0-9][0-5][0-9].mp4"))
					.collect(Collectors.toList());

			if (listOfFiles.isEmpty()) {
				logger.debug("VideoFinderJob: No videos found. Exiting.");
				return;
			}

			// move the files to new location
			int movedVideoCounter = 0;

			if (checkFolders()) {
				for (String videoLocation : listOfFiles) {
					File videoFile = new File(videoLocation);
					String cameraName = videoFile.getName().substring(0, 4);
					Camera cam = cameraService.findCameraByName(cameraName);
					if (cam == null) {
						videoFile.renameTo(new File(errorFolderLocation + "\\" + videoFile.getName()));
						logger.warn("Found video with no camera!");
					} else {
						if (videoFile.renameTo(new File(foundFolderLocation + "\\" + videoFile.getName()))) {
							// persist the video locations
							Video video = new Video();
							video.setLocation(foundFolderLocation + "\\" + videoFile.getName());
							video.setStartedAt(commons.formatDateFromString(videoFile.getName().substring(5, 18), "VIDEO"));
							cameraService.addVideo(cam, video);
							movedVideoCounter++;
						} else {
							logger.warn("VideoFinderJob: File " + videoFile.getAbsolutePath() + " is failed to move!");
						}
					}
				}
			}

			logger.debug("VideoFinderJob: Finished. Moved " + movedVideoCounter + " files.");
		} catch (IOException e) {
			logger.error("VideoFinderJob: Unable to search for video files. ", e);
		} catch (Exception e) {
			logger.error("VideoFinderJob: Error in video finder job. ", e);
		}
	}

	private boolean checkFolders() {
		boolean created = true;
		File folder = new File(foundFolderLocation);
		if (!folder.exists() || !folder.isDirectory()) {
			// creating "found" folder
			try {
				folder.mkdir();
			} catch (SecurityException e) {
				logger.error("VideoFinderJob: Unable to create found folder. ", e);
				created = false;
			}
		}
		folder = new File(errorFolderLocation);
		if (!folder.exists() || !folder.isDirectory()) {
			// creating "found" folder
			try {
				folder.mkdir();
			} catch (SecurityException e) {
				logger.error("VideoFinderJob: Unable to create error folder. ", e);
				created = false;
			}
		}
		return created;
	}
}
