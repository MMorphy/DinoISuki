package hr.go2.play.configuration;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hr.go2.play.jobs.VideoFinderJob;

@Configuration
public class SchedulerConfig {
	
	@Value("${application.job.video-finder.interval}") int intervalInSeconds;
	
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
