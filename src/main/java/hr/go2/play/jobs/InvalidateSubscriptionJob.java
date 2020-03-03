package hr.go2.play.jobs;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import hr.go2.play.services.SubscriptionService;

public class InvalidateSubscriptionJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(InvalidateSubscriptionJob.class);

	@Autowired
	private SubscriptionService subscriptionService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.debug("InvalidateSubscriptionJob: Started");
		Date now = new Date();
		int numberOfInvalidatingSubscriptions = subscriptionService.numberOfInvalidatingSubscriptions(now);
		if (numberOfInvalidatingSubscriptions > 0) {
			logger.info("About to invalidate " + numberOfInvalidatingSubscriptions + " subscription(s).");
		}
		subscriptionService.updateValidityByTime(now);
		logger.debug("InvalidateSubscriptionJob: Finished");
	}


}
