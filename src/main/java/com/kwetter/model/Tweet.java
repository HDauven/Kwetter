package com.kwetter.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * Created by hein on 4/3/17.
 */
@Entity
public class Tweet implements Serializable {

    @Id
    @TableGenerator(name = "TWEET_GEN", table = "SEQUENCE_TABLE",
            pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
            pkColumnValue = "TWEET_SEQ")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TWEET_GEN")
    private Long id;

    @NotNull
    @Size(min=1, max=140)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne
    private Person tweeter;

    @ManyToOne
    private Tweet repliedTo;

    @OneToMany(mappedBy = "repliedTo", orphanRemoval = true)
    private List<Tweet> replies;

    @ManyToMany
    private List<Hashtag> hashtags;

    @ManyToMany
    private List<Person> mentioned;

    @ManyToMany
    @JoinTable(name = "liked_liker")
    private List<Person> likers;

    public Tweet() {
        this.replies = new ArrayList<>();
        this.hashtags = new ArrayList<>();
        this.mentioned = new ArrayList<>();
        this.likers = new ArrayList<>();
        this.createdAt = new Date();
    }

    public Tweet(String description, Person tweeter) {
        this.description = description;
        this.tweeter = tweeter;
        this.replies = new ArrayList<>();
        this.hashtags = new ArrayList<>();
        this.mentioned = new ArrayList<>();
        this.likers = new ArrayList<>();
        this.createdAt = new Date();
    }

    public Tweet(String description, Person tweeter, Tweet replyTo) {
        this.description = description;
        this.tweeter = tweeter;
        this.repliedTo = replyTo;
        this.replies = new ArrayList<>();
        this.hashtags = new ArrayList<>();
        this.mentioned = new ArrayList<>();
        this.likers = new ArrayList<>();
        this.createdAt = new Date();
    }

    public Tweet(String description, Date createdAt, Person tweeter) {
        this.description = description;
        this.createdAt = createdAt;
        this.tweeter = tweeter;
        this.replies = new ArrayList<>();
        this.hashtags = new ArrayList<>();
        this.mentioned = new ArrayList<>();
        this.likers = new ArrayList<>();
        this.createdAt = new Date();
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Person getTweeter() {
        return tweeter;
    }

    public void setTweeter(Person tweeter) {
        this.tweeter = tweeter;
    }

    public Tweet getRepliedTo() {
        return repliedTo;
    }

    public void setRepliedTo(Tweet repliedTo) {
        this.repliedTo = repliedTo;
    }

    public List<Tweet> getReplies() {
        return replies;
    }

    public void setReplies(List<Tweet> replies) {
        this.replies = replies;
    }

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public void addHashtag(Hashtag hashtag) {
        this.hashtags.add(hashtag);
        hashtag.getTweets().add(this);
    }

    public List<Person> getMentioned() {
        return mentioned;
    }

    public void setMentioned(List<Person> mentioned) {
        this.mentioned = mentioned;
    }

    public void addMention(Person person) {
        this.mentioned.add(person);
        person.getMentions().add(this);
    }

    public List<Person> getLikers() {
        return likers;
    }

    public void addLike(Person person) {
        this.likers.add(person);
        person.getLikes().add(this);
    }

    public void unlike(Person person) {
        this.likers.remove(person);
        person.getLikes().remove(this);
    }

    public Long getLikesCount() {
        return (long) likers.size();
    }

    public Long getRepliesCount() {
        return (long) replies.size();
    }
}
