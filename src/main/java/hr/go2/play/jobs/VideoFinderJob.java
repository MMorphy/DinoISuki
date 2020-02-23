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

import hr.go2.play.entities.Video;
import hr.go2.play.repositories.VideoRepository;

public class VideoFinderJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(VideoFinderJob.class);
	
	@Value("${application.job.video-finder.search-folder-location}") String searchFolderLocation;
	@Value("${application.job.video-finder.found-folder-location}") String foundFolderLocation;
	
	@Autowired
	private VideoRepository videoRepository;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.debug("VideoFinderJob: Started");
		// search for *.mp4 files
		List<String> listOfFiles = new ArrayList<String>();
		
		try (Stream<Path> walk = Files.walk(Paths.get(searchFolderLocation), 1)) {

			listOfFiles = walk.map(x -> x.toString())
					.filter(file -> file.endsWith(".mp4")).collect(Collectors.toList());

			if(listOfFiles.isEmpty()) {
				logger.debug("VideoFinderJob: No videos found. Exiting.");
				return;
			}
			
			// move the files to new location
			int movedVideoCounter = 0;
			if(checkFoundFolder()) {
				for(String videoLocation : listOfFiles) {
					File videoFile = new File(videoLocation);
		    		if(videoFile.renameTo(new File(foundFolderLocation + "\\" + videoFile.getName()))) {
		    			// persist the video locations
	    				Video video = new Video();
						video.setLocation(foundFolderLocation + "\\" + videoFile.getName());
						videoRepository.save(video);
						movedVideoCounter++;
		    		} else {
		    			logger.warn("VideoFinderJob: File " + videoFile.getAbsolutePath() + " is failed to move!");
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
	
	private boolean checkFoundFolder() {
		boolean created = true;
		File foundFolder = new File(foundFolderLocation);
		if(!foundFolder.exists() || !foundFolder.isDirectory()) { 
		    // creating "found" folder
			try{
				foundFolder.mkdir();
			} 
		    catch(SecurityException e){
		    	logger.error("VideoFinderJob: Unable to create found folder. ", e);
		    	created = false;
		    }
		}
		return created;
	}
}
