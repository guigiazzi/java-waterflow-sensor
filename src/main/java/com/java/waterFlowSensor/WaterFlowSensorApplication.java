package com.java.waterFlowSensor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.java.waterFlowSensor.DTO.ChartViewDTO;

@SpringBootApplication
@EnableScheduling
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
	
//	@Async
//    @Scheduled(fixedRate = 45000)
//    public void scheduleFixedRateTaskAsync() throws InterruptedException {
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formatDate = sdf.format(date);
//        System.out.println(formatDate);
//        mongoTemplate.remove(new Query(
//        		Criteria.where("timestamp").lte(formatDate)),
//        		CacheRecordDTO.class);
//        System.out.println("Registros de cache deletados com sucesso");
//    }
	
}

