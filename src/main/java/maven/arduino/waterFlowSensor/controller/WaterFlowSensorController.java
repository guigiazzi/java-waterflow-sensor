package maven.arduino.waterFlowSensor.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WaterFlowSensorController {
	
	private static final String URL = "http://blynk-cloud.com/24d6fecc78b74ce39ed55c8a09f0823f/get/V5";
	
	private static final String USER_AGENT = "Mozilla/5.0";
			
	@RequestMapping(value="/getData", method=RequestMethod.GET)
	public String getData(){		
		URL obj;
		HttpURLConnection con;
		try {
			obj = new URL(URL);
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			int responseCode;
			responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				return response.toString();
			} else {
				return "GET request not worked";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
