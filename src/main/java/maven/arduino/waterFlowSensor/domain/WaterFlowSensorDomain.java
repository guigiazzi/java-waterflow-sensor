package maven.arduino.waterFlowSensor.domain;

import org.springframework.stereotype.Component;

//@Component
public class WaterFlowSensorDomain {
	
	private String user;
	
	private String deviceId;
	
	private String value;
		
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}