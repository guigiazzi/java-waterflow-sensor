package com.java.waterFlowSensor.DTO;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "ChartViewCollection")
public class ChartViewDTO {

//	@Transient
//    public static final String chartId = "users_sequence";
	@NotBlank
	private String chartId;
	@NotBlank
	private String username;
	@NotBlank
	private String title;
	@NotBlank
	private String description;
	@NotBlank
	private String type;

}
