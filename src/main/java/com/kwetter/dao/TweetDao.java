package com.kwetter.dao;

import com.kwetter.model.Hashtag;
import com.kwetter.model.Person;
import com.kwetter.model.Tweet;

import java.util.Date;
import java.util.List;

/**
 * Created by hein on 5/18/17.
 */
public interface TweetDao extends GenericDao<Tweet> {
    List<Tweet> createTimeline(Person user);

    List<Tweet> findMentions(Person user);

    List<Tweet> getLatestTweets(Person user);

    List<Tweet> searchTweets(String hashtag);

    List<Tweet> getByUser(Long userId);

    boolean checkIfUserLikedTweet(Tweet tweet, Person user);

    Long countTweetsBetween(Hashtag tag, Date beginDate, Date endDate);
}
