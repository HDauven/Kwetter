package com.kwetter.soap;

import java.util.Date;

/**
 * Created by hein on 5/25/17.
 */
public class SimpleTweet {
    private String message;
    private String username;
    private Date createdAt;

    public SimpleTweet() {
    }

    public SimpleTweet(String message, String username) {
        this.message = message;
        this.username = username;
    }

    public SimpleTweet(String message, String username, Date createdAt) {
        this.message = message;
        this.username = username;
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "SimpleTweet{" +
                "message='" + message + '\'' +
                ", username='" + username + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
