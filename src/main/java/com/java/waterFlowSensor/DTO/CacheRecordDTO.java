package com.java.waterFlowSensor.DTO;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "CacheRecordCollection")
public class CacheRecordDTO {
	
	private String username;
	private List<DataPointDTO> dataPoints;
	private String timestamp;
	
}
