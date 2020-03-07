package hr.go2.play.jobs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;

import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import hr.go2.play.entities.Camera;
import hr.go2.play.entities.Location;
import hr.go2.play.entities.Video;
import hr.go2.play.entities.WorkingHours;
import hr.go2.play.impl.CameraServiceImpl;
import hr.go2.play.services.LocationService;
import hr.go2.play.util.Commons;

public class VideoFinderJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(VideoFinderJob.class);

	@Value("${application.job.video-finder.search-folder-location}")
	String searchFolderLocation;
	@Value("${application.job.video-finder.found-folder-location}")
	String foundFolderLocation;
	@Value("${application.job.video-finder.error-folder-location}")
	String errorFolderLocation;
	@Value("${application.job.video-finder.archive-folder-location}")
	String archiveFolderLocation;

	@Autowired
	private Commons commons;
	@Autowired
	private CameraServiceImpl cameraService;
	@Autowired
	private LocationService locationService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.debug("VideoFinderJob: Started");

		// search for *.mp4 files
		List<String> listOfFiles = new ArrayList<String>();

		try (Stream<Path> walk = Files.walk(Paths.get(searchFolderLocation), 1)) {
			// When moving to unix, need to change the lastIndex of \\ to /
			// Current format is <NAMEOFCAM(3 alpha + 1 number)>_yyyyMMdd_hhmm.mp4
			listOfFiles = walk.map(x -> x.toString()).filter(file -> file.substring(file.lastIndexOf(File.separator) + 1).matches(
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
						videoFile.renameTo(new File(errorFolderLocation + File.separator + videoFile.getName()));
						logger.warn("Found video with no camera!");
					} else if (isOutsideWorkingHours(cam.getName(), videoFile.getName())) {
						// outside working hours - preparing for backup
						logger.debug("Video: " + videoFile.getName() + " is outside working hours");
						videoFile.renameTo(new File(archiveFolderLocation + File.separator + videoFile.getName()));
					} else {
						if (videoFile.renameTo(new File(foundFolderLocation + File.separator + videoFile.getName()))) {
							// persist the video locations
							Video video = new Video();
							video.setLocation(foundFolderLocation + File.separator + videoFile.getName());
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
			// creating "error" folder
			try {
				folder.mkdir();
			} catch (SecurityException e) {
				logger.error("VideoFinderJob: Unable to create error folder. ", e);
				created = false;
			}
		}
		folder = new File(archiveFolderLocation);
		if (!folder.exists() || !folder.isDirectory()) {
			// creating "archive" folder
			try {
				folder.mkdir();
			} catch (SecurityException e) {
				logger.error("VideoFinderJob: Unable to create archive folder. ", e);
				created = false;
			}
		}
		return created;
	}

	private boolean isOutsideWorkingHours(String cameraName, String videoFileName) {
		Optional<Location> locationOpt = locationService.findLocationByCameraName(cameraName);
		if (locationOpt.isPresent()) {
			Location location = locationOpt.get();
			List<WorkingHours> workingHoursList = (List<WorkingHours>) locationService.findWorkingHoursByLocationId(location.getId());
			if (workingHoursList == null || !workingHoursList.isEmpty()) {
				// extract date from video name (<NAMEOFCAM(3 alpha + 1 number)>_yyyyMMdd_hhmm.mp4)
				String dateFromVideo = videoFileName.substring(5, videoFileName.length());
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_hhmm");
				try {
					Date videoDate = format.parse(dateFromVideo);
					WorkingHours workingHours = getWorkingHoursOnDate(workingHoursList, videoDate);
					// logger.debug("workingHours:" + workingHours.getFromTime() + " - " + workingHours.getToTime() + " video time: " + videoDate);
					if (timeToInt(workingHours.getFromTime()) > timeToInt(videoDate) || timeToInt(workingHours.getToTime()) < timeToInt(videoDate)) {
						return true;
					}
				} catch (ParseException e) {
					logger.warn("Unable to parse date from video: " + videoFileName + " # " + e.getMessage());
				}
			} else {
				logger.warn("No working hours found for location: " + location.getName());
			}
		} else {
			logger.warn("Unable to find location for camera: " + cameraName + " on video file:" + videoFileName);
		}

		return false;
	}

	private int timeToInt(Date inputDate) {
		if (inputDate == null) {
			return 0;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		String dateString = sdf.format(inputDate);
		int result = Integer.parseInt(dateString.substring(0, 2)) * 60 + Integer.parseInt(dateString.substring(2, dateString.length()));
		return result;
	}

	private WorkingHours getWorkingHoursOnDate(List<WorkingHours> workingHoursList, Date videoDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = sdf.format(videoDate);
		// is there a special case with this exact date?
		for (WorkingHours workingHours : workingHoursList) {
			if (workingHours.getDayType() != null && workingHours.getDayType().getType().contains(dateString)) {
				return workingHours;
			}
		}
		if (isOnHoliday(videoDate)) {
			// it's a holiday!!!
			for (WorkingHours workingHours : workingHoursList) {
				if (workingHours.getDayType() != null && workingHours.getDayType().getType().equals("HOLIDAY")) {
					return workingHours;
				}
			}
			for (WorkingHours workingHours : workingHoursList) {
				if (workingHours.getDayType() != null && workingHours.getDayType().getType().equals("WEEKEND")) {
					return workingHours;
				}
			}
		} else {
			// not a holiday
			LocalDate date = videoDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
				for (WorkingHours workingHours : workingHoursList) {
					if (workingHours.getDayType() != null && workingHours.getDayType().getType().equals("WEEKEND")) {
						return workingHours;
					}
				}
			} else {
				// regular day
				for (WorkingHours workingHours : workingHoursList) {
					if (workingHours.getDayType() != null && workingHours.getDayType().getType().equals("WORKDAY")) {
						return workingHours;
					}
				}
			}
		}
		// not found anything matching holiday, returning the first one
		return workingHoursList.get(0);
	}

	private boolean isOnHoliday(Date videoDate) {
		LocalDate date = videoDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		HolidayManager m = HolidayManager.getInstance(HolidayCalendar.CROATIA);
		Set<Holiday> holidays = m.getHolidays(date.getYear(), (String) null);
		for (Holiday holiday : holidays) {
			if (date.isEqual(LocalDate.parse(holiday.getDate().toString()))) {
				return true;
			}
		}
		return false;
	}
}
