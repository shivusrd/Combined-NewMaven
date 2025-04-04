package com.mongoUtil;

import org.bson.types.ObjectId;

import baselibrary.Baselibrary;

public class TestingUrl extends Baselibrary
{
    private ObjectId id; // Maps to _id in MongoDB
    private String siteName;
    private String url;
    private String description;
    private java.util.Date createdAt;

    // Default constructor (required for some codecs)
    public TestingUrl() {}

    // Constructor with fields
    public TestingUrl(String siteName, String url, String description, java.util.Date createdAt) {
        this.siteName = siteName;
        this.url = url;
        this.description = description;
        this.createdAt = createdAt;
    }

    // Getters and setters for all fields
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public String getSiteName() { return siteName; }
    public void setSiteName(String siteName) { this.siteName = siteName; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public java.util.Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.util.Date createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "TestingUrl{" +
               "id=" + id +
               ", siteName='" + siteName + '\'' +
               ", url='" + url + '\'' +
               ", description='" + description + '\'' +
               ", createdAt=" + createdAt +
               '}';
    }
}
