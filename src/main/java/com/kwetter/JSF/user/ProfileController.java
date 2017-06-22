package com.kwetter.JSF.user;

import com.kwetter.JSF.AuthBean;
import com.kwetter.model.Hashtag;
import com.kwetter.model.Person;
import com.kwetter.model.Tweet;
import com.kwetter.service.TweetService;
import com.kwetter.service.UserService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.*;

/**
 * Created by hein on 5/22/17.
 */
@ManagedBean
@ViewScoped
public class ProfileController {

    @Inject
    private UserService userService;
    @Inject
    private AuthBean authBean;
    @Inject
    private TweetService tweetService;
    private String username;
    private Person user;
    private Person observingProfile;
    private List<Tweet> timeline;
    private List<Tweet> mentions;
    private List<Tweet> observingTweets;
    private List<Person> observingFollowing;
    private List<Person> observingFollowers;
    private List<Tweet> observingLikes;
    private LinkedHashMap<Hashtag, Long> trending;
    private List<Hashtag> trendingHashtags;
    private Tweet tweet;
    private String message;
    private Long parentId;

    @PostConstruct
    public void init() {
        user = userService.getUser(authBean.getPrincipalName());
        timeline = tweetService.buildTimeline(user.getId());
        mentions = tweetService.findMentions(user.getId());
        trending = tweetService.getTrendingHashtags();
        trendingHashtags = new ArrayList<>(trending.keySet());
        if (observingProfile == null && username != null) {
            observingProfile = userService.getUser(username);
        }
        if (observingProfile != null) {
            observingTweets = tweetService.findTweetsByUser(observingProfile.getId());
            observingFollowing = userService.getFollowing(observingProfile.getId());
            observingFollowers = userService.getFollowers(observingProfile.getId());
            observingLikes = userService.getLikes(observingProfile.getId());
        }
    }

    public String doCreateTweet() {
        Tweet parent = tweetService.getTweet(parentId);

        if (parent != null) {
            tweet = new Tweet(message, user, parent);
            tweetService.sendTweet(tweet);
        } else {
            tweet = new Tweet(message, user);
            tweetService.sendTweet(tweet);
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Tweet created",
                        "The tweet " + tweet.getDescription() + " has been created with id=" + tweet.getId()));

        return null;
    }

    public String doLikeTweet(Tweet tweet) {
        tweetService.likeTweet(tweet.getId(), user.getId());
        return null;
    }

    public String doUnlikeTweet(Tweet tweet) {
        tweetService.unlikeTweet(tweet.getId(), user.getId());
        return null;
    }

    public boolean doCheckIfUserLikedTweet(Tweet tweet) {
        return tweetService.checkIfUserLikedTweet(tweet, user);
    }

    public void doFindUserByUsername() {
        if  (username == null || username.isEmpty()) {
            username = authBean.getPrincipalName();
        }
        System.out.println("The found username: " + username);
        observingProfile = userService.getUser(username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public Person getObservingProfile() {
        return observingProfile;
    }

    public void setObservingProfile(Person observingProfile) {
        this.observingProfile = observingProfile;
    }

    public List<Tweet> getTimeline() {
        return tweetService.buildTimeline(user.getId());
    }

    public void setTimeline(List<Tweet> timeline) {
        this.timeline = timeline;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Tweet> getMentions() {
        return tweetService.findMentions(user.getId());
    }

    public void setMentions(List<Tweet> mentions) {
        this.mentions = mentions;
    }

    public List<Tweet> getObservingTweets() {
        return tweetService.findTweetsByUser(observingProfile.getId());
    }

    public void setObservingTweets(List<Tweet> observingTweets) {
        this.observingTweets = observingTweets;
    }

    public List<Person> getObservingFollowing() {
        return userService.getFollowing(observingProfile.getId());
    }

    public void setObservingFollowing(List<Person> observingFollowing) {
        this.observingFollowing = observingFollowing;
    }

    public List<Person> getObservingFollowers() {
        return userService.getFollowers(observingProfile.getId());
    }

    public void setObservingFollowers(List<Person> observingFollowers) {
        this.observingFollowers = observingFollowers;
    }

    public List<Tweet> getObservingLikes() {
        return userService.getLikes(observingProfile.getId());
    }

    public void setObservingLikes(List<Tweet> observingLikes) {
        this.observingLikes = observingLikes;
    }

    public LinkedHashMap<Hashtag, Long> getTrending() {
        return trending;
    }

    public List<Hashtag> getTrendingHashtags() {
        trending = tweetService.getTrendingHashtags();
        trendingHashtags = new ArrayList<>(trending.keySet());
        return trendingHashtags;
    }
}
