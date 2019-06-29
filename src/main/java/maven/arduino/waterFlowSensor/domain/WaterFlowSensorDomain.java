package maven.arduino.waterFlowSensor.domain;

import org.springframework.stereotype.Component;

//@Component
public class WaterFlowSensorDomain {
	
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}