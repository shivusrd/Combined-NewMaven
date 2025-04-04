package com.mongoUtil;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.Document;

public class MongoClientConnection {
	
	

	    private static MongoClient mongoClient;
	    private static final String CONNECTION_STRING = "mongodb+srv://dubeyshivam890:7jUnAeHYmAIHHLm7@combinedmaven.s5d398a.mongodb.net/?retryWrites=true&w=majority&appName=CombinedMaven";
	    private static final String DATABASE_NAME = "testing_db"; // Use your desired database name

	    public static MongoClient getMongoClient() {
	        if (mongoClient == null) {
	            CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
	            CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

	            MongoClientSettings settings = MongoClientSettings.builder()
	                    .applyConnectionString(new com.mongodb.ConnectionString(CONNECTION_STRING))
	                    .codecRegistry(codecRegistry)
	                    .build();
	            mongoClient = MongoClients.create(settings);
	        }
	        return mongoClient;
	    }

	    public static MongoDatabase getDatabase() {
	        return getMongoClient().getDatabase(DATABASE_NAME);
	    }

	    public static <T> com.mongodb.client.MongoCollection<T> getCollection(String collectionName, Class<T> clazz) {
	        return getDatabase().getCollection(collectionName, clazz);
	    }

	    public static void closeMongoClient() {
	        if (mongoClient != null) {
	            mongoClient.close();
	        }
	    }

	    public static void pingDatabase() {
	        try (MongoClient client = getMongoClient()) {
	            MongoDatabase database = client.getDatabase("admin");
	            database.runCommand(new Document("ping", 1));
	            System.out.println("Successfully pinged the MongoDB deployment!");
	        } catch (MongoException e) {
	            System.err.println("Failed to ping the MongoDB deployment: " + e.getMessage());
	        }
	    }

	    public static void main(String[] args) {
	        pingDatabase(); // Example of pinging the database
	        closeMongoClient();
	    }

}
