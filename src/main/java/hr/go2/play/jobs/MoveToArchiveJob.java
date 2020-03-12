package hr.go2.play.jobs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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

import hr.go2.play.entities.Video;
import hr.go2.play.services.VideoService;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

public class MoveToArchiveJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(MoveToArchiveJob.class);

	@Value("${application.job.move-to-archive.ip}")
	String remoteIp;
	@Value("${application.job.move-to-archive.username}")
	String remoteUsername;
	@Value("${application.job.move-to-archive.password}")
	String remotePassword;
	@Value("${application.job.move-to-archive.remote-archive-folder}")
	String remoteArchiveFolder;
	@Value("${application.job.video-finder.archive-folder-location}")
	String archiveFolderLocation;
	@Value("${application.job.move-to-archive.time-to-archive-found-videos-in-days}")
	int timeToArchiveFound;
	@Value("${application.job.move-to-archive.time-to-archive-archived-videos-in-days}")
	int timeToArchiveArchived;

	@Autowired
	private VideoService videoService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.debug("MoveToArchiveJob: Started");

		LocalDateTime findVideosUntil = LocalDateTime.now().minusDays(timeToArchiveFound);
		Date findVideosUntilDate = Date.from(findVideosUntil.atZone(ZoneId.systemDefault()).toInstant());
		List<Video> oldVideos = videoService.findVideosOlderThanDate(findVideosUntilDate);

		oldVideos.addAll(getArchivedVideoToBackup()); // adding also videos from archive

		int counter = 0;
		if (!oldVideos.isEmpty()) {
			@SuppressWarnings("resource")
			SSHClient client = new SSHClient();
			client.addHostKeyVerifier(new PromiscuousVerifier());
			try {
				client.connect(remoteIp);
				client.authPassword(remoteUsername, remotePassword);

				SFTPClient sftpClient = client.newSFTPClient();

				for (Video oldVideo : oldVideos) {
					String videoLocation = oldVideo.getLocation();
					String fileName = videoLocation.substring(videoLocation.lastIndexOf(File.separator) + 1, videoLocation.length());
					File videoFile = new File(videoLocation);
					if (videoFile.exists()) {
						sftpClient.put(videoLocation, remoteArchiveFolder + File.separator + fileName);
						videoFile.delete();
						counter++;
					} else {
						oldVideo.setLocation("*not-found*" + fileName);
					}
					oldVideo.setArchived(true);
					if (oldVideo.getId() != -1) { // skipping videos prepared for archive (out of working hours)
						videoService.saveVideo(oldVideo);
					}
				}
				sftpClient.close();
				client.disconnect();
			} catch (IOException e) {
				logger.error("Unable to connect to: " + remoteIp + " # " + e.getMessage());
			} catch (Exception e) {
				logger.error("Unable to connect to: " + remoteIp + " # " + e.getMessage());
			}
		}
		logger.debug("MoveToArchiveJob: Finished Moved " + counter + " video files.");
	}

	private List<Video> getArchivedVideoToBackup() {
		LocalDateTime findVideosUntil = LocalDateTime.now().minusDays(timeToArchiveArchived);
		Date findVideosUntilDate = Date.from(findVideosUntil.atZone(ZoneId.systemDefault()).toInstant());
		List<Video> listOfVideos = new ArrayList<Video>();
		List<String> listOfFiles = new ArrayList<String>();
		try (Stream<Path> walk = Files.walk(Paths.get(archiveFolderLocation), 1)) {
			listOfFiles = walk.map(x -> x.toString()).filter(file -> file.substring(file.lastIndexOf(File.separator) + 1).matches("^([A-Z]{3}[0-9]{1})_([2][0][2-3][0-9][0][0-9]|[1][0-2])([0][0-9]|[1][0-9]|[2][0-9]|[3][0-1])_[0-2][0-9][0-5][0-9].mp4")).collect(Collectors.toList());
			if (!listOfFiles.isEmpty()) {
				for (String videoFilePath : listOfFiles) {
					String videoFileName = videoFilePath.substring(videoFilePath.lastIndexOf(File.separator) + 1, videoFilePath.lastIndexOf("."));
					String dateFromVideo = videoFileName.substring(5, videoFileName.length());
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmm");
					try {
						Date videoDate = format.parse(dateFromVideo);
						if (videoDate.before(findVideosUntilDate)) {
							Video oldVideo = new Video();
							oldVideo.setId((long) -1); // for archived videos set to -1
							oldVideo.setLocation(videoFilePath);
							listOfVideos.add(oldVideo);
						}
					} catch (ParseException e) {
						logger.error("Unrecognized video file name format: " + videoFileName + " # " + e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			logger.error("Unable to fetch archive files # " + e.getMessage());
			e.printStackTrace();
		}
		logger.error("Found archive files: " + listOfVideos.size());
		return listOfVideos;
	}
}
