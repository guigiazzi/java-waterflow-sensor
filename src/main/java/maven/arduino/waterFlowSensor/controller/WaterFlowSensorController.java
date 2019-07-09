package maven.arduino.waterFlowSensor.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import maven.arduino.waterFlowSensor.date.DateAndTime;
import maven.arduino.waterFlowSensor.domain.WaterFlowSensorDomain;
import maven.arduino.waterFlowSensor.mongoDB.MongoDBConnection;

//@RestController
public class WaterFlowSensorController {

	private static final String WATERFLOW_URL = "http://blynk-cloud.com/24d6fecc78b74ce39ed55c8a09f0823f/get/V5";

	private static final String USER_URL = "http://blynk-cloud.com/24d6fecc78b74ce39ed55c8a09f0823f/get/V6";

	private static final String DEVICEID_URL = "http://blynk-cloud.com/24d6fecc78b74ce39ed55c8a09f0823f/get/V7";

	private static final String USER_AGENT = "Mozilla/5.0";

	//@Autowired
	private WaterFlowSensorDomain domain;

	//@Autowired
	private MongoDBConnection mongo;

	private StringBuffer response;

	private final Logger LOGGER = LoggerFactory.getLogger(WaterFlowSensorController.class);

	//@RequestMapping(value = "/getData", method = RequestMethod.GET)
	public void getData() {
		this.domain = new WaterFlowSensorDomain();
		this.mongo = new MongoDBConnection();
		this.mongo.openConnection();
		
		sendGETRequest(WATERFLOW_URL);
		String value = response.toString();
		this.domain.setValue(value);
		
		sendGETRequest(USER_URL);
		String userId = response.toString();
		this.domain.setUser(userId);
		
		sendGETRequest(DEVICEID_URL);
		String deviceId = response.toString();
		this.domain.setDeviceId(deviceId);
		
		this.mongo.store(this.domain);
		this.mongo.closeConnection();
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
				this.response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					this.response.append(inputLine);
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