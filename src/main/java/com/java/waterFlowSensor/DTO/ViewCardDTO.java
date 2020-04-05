package com.java.waterFlowSensor.DTO;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "ViewCardCollection")
public class ViewCardDTO {

	@NotBlank
	private String title;
	@NotBlank
	private String description;
	@NotBlank
	private String username;

}
