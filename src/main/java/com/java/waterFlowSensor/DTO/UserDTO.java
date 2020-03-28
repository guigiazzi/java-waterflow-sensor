package com.java.waterFlowSensor.DTO;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import com.java.waterFlowSensor.enums.GenderEnum;
import com.java.waterFlowSensor.enums.RegionEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter @RequiredArgsConstructor
@Document(collection = "UserCollection")
public class UserDTO {
	
	@NotNull private String username;
	@NotNull private String password;
	@NotNull private String email;
	@NotNull private String phone;
	@NotNull private RegionEnum region;
	@NotNull private GenderEnum gender;
	@NotNull private String address;
			
}
