package hr.go2.play.configuration;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hr.go2.play.jobs.VideoCleanerJob;

@Configuration
public class SchedulerConfig {
	
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
}
