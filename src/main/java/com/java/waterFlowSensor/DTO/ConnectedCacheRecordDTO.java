package com.java.waterFlowSensor.DTO;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ConnectedCacheRecordCollection")
public class ConnectedCacheRecordDTO {

	private String _id;
	private String username;
	private String deviceId;
	private List<DataPointDTO> dataPoints;
//	private String timestamp;
	private boolean isConnected;

	public ConnectedCacheRecordDTO(String username, String deviceId, List<DataPointDTO> dataPoints,
			boolean isConnected) {
		super();
		this.username = username;
		this.deviceId = deviceId;
		this.dataPoints = dataPoints;
		this.isConnected = isConnected;
	}

}
