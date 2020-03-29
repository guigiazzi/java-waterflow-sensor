package com.java.waterFlowSensor.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import com.java.waterFlowSensor.enums.GenderEnum;
import com.java.waterFlowSensor.enums.RegionEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "UserCollection")
public class UserDTO {

	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String email;
	@NotBlank
	private String phone;
	@NotNull
	private RegionEnum region;
	@NotNull
	private GenderEnum gender;
	@NotBlank
	private String address;

}
