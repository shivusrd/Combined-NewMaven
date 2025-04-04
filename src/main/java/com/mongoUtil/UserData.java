package com.mongoUtil;

import org.bson.types.ObjectId;

import baselibrary.Baselibrary;

public class UserData extends Baselibrary {
    private ObjectId id; // Maps to _id in MongoDB
    private String userId;
    private String username;
    private String password;

    public UserData() {}

    public UserData(String userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "UserData{" +
               "id=" + id +
               ", userId='" + userId + '\'' +
               ", username='" + username + '\'' +
               ", password='********'" + // Masking password for display
               '}';
    }
    
}