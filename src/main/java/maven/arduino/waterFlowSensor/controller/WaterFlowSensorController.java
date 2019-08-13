package maven.arduino.waterFlowSensor.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import maven.arduino.waterFlowSensor.date.DateAndTime;
import maven.arduino.waterFlowSensor.domain.WaterFlowSensorDomain;
import maven.arduino.waterFlowSensor.mongoDB.MongoDBConnection;

@RestController
public class WaterFlowSensorController implements Job {

	private static final String WATERFLOW_URL = "http://blynk-cloud.com/24d6fecc78b74ce39ed55c8a09f0823f/get/V5";

	private static final String USER_URL = "http://blynk-cloud.com/24d6fecc78b74ce39ed55c8a09f0823f/get/V6";

	private static final String DEVICEID_URL = "http://blynk-cloud.com/24d6fecc78b74ce39ed55c8a09f0823f/get/V7";
	
	private static final String DESCRIPTION_URL = "http://blynk-cloud.com/24d6fecc78b74ce39ed55c8a09f0823f/get/V8";

	private static final String USER_AGENT = "Mozilla/5.0";

	@Autowired
	private WaterFlowSensorDomain domain;

	@Autowired
	private MongoDBConnection mongo;
	
	private String responseString;
	
	//dados falsos para analise
	private String userList[] = {"João da Silva", "Matheus Augusto", "Felipe Rocha", "Guilherme Henrique"};
	private String deviceIdList[] = {"00-AA-01-BB-CC", "11-BB-02-CC-DD", "22-CC-03-D, D-EE", "33-DD-04-EE-FF"};
	private String descriptionList[] = {"Pia do banheiro", "Filtro de água", "Máquina de lavar roupas", "Mangueira do jardim"};

	private final Logger LOGGER = LoggerFactory.getLogger(WaterFlowSensorController.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("hello world");
	}
	
	@Bean
	public JobDetail jobDetail() {
		return JobBuilder.newJob().ofType(WaterFlowSensorController.class)
				.storeDurably()
				.withIdentity("job do waterFlowSensor")
				.withDescription("inicia um job do waterFlowSensor")
				.build();
	}
	
	@Bean
	public Trigger trigger (JobDetail job) {
		return TriggerBuilder.newTrigger().forJob(job)
				.withIdentity("trigger para o job do waterFlowSensor")
				.withDescription("inicia um trigger para chamar o job do waterFlowSensor")
				.build();
	}
	
	@Bean
	public Scheduler scheduler(Trigger trigger,  JobDetail job, SchedulerFactoryBean factory) throws SchedulerException{
		Scheduler scheduler = factory.getScheduler();
		scheduler.scheduleJob(job, trigger);
		scheduler.start();
		return scheduler;
	}
	
	@RequestMapping(value = "/getData", method = RequestMethod.GET)
	public String getData() {
		this.mongo.openConnection();
		
		sendGETRequest(WATERFLOW_URL);
		double flowRate = Double.parseDouble(this.responseString);
		//this.domain.setFlowRate(flowRate);
		Random r1 = new Random();
		this.domain.setFlowRate(r1.nextInt(36));
		
		sendGETRequest(USER_URL);
		String userId = this.responseString;
		//this.domain.setUser(userId);
		Random r2 = new Random();
		this.domain.setUser(userList[r2.nextInt(4)]);
		
		sendGETRequest(DEVICEID_URL);
		String deviceId = this.responseString;
		//this.domain.setDeviceId(deviceId);
		int randomNumber = r2.nextInt(4);
		this.domain.setDeviceId(deviceIdList[randomNumber]);
		
		sendGETRequest(DESCRIPTION_URL);
		String description = this.responseString;
		//this.domain.setDescription(description);
		this.domain.setDescription(descriptionList[randomNumber]);
		
		DateAndTime time = new DateAndTime();
		String timestamp = time.getTimestamp();
		this.domain.setTimestamp(timestamp);
		
		try {
			LOGGER.info("Inserindo " + this.domain.toString() + " no Mongo");
			this.mongo.store(this.domain);
		} catch(Exception e) {
			LOGGER.error("Ocorreu um erro ao inserir no Mongo", e);
		}
		
		this.mongo.closeConnection();
		
		return this.domain.toString();
	}

	private void sendGETRequest(String URL) {
		try {
			URL obj = new URL(URL);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			int responseCode;
			responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
								
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				
				this.responseString = response.toString();

				if(this.responseString.contains("[") && this.responseString.contains("]")) { // removendo caracteres [ e ]
					this.responseString = this.responseString.replace("[", "").replace("]", "");
				}
				
				if(this.responseString.contains("\"")) { // removendo aspas, pois o Mongo ja as insere, ficando duplicado
					this.responseString = this.responseString.replace("\"", "");
				}
				
				in.close();
				
			} else {
				throw new UnknownError();
			}
		} catch (Exception e) {
			LOGGER.error("Ocorreu um erro ao mandar a requisição GET");
		}
	}
}