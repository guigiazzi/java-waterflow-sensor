package com.java.waterFlowSensor.DTO;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "FixedChartViewCardCollection")
public class FixedChartViewCardDTO {

	@NotBlank
	private String description;
	@NotBlank
	private String type;
	@NotBlank
	private String chartId;

}
