package com.java.waterFlowSensor.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ChartViewDTO {

	private String chartId;
	private String title;
	private String type;
	private List<DataPointDTO> dataPoints;
	private boolean connectedDevice;

}
