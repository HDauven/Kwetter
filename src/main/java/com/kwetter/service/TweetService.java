package com.kwetter.service;

import com.kwetter.model.Hashtag;
import com.kwetter.model.Person;
import com.kwetter.model.Tweet;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hein on 5/18/17.
 */
public interface TweetService {

    void sendTweet(Tweet tweet);

    boolean checkIfUserLikedTweet(Tweet tweet, Person user);

    void likeTweet(Long tweetId, Long userId);

    void unlikeTweet(Long tweetId, Long userId);

    List<Tweet> buildTimeline(Long userId);

    List<Tweet> latestTweets(Long userId, int numberOfTweets);

    List<Tweet> searchTweets(String keyword);

    List<Tweet> findMentions(Long userId);

    List<Tweet> findTweetsByUser(Long userId);

    Tweet getTweet(Long tweetId);

    List<Tweet> getAllTweets();

    List<Tweet> getReplies(Long tweetId) throws NullPointerException;

    List<Hashtag> getHashtags(Long tweetId);

    List<Hashtag> getHashtags();

    Hashtag getHashtag(Long hashtagId);

    List<Person> getLikers(Long tweetId);

    void removeTweet(Tweet tweet);

    void updateTweet(Tweet tweet);

    LinkedHashMap<Hashtag, Long> getTrendingHashtags();
}
