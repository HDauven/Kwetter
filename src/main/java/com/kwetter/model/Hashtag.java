package com.kwetter.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hein on 4/3/17.
 */
@Entity
public class Hashtag implements Serializable {

    @Id
    @TableGenerator(name = "HASHTAG_GEN", table = "SEQUENCE_TABLE",
            pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
            pkColumnValue = "HASHTAG_SEQ")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "HASHTAG_GEN")
    private Long id;

    @NotNull
    private String tag;

    @ManyToMany(mappedBy = "hashtags")
    private List<Tweet> tweets;

    public Hashtag() {
        this.tweets = new ArrayList<>();
    }

    public Hashtag(String hashtag) {
        this.tweets = new ArrayList<>();
        this.tag = hashtag.toLowerCase();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public Long getOccurrences() {
        return (long) tweets.size();
    }
}
