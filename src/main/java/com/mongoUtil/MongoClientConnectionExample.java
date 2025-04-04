package com.mongoUtil;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import baselibrary.Baselibrary;

public class MongoClientConnectionExample extends Baselibrary {
	
	

//cluster - combined maven - server
//database - sample_mflix- database
//collections-movies,sessions- tables
//document - Single unit of data (BSON object) -row
//field - column - Key-value pair within a document
	
	public static void main(String[] args) {
        String connectionString = "mongodb+srv://dubeyshivam890:7jUnAeHYmAIHHLm7@combinedmaven.s5d398a.mongodb.net/?retryWrites=true&w=majority&appName=CombinedMaven";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("admin");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }

}
