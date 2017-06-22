package com.kwetter.JSF;

import com.kwetter.model.Person;
import com.kwetter.model.Tweet;
import com.kwetter.service.TweetService;
import com.kwetter.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by hein on 5/29/17.
 */
@Named
@RequestScoped
public class AdminTweetController {

    @Inject
    private TweetService tweetService;
    @Inject
    private UserService userService;
    private List<Tweet> tweets;
    private Long tweetId;
    private String message;
    private String username;
    private Long parentId;
    private Tweet tweet;

    @PostConstruct
    public void init() {
        tweets = tweetService.getAllTweets();
    }

    public String doCreateTweet() {
        Person user = userService.getUser(username);
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

        return "tweet.xhtml";
    }

    public void doFindTweetById() {
        tweet = tweetService.getTweet(tweetId);
    }

    public void doRemoveTweet(Tweet tweet) {
        tweetService.removeTweet(tweet);
    }

    public List<Tweet> getTweets() {
        return tweetService.getAllTweets();
    }

    public TweetService getTweetService() {
        return tweetService;
    }

    public void setTweetService(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }
}
