package maven.arduino.waterFlowSensor.mongoDB;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import maven.arduino.waterFlowSensor.domain.WaterFlowSensorDomain;

public class MongoDBConnection {
	
	private MongoClient mongoClient;
	
	private MongoDatabase database;
	
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

	public MongoCollection<Document> getCollection() {
		return collection;
	}

	public void setCollection(MongoCollection<Document> collection) {
		this.collection = collection;
	}	
	
	public void openConnection() {
		MongoClientURI uri = new MongoClientURI("mongodb://admin:admin@cluster0-shard-00-00-qkxa6.mongodb.net:27017,cluster0-shard-00-01-qkxa6.mongodb.net:27017,cluster0-shard-00-02-qkxa6.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority");
		this.mongoClient = new MongoClient(uri);
		this.database = mongoClient.getDatabase("waterFlowDB");
		this.collection = database.getCollection("waterFlowCollection");
	}
	
	public void closeConnection() {
		this.mongoClient.close();
	}
	
	public void store(WaterFlowSensorDomain domain) {
		Document dataCollected = new Document("flowRate", domain.getFlowRate())
				.append("userID", domain.getUser())
				.append("deviceID", domain.getDeviceId())
				.append("description", domain.getDescription())
				.append("timestamp", domain.getTimestamp());
		this.collection.insertOne(dataCollected);
		//this.collection.deleteMany(new Document()); para deletar todos os documentos da collection
	}
}
