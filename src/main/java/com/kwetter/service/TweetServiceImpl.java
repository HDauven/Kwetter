package com.kwetter.service;

import com.kwetter.JMS.TweetMessagePublisher;
import com.kwetter.dao.HashtagDao;
import com.kwetter.dao.PersonDao;
import com.kwetter.dao.TweetDao;
import com.kwetter.dao.jpa.JPA;
import com.kwetter.interceptor.TweetLanguageInterceptor;
import com.kwetter.model.Hashtag;
import com.kwetter.model.Person;
import com.kwetter.model.Tweet;
import com.kwetter.model.comparator.TweetComparator;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by hein on 5/18/17.
 */
@Stateless
public class TweetServiceImpl implements TweetService {

    @Inject @JPA
    TweetDao tweetDao;

    @Inject @JPA
    PersonDao personDao;

    @Inject @JPA
    HashtagDao hashtagDao;

    @Inject
    private Event<Tweet> tweetAddedEvent;

    @Override
    @Interceptors(TweetLanguageInterceptor.class)
    public void sendTweet(Tweet tweet) {
        tweetDao.create(tweet);
        Matcher usernames = Pattern.compile("(?<=@)([\\w-]+)").matcher(tweet.getDescription());
        while (usernames.find()) {
            Person mentioned = personDao.findByUsername(usernames.group());
            if (mentioned != null) {
                tweet.addMention(mentioned);
            }
        }
        Matcher hashtags = Pattern.compile("(?<=#)([\\w-]+)").matcher(tweet.getDescription());
        while (hashtags.find()) {
            Hashtag hashtag = hashtagDao.findByTag(hashtags.group());
            if (hashtag == null) {
                hashtag = new Hashtag(hashtags.group());
                hashtagDao.create(hashtag);
            }
            tweet.addHashtag(hashtag);
        }
        tweetDao.update(tweet);
        tweetAddedEvent.fire(tweet);
    }

    @Override
    public boolean checkIfUserLikedTweet(Tweet tweet, Person user) {
        return tweetDao.checkIfUserLikedTweet(tweet, user);
    }

    @Override
    public void likeTweet(Long tweetId, Long userId) {
        Tweet tweet = tweetDao.findById(tweetId);
        Person user = personDao.findById(userId);
        if (tweet == null) {
            throw new NullPointerException("Tweet does not exist!");
        }
        if (user == null) {
            throw new NullPointerException("User does not exist!");
        }
        tweet.addLike(user);
    }

    @Override
    public void unlikeTweet(Long tweetId, Long userId) {
        Tweet tweet = tweetDao.findById(tweetId);
        Person user = personDao.findById(userId);
        if (tweet == null) {
            throw new NullPointerException("Tweet does not exist!");
        }
        if (user == null) {
            throw new NullPointerException("User does not exist!");
        }
        tweet.unlike(user);
    }

    @Override
    public List<Tweet> buildTimeline(Long userId) {
        Person user = personDao.findById(userId);
        if (user == null) {
            throw new NullPointerException("User does not exist!");
        }
        return TweetComparator.sortByCreationDate(tweetDao.createTimeline(user));
    }

    @Override
    public List<Tweet> latestTweets(Long userId, int numberOfTweets) {
        return null;
    }

    @Override
    public List<Tweet> searchTweets(String keyword) {
        return null;
    }

    @Override
    public List<Tweet> findMentions(Long userId) {
        Person user = personDao.findById(userId);
        if (user == null) {
            throw new NullPointerException("User does not exist!");
        }
        return TweetComparator.sortByCreationDate(tweetDao.findMentions(user));
    }

    @Override
    public List<Tweet> findTweetsByUser(Long userId) {
        return TweetComparator.sortByCreationDate(tweetDao.getByUser(userId));
    }

    @Override
    public Tweet getTweet(Long tweetId) {
        return tweetDao.findById(tweetId);
    }

    @Override
    public List<Tweet> getAllTweets() {
        List<Tweet> tweets = tweetDao.findAll();
        return TweetComparator.sortByCreationDate(tweets);
    }

    @Override
    public List<Tweet> getReplies(Long tweetId) {
        Tweet tweet = tweetDao.findById(tweetId);
        if (tweet == null) {
            throw new NullPointerException("This tweet does not exist!");
        }
        return TweetComparator.sortByCreationDate(tweet.getReplies());
    }

    @Override
    public List<Hashtag> getHashtags(Long tweetId) {
        Tweet tweet = tweetDao.findById(tweetId);
        if (tweet == null) {
            throw new NullPointerException("This tweet does not exist!");
        }
        return tweet.getHashtags();
    }

    @Override
    public List<Hashtag> getHashtags() {
        return hashtagDao.findAll();
    }

    @Override
    public Hashtag getHashtag(Long hashtagId) {
        return hashtagDao.findById(hashtagId);
    }

    @Override
    public List<Person> getLikers(Long tweetId) {
        Tweet tweet = tweetDao.findById(tweetId);
        if (tweet == null) {
            throw new NullPointerException("this tweet does not exist!");
        }
        return tweet.getLikers();
    }

    @Override
    public void removeTweet(Tweet tweet) {
        tweetDao.remove(tweet);
    }

    @Override
    public void updateTweet(Tweet tweet) {
        tweetDao.update(tweet);
    }

    @Override
    public LinkedHashMap<Hashtag, Long> getTrendingHashtags() {
        List<Hashtag> hashtags = hashtagDao.findAll();
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date oneWeekAgo = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
        Date now = new Date(System.currentTimeMillis() + DAY_IN_MS);
        Map<Hashtag, Long> hashtagTweetMap = new HashMap<>();
        for (Hashtag hashtag: hashtags) {
            Long occurrancesInOneWeek = tweetDao.countTweetsBetween(hashtag, oneWeekAgo, now);
            hashtagTweetMap.put(hashtag, occurrancesInOneWeek);
        }
        LinkedHashMap<Hashtag, Long> sorted = hashtagTweetMap.entrySet()
                .stream().sorted(Map.Entry.<Hashtag, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return sorted;
    }
}
