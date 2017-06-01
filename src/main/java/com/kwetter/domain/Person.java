package com.kwetter.model;

import com.kwetter.model.validation.ValidEmail;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by hein on 3/11/17.
 */
@Entity
public class Person implements Serializable {

    @Id
    @TableGenerator(name = "PERSON_GEN", table = "SEQUENCE_TABLE",
            pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
            pkColumnValue = "PERSON_SEQ")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PERSON_GEN")
    private Long id;

    @NotNull
    @Size(min=1, max=64)
    private String firstName;

    @NotNull
    @Size(min=1, max=64)
    private String lastName;

    @Column(unique = true)
    @NotNull
    @Size(min=1, max=32)
    private String username;

    @Column(unique = true)
    @ValidEmail
    private String email;

    @Column(unique = true)
    @Size(min=1, max=256)
    private String url;

    private boolean verified;

    @Size(min=1, max=140)
    private String description;

    @OneToMany(mappedBy = "tweeter", orphanRemoval = true)
    private List<Tweet> tweets;

    @ManyToMany(mappedBy = "following")
    private List<Person> followers;

    @ManyToMany
    @JoinTable(name = "follower_followed")
    private List<Person> following;

    @ManyToMany(mappedBy = "mentioned")
    private List<Tweet> mentions;

    @ManyToMany(mappedBy = "likers")
    private List<Tweet> liked;

    @Size(min=1, max=255)
    private String profileImageUrl = "https://assets.materialup.com/uploads/e72ef34c-ebe7-4a74-80b1-bc80eabd566c/avatar.png";

    @Column(nullable = false)
    @Size(min=1, max=255)
    private String password;

    @ManyToMany(mappedBy = "users")
    private List<Group> groups;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Person() {
        this.tweets = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.mentions = new ArrayList<>();
        this.liked = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.createdAt = new Date();
    }

    public Person(String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
        this.username = username.toLowerCase();
        this.email = email.toLowerCase();
        this.password = toSha256(password);
        this.tweets = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.mentions = new ArrayList<>();
        this.liked = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.createdAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = toSha256(password);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public List<Person> getFollowers() {
        return followers;
    }

    public void addFollowing(Person following) {
        this.following.add(following);
        following.getFollowers().add(this);
    }

    public List<Person> getFollowing() {
        return following;
    }

    public List<Tweet> getMentions() {
        return mentions;
    }

    public List<Tweet> getLikes() {
        return liked;
    }

    public List<Group> getGroups() { return groups; }

    public Long getTweetCount() {
        return (long) tweets.size();
    }

    public Long getFollowersCount() {
        return (long) followers.size();
    }

    public Long getFollowingCount() {
        return (long) following.size();
    }

    public Long getLikesCount() {
        return (long) liked.size();
    }

    public Long getMentionsCount() {
        return (long) mentions.size();
    }

    private String toSha256(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data.getBytes());
            return bytesToHex(md.digest());
        } catch (Exception ex) {
            return null;
        }

    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}