package com.java.waterFlowSensor.DTO;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "FixedChartViewCardCollection")
@JsonInclude(Include.NON_NULL)
public class FixedChartViewCardDTO {

	@NotBlank
	private String title;
	@NotBlank
	private String type;
	@NotBlank
	private String chartId;

}
