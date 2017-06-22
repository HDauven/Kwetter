package com.kwetter.rest.dto;

import java.util.Date;

/**
 * Created by hein on 5/19/17.
 */
public class TweetDto {
    public Long id;
    public String description;
    public String tweeter;
    public Long tweeterId;
    public Long repliedTo;
    public Long likes;
    public Long replies;
    public Date createdAt;

    public TweetDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTweeter() {
        return tweeter;
    }

    public void setTweeter(String tweeter) {
        this.tweeter = tweeter;
    }

    public Long getTweeterId() {
        return tweeterId;
    }

    public void setTweeterId(Long tweeterId) {
        this.tweeterId = tweeterId;
    }

    public Long getRepliedTo() {
        return repliedTo;
    }

    public void setRepliedTo(Long repliedTo) {
        this.repliedTo = repliedTo;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getReplies() {
        return replies;
    }

    public void setReplies(Long replies) {
        this.replies = replies;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
