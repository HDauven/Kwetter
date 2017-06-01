package com.kwetter.dao.jpa;

import com.kwetter.dao.TweetDao;
import com.kwetter.model.Hashtag;
import com.kwetter.model.Person;
import com.kwetter.model.Tweet;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Created by hein on 5/18/17.
 */
@Stateless @JPA
public class TweetDaoJPA extends GenericDaoJPA<Tweet> implements TweetDao {
    @Override
    public List<Tweet> createTimeline(Person user) {
        try {
            return em.createQuery("SELECT t FROM Tweet t WHERE t.tweeter = :user " +
                    "OR t.tweeter IN (SELECT p FROM Person p WHERE p.followers = :user)" +
                    "ORDER BY t.createdAt DESC", Tweet.class)
                    .setParameter("user", user)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Tweet> findMentions(Person user) {
        try {
            return em.createQuery("SELECT t FROM Tweet t WHERE t.mentioned = :user", Tweet.class)
                    .setParameter("user", user)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Tweet> getLatestTweets(Person user) {
        return null;
    }

    @Override
    public List<Tweet> searchTweets(String hashtag) {
        return null;
    }

    @Override
    public List<Tweet> getByUser(Long userId) {
        try {
            return em.createQuery("SELECT t FROM Tweet t WHERE t.tweeter.id = :userId", Tweet.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch(NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean checkIfUserLikedTweet(Tweet tweet, Person user) {
        try {
            long count = (long) em.createQuery("SELECT count(t.id) FROM Tweet t WHERE t.id = :tweetId AND t.likers IN(:user)")
                    .setParameter("tweetId", tweet.getId())
                    .setParameter("user", user)
                    .getSingleResult();
            if (count == 1) {
                return true;
            } else {
                return false;
            }
        } catch(NoResultException e) {
            return false;
        }
    }

    @Override
    public Long countTweetsBetween(Hashtag tag, Date beginDate, Date endDate) {
        try {
            return (Long) em.createQuery("SELECT count(t.id) FROM Tweet t " +
                    "WHERE t.hashtags IN(:tag) AND t.createdAt BETWEEN :beginDate AND :endDate")
                    .setParameter("tag", tag)
                    .setParameter("beginDate", beginDate, TemporalType.DATE)
                    .setParameter("endDate", endDate, TemporalType.DATE)
                    .getSingleResult();
        } catch(NoResultException e) {
            return 0L;
        }
    }
}
