package com.java.waterFlowSensor.util;

import java.util.List;

import com.java.waterFlowSensor.DTO.DataPointDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CacheRecordUtil {
	
	private String username;
	private List<DataPointDTO> dataPoints;
	
}
