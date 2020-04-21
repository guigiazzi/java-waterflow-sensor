package com.java.waterFlowSensor.DTO;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataPointDTO {

	@NotBlank
	private double y;
	@NotBlank
	private String label;
	@NotBlank	
	private String name;
//	@NotBlank
//	private String indexLabel;
	@NotBlank
	private String legendText;
}
