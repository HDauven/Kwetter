package com.kwetter.batch;

import com.kwetter.model.Person;
import com.kwetter.model.Tweet;
import com.kwetter.service.TweetService;
import com.kwetter.service.UserService;

import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

/**
 * Created by hein on 5/25/17.
 */
@Dependent
@Named
public class TweetProcessor implements ItemProcessor {

    @Inject
    UserService userService;

    @Inject
    TweetService tweetService;

    @Override
    public Object processItem(Object tweetString) throws Exception {
        JsonObject tweetJson;
        System.out.println("Process incoming JSON: " + tweetString);
        try (JsonReader reader = Json.createReader(new StringReader((String) tweetString))) {
            tweetJson = reader.readObject();
            reader.close();
        }
        Person user = userService.getUser(tweetJson.getString("user"));
        if (user == null) {
            System.out.println("User is null!" + tweetJson.getString("user"));
            return null;
        }
        Tweet tweet = new Tweet();
        String text = tweetJson.getString("text");
        if (tweetJson.containsKey("repliedTo")) {
            Tweet parent = tweetService.getTweet((long) tweetJson.getInt("repliedTo"));
            if (parent != null) {
                tweet.setRepliedTo(parent);
            }
        }
        tweet.setTweeter(user);
        tweet.setDescription(text);
        System.out.println("Tweet is succesful: " + tweet.getDescription());
        return tweet;
    }
}
