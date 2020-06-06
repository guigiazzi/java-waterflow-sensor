package com.java.waterFlowSensor.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.java.waterFlowSensor.enums.GenderEnum;
import com.java.waterFlowSensor.enums.RegionEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "UserCollection")
@JsonInclude(Include.NON_NULL)
public class UserDTO {
	
	private String _id;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String name;
	@NotBlank
	private String email;
	@NotNull
	private GenderEnum sex;
	@NotNull
	private RegionEnum region;
	@NotBlank
	private String phoneNumber;
	@NotBlank
	private String address;

}
