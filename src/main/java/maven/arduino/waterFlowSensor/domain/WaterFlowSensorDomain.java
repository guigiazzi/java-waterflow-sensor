package maven.arduino.waterFlowSensor.domain;

import org.springframework.stereotype.Component;

//@Component
public class WaterFlowSensorDomain {
	
	private String key;
	
	private String value;
	
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
