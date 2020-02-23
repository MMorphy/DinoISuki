package hr.go2.play.configuration;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hr.go2.play.jobs.VideoCleanerJob;
import hr.go2.play.jobs.VideoFinderJob;

@Configuration
public class SchedulerConfig {
	
	@Value("${application.job.video-finder.interval}") int intervalInSeconds;
	
	@Bean
	public JobDetail videoCleanerJobDetail() {
		return JobBuilder.newJob(VideoCleanerJob.class).withIdentity("videoCleaner").storeDurably().build();
	}

	@Bean
	public Trigger trosakStatJobTrigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10)
				.repeatForever();
		return TriggerBuilder.newTrigger().forJob(videoCleanerJobDetail()).withIdentity("videoCleaner")
				.withSchedule(scheduleBuilder).build();
	}
	
	@Bean
	public JobDetail videoFinderJobDetail() {
		return JobBuilder.newJob(VideoFinderJob.class).withIdentity("videoFinder").storeDurably().build();
	}

	@Bean
	public Trigger videoFinderJobTrigger() {
		SimpleScheduleBuilder videoFinderScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(intervalInSeconds).repeatForever();
		return TriggerBuilder.newTrigger().forJob(videoFinderJobDetail()).withIdentity("videoFinder").withSchedule(videoFinderScheduleBuilder).build();
	}
}
