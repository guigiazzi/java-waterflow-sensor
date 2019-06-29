package maven.arduino.waterFlowSensor.mongoDB;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Repository;

//@Repository
public class MongoDBConnection {
	
	private MongoClient mongoClient;
	
	//private MongoDatabase database;

	private DB database;
	
	private DBCollection collection;
	
	public MongoDBConnection() {
	}
	
	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	/*public MongoDatabase getDatabase() {
		return database;
	}

	public void setDatabase(MongoDatabase database) {
		this.database = database;
	}*/

	public DB getDatabase() {
		return database;
	}

	public void setDatabase(DB database) {
		this.database = database;
	}

	public DBCollection getCollection() {
		return collection;
	}

	public void setCollection(DBCollection collection) {
		this.collection = collection;
	}	
	
	/*public void openConnection() {
		MongoClientURI uri = new MongoClientURI("mongodb+srv://admin:admin@waterflowsensorcluster-qkxa6.gcp.mongodb.net/WaterFlowSensorDB?retryWrites=true&w=majority");
		this.mongoClient = new MongoClient(uri);
		this.database = mongoClient.getDatabase("WaterFlowSensorDB");
	}*/

	public void openConnection() {
		try {
			this.mongoClient = new MongoClient();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.database = mongoClient.getDB("local");
		this.collection = database.getCollection("startup_log");
	}
	
	public void closeConnection() {
		this.mongoClient.close();
	}
	
	public void store(String key, String value) {
		DBObject dataCollected = new BasicDBObject("_id", key)
				.append("flowRate", value);
		
		this.collection.insert(dataCollected);
	}
}
