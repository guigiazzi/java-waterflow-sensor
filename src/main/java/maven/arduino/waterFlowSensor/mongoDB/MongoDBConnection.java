package maven.arduino.waterFlowSensor.mongoDB;

import java.net.UnknownHostException;

import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@Repository
public class MongoDBConnection {
	
	private MongoClient mongoClient;
	
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
