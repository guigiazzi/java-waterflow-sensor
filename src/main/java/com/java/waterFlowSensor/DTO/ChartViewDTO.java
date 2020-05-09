package com.java.waterFlowSensor.DTO;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ChartViewDTO {

	@NotBlank
	private String chartId;
	@NotBlank
	private String title;
	@NotBlank
	private String type;
	@NotBlank
	private List<DataPointDTO> dataPoints;

}
