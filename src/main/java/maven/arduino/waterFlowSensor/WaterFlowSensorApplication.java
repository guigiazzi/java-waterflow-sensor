package maven.arduino.waterFlowSensor;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import maven.arduino.waterFlowSensor.controller.WaterFlowSensorController;

@SpringBootApplication
public class WaterFlowSensorApplication {
	public static void main(String[] args) {
		SpringApplication.run(WaterFlowSensorApplication.class, args);
		
		JobDetail job = JobBuilder.newJob(WaterFlowSensorController.class)
				.withIdentity("waterFlowSensorJob", "group1").build();
		
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("waterFlowSensorJob", "group1")
				.withSchedule(
					SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInSeconds(5).repeatForever())
				.build();
		
		Scheduler scheduler;
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
