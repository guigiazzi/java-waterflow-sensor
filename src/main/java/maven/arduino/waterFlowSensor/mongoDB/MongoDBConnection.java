package maven.arduino.waterFlowSensor.mongoDB;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import maven.arduino.waterFlowSensor.domain.WaterFlowSensorDomain;

//@Repository
public class MongoDBConnection {
	
	private MongoClient mongoClient;
	
	private MongoDatabase database;

	//private DB database;
	
	private MongoCollection<Document> collection;
	
	public MongoDBConnection() {
	}
	
	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public MongoDatabase getDatabase() {
		return database;
	}

	public void setDatabase(MongoDatabase database) {
		this.database = database;
	}

//	public DB getDatabase() {
//		return database;
//	}
//
//	public void setDatabase(DB database) {
//		this.database = database;
//	}

	public MongoCollection<Document> getCollection() {
		return collection;
	}

	public void setCollection(MongoCollection<Document> collection) {
		this.collection = collection;
	}	
	
	public void openConnection() {
		MongoClientURI uri = new MongoClientURI("mongodb+srv://admin:admin@cluster0-qkxa6.mongodb.net/test?retryWrites=true&w=majority");
		this.mongoClient = new MongoClient(uri);
		this.database = mongoClient.getDatabase("waterFlowDB");
		this.collection = database.getCollection("waterFlowCollection");
	}

//	public void openConnection() {
//		try {
//			this.mongoClient = new MongoClient();
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//		this.database = mongoClient.getDB("local");
//		this.collection = database.getCollection("startup_log");
//	}
	
	public void closeConnection() {
		this.mongoClient.close();
	}
	
	public void store(WaterFlowSensorDomain domain) {
		Document dataCollected = new Document("flowRate", domain.getValue())
				.append("userID", domain.getUser())
				.append("deviceID", domain.getDeviceId())
				.append("description", domain.getDescription());
		this.collection.insertOne(dataCollected);
	}
}
