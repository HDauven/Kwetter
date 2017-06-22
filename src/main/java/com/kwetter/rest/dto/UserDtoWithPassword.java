package com.kwetter.rest.dto;

import java.util.Date;

/**
 * Created by hein on 5/30/17.
 */
public class UserDtoWithPassword {private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean verified;
    private String description;
    private Long tweets;
    private Long followers;
    private Long following;
    private Long likes;
    private Long mentions;
    private String profileImageUrl;
    private Date createdAt;

    public UserDtoWithPassword() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTweets() {
        return tweets;
    }

    public void setTweets(Long tweets) {
        if (tweets == null) {
            this.tweets = 0L;
        } else {
            this.tweets = tweets;
        }
    }

    public Long getFollowers() {
        return followers;
    }

    public void setFollowers(Long followers) {
        if (followers == null) {
            this.followers = 0L;
        } else {
            this.followers = followers;
        }
    }

    public Long getFollowing() {
        return following;
    }

    public void setFollowing(Long following) {
        if (following == null) {
            this.following = 0L;
        } else {
            this.following = following;
        }
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        if (likes == null) {
            this.likes = 0L;
        } else {
            this.likes = likes;
        }
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setMentions(Long mentions) {
        if (mentions == null) {
            this.mentions = 0L;
        } else {
            this.mentions = mentions;
        }
    }

    public Long getMentions() {
        return mentions;
    }
}