package com.mongoUtil;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import baselibrary.Baselibrary;

import org.bson.Document;

public class TestDataDAO extends Baselibrary {

    private final MongoCollection<TestingUrl> urlCollection;

    public TestDataDAO() {
        this.urlCollection = MongoClientConnection.getCollection("Site_url", TestingUrl.class);
    }

    // Method to get a URL by site name
    public TestingUrl getUrlBySiteName(String siteName) {
        return urlCollection.find(Filters.eq("site_name", siteName)).first();
    }

    // Method to add a new URL
    public void addUrl(TestingUrl testingUrl) {
        urlCollection.insertOne(testingUrl);
    }

    // Method to update an existing URL (e.g., by site name)
    public void updateUrlBySiteName(String siteName, String newUrl) {
        Document update = new Document("$set", new Document("url", newUrl));
        urlCollection.updateOne(Filters.eq("siteName", siteName), update);
    }

    // Method to delete a URL by site name
    public void deleteUrlBySiteName(String siteName) {
        urlCollection.deleteOne(Filters.eq("siteName", siteName));
    }

    // You can add more methods for other collections (user_data, etc.)
    // and for more complex queries or operations.

    public static void main(String[] args) {
        MongoClientConnection.getMongoClient(); // Initialize connection

        TestDataDAO dao = new TestDataDAO();

        // Example usage:
        TestingUrl bikewale = dao.getUrlBySiteName("Bikewale");
        if (bikewale != null) {
            System.out.println("Bikewale URL from DB: " + bikewale.getUrl());
        } else {
            System.out.println("Bikewale URL not found in DB.");
        }

        // Example of adding a new URL
        // TestingUrl newSite = new TestingUrl("NewSite", "https://www.newsite.com/", "A new test site", new java.util.Date());
        // dao.addUrl(newSite);
        // System.out.println("Added: " + newSite);

        // Example of updating a URL
        // dao.updateUrlBySiteName("Bikewale", "https://www.newbikewale.com/");
        // System.out.println("Updated Bikewale URL.");

        // Example of deleting a URL
        // dao.deleteUrlBySiteName("NewSite");
        // System.out.println("Deleted NewSite URL.");

        MongoClientConnection.closeMongoClient(); // Close connection
    }
    
}
