package hr.go2.play.configuration;

import javax.annotation.PostConstruct;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hr.go2.play.jobs.InvalidateSubscriptionJob;
import hr.go2.play.jobs.MoveToArchiveJob;
import hr.go2.play.jobs.VideoFinderJob;
import hr.go2.play.util.Commons;

@Configuration
public class SchedulerConfig {

//	@Value("${application.job.video-finder.interval}")
	int intervalInSeconds;
//	@Value("${application.job.invalidate-subscription.interval}")
	int invalidateSubscriptionJob_intervalInSeconds;
//	@Value("${application.job.move-to-archive.interval}")
	int moveToArchiveJob_intervalInSeconds;

	@Autowired
	Commons commons;

	@PostConstruct
	private void initVariables() {
		intervalInSeconds = commons.getProperty("application_job_videoFinder_interval", Integer.class);
		invalidateSubscriptionJob_intervalInSeconds = commons.getProperty("application_job_invalidateSubscription_interval", Integer.class);
		moveToArchiveJob_intervalInSeconds = commons.getProperty("application_job_moveToArchive_interval", Integer.class);
	}

	@Bean
	public JobDetail videoFinderJobDetail() {
		return JobBuilder.newJob(VideoFinderJob.class).withIdentity("videoFinder").storeDurably().build();
	}

	@Bean
	public Trigger videoFinderJobTrigger() {
		SimpleScheduleBuilder videoFinderScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
				.withIntervalInSeconds(intervalInSeconds).repeatForever();
		return TriggerBuilder.newTrigger().forJob(videoFinderJobDetail()).withIdentity("videoFinder")
				.withSchedule(videoFinderScheduleBuilder).build();
	}



	@Bean
	public JobDetail invalidateSubscriptionJobDetail() {
		return JobBuilder.newJob(InvalidateSubscriptionJob.class).withIdentity("invalidateSubscription").storeDurably().build();
	}

	@Bean
	public Trigger invalidateSubscriptionJobTrigger() {
		SimpleScheduleBuilder invalidateSubscriptionScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(invalidateSubscriptionJob_intervalInSeconds).repeatForever();
		return TriggerBuilder.newTrigger().forJob(invalidateSubscriptionJobDetail()).withIdentity("invalidateSubscription").withSchedule(invalidateSubscriptionScheduleBuilder).build();
	}



	@Bean
	public JobDetail moveToArchiveJobDetail() {
		return JobBuilder.newJob(MoveToArchiveJob.class).withIdentity("moveToArchive").storeDurably().build();
	}

	@Bean
	public Trigger moveToArchiveJobTrigger() {
		SimpleScheduleBuilder moveToArchiveScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(moveToArchiveJob_intervalInSeconds).repeatForever();
		return TriggerBuilder.newTrigger().forJob(moveToArchiveJobDetail()).withIdentity("moveToArchive").withSchedule(moveToArchiveScheduleBuilder).build();
	}

}
