package com.kwetter.JMS;

import com.kwetter.dao.PersonDao;
import com.kwetter.model.Person;
import com.kwetter.model.Tweet;
import com.kwetter.service.TweetService;
import com.kwetter.service.UserService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

/**
 * Created by hein on 5/24/17.
 */
@MessageDriven(name= "tweetmdb", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/tweetq"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "tweetq"),
        @ActivationConfigProperty(propertyName = "resourceAdapter", propertyValue = "activemq-rar-5.14.5"),
})
public class TweetMessageBean implements MessageListener {

    @Inject
    private UserService userService;

    @Inject
    private TweetService tweetService;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            Tweet tweet = messageParser(message);
            if (tweet != null) {
                tweetService.sendTweet(tweet);
            }
        }
    }

    private Tweet messageParser(Message message) {
        String messageBody = null;
        String tweetMessage;
        Person tweeter;
        Tweet parent;
        try {
            messageBody = ((TextMessage) message).getText();
            System.out.println("A new tweet arrived: " + messageBody);
        } catch (JMSException e) {
            System.out.println("A new tweet arrived, but something went wrong: " + message);
            e.printStackTrace();
        }
        try (JsonReader reader = Json.createReader(new StringReader(messageBody))) {
            JsonObject jsonMessage = reader.readObject();
            if (jsonMessage.containsKey("id")) {
                return null;
            }
            tweeter = userService.getUser(jsonMessage.getString("username"));
            tweetMessage = jsonMessage.getString("message");
            if (jsonMessage.containsKey("repliedTo")) {
                parent = tweetService.getTweet((long) jsonMessage.getInt("repliedTo"));
                if (parent != null) {
                    return new Tweet(tweetMessage, tweeter, parent);
                }
            }
        }
        return new Tweet(tweetMessage, tweeter);
    }
}
