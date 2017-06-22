package com.kwetter.soap;

import com.kwetter.model.Person;
import com.kwetter.model.Tweet;
import com.kwetter.service.TweetService;
import com.kwetter.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by hein on 5/25/17.
 */
@WebService(serviceName = "GoPlusService")
public class GoPlusServiceImpl implements GoPlusService {

    @Inject
    TweetService tweetService;

    @Inject
    UserService userService;

    @Override
    @WebMethod
    public void createTweet(SimpleTweet tweet) {
        Person user = userService.getUser(tweet.getUsername());
        if (user != null) {
            Tweet newTweet = new Tweet(tweet.getMessage(), user);
            tweetService.sendTweet(newTweet);
        }
    }

    @Override
    @WebMethod
    public List<SimpleTweet> getTimeline(String username) {
        Person user = userService.getUser(username);
        List<Tweet> tweets = new ArrayList<>();
        List<SimpleTweet> simpleTweets = new ArrayList<>();
        if (user != null) {
            tweets = tweetService.buildTimeline(user.getId());
            for (Tweet tweet: tweets) {
                simpleTweets.add(new SimpleTweet(tweet.getDescription(), tweet.getTweeter().getUsername(), tweet.getCreatedAt()));
            }
        }
        return simpleTweets;
    }
}
