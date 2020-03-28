package com.java.waterFlowSensor.DTO;

import javax.validation.constraints.NotNull;

import com.java.waterFlowSensor.enums.GenderEnum;
import com.java.waterFlowSensor.enums.RegionEnum;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
	
	private @NotNull String username;
	private @NotNull String password;
	private @NotNull String email;
	private @NotNull String phone;
	private @NotNull RegionEnum region;
	private @NotNull GenderEnum gender;
	private @NotNull String address;
	
	public UserDTO(String username, String password, String email, String phone, RegionEnum region,
			GenderEnum gender, String address) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.region = region;
		this.gender = gender;
		this.address = address;
	}
		
}
