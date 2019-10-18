package hr.go2.play.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class VideoCleanerJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(VideoCleanerJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("Opalio se VideoCleaner");
	}

}
