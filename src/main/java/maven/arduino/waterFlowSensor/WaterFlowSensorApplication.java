package maven.arduino.waterFlowSensor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import maven.arduino.waterFlowSensor.controller.WaterFlowSensorController;

//@SpringBootApplication
public class WaterFlowSensorApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(WaterFlowSensorApplication.class, args);
//	}

	public static void main(String[] args) {
		WaterFlowSensorController controller = new WaterFlowSensorController();
		System.out.println(controller.getData());
	}
}
