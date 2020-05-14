package com.java.waterFlowSensor.DTO;

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
public class DataPointDTO {

	@NotBlank
	private String x;
	@NotBlank
	private double y;
	@NotBlank
	private String label;
	@NotBlank	
	private String name;
	@NotBlank
	private String legendText;
}
