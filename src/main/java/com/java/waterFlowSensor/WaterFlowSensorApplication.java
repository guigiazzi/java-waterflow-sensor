package com.java.waterFlowSensor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import com.java.waterFlowSensor.DTO.ChartViewDTO;

@SpringBootApplication
@EnableAsync
public class WaterFlowSensorApplication {
	public static void main(String[] args) {
		SpringApplication.run(WaterFlowSensorApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ChartViewDTO chartView() {
		return new ChartViewDTO();
	}
	
}

