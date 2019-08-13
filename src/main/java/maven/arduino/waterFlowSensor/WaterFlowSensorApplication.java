package maven.arduino.waterFlowSensor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WaterFlowSensorApplication {
	public static void main(String[] args) {
		SpringApplication.run(WaterFlowSensorApplication.class, args);
	}
}
