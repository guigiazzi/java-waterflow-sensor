package maven.arduino.waterFlowSensor;

import maven.arduino.waterFlowSensor.controller.WaterFlowSensorController;

public class WaterFlowSensorApplication {
	public static void main(String[] args) {
		WaterFlowSensorController controller = new WaterFlowSensorController();
		controller.getData();
	}
}
