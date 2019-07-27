package maven.arduino.waterFlowSensor.domain;

public class WaterFlowSensorDomain {
	
	private String user;
	
	private String deviceId;
	
	private String flowRate;
	
	private String description;
	
	private String timestamp;

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

	public String getFlowRate() {
		return flowRate;
	}

	public void setFlowRate(String flowRate) {
		this.flowRate = flowRate;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return "{\"flowRate\": \"" + this.flowRate + "\", \"userID\": \"" + this.user + "\", \"deviceID\": \"" + this.deviceId + "\", \"description\": \"" + this.description + "\", \"timestamp\": \"" + this.timestamp + "\"}";
	}
}