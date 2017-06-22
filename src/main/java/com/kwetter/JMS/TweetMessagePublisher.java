package com.kwetter.JMS;

import com.kwetter.model.Hashtag;
import com.kwetter.model.Tweet;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.annotation.Resource;
import javax.enterprise.event.Observes;
import javax.jms.*;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import java.util.List;

/**
 * Created by hein on 5/26/17.
 */
public class TweetMessagePublisher {

    public TweetMessagePublisher() {
    }

    public void sendTweet(@Observes Tweet tweet) {
        Connection connection = null;
        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = factory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("tweetTopic");
            MessageProducer producer = session.createProducer(topic);
            List<Hashtag> hashtags = tweet.getHashtags();
            for (Hashtag hashtag: hashtags) {
                if (hashtag.getTag().equalsIgnoreCase("sports")) {
                    TextMessage message = session.createTextMessage(tweetToJson(tweet));
                    message.setStringProperty("typeMessage", "sports");
                    producer.send(message);
                }
                if (hashtag.getTag().equalsIgnoreCase("politics")) {
                    TextMessage message = session.createTextMessage(tweetToJson(tweet));
                    message.setStringProperty("typeMessage", "politics");
                    producer.send(message);
                }
                if (hashtag.getTag().equalsIgnoreCase("computers")) {
                    TextMessage message = session.createTextMessage(tweetToJson(tweet));
                    message.setStringProperty("typeMessage", "computers");
                    producer.send(message);
                }
                if (hashtag.getTag().equalsIgnoreCase("games")) {
                    TextMessage message = session.createTextMessage(tweetToJson(tweet));
                    message.setStringProperty("typeMessage", "games");
                    producer.send(message);
                }
                if (hashtag.getTag() != null) {
                    TextMessage message = session.createTextMessage(tweetToJson(tweet));
                    message.setStringProperty("typeMessage", hashtag.getTag());
                    System.out.println(message.getStringProperty("typeMessage"));
                    System.out.println(message);
                    producer.send(message);
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String tweetToJson(Tweet tweet) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("id", tweet.getId())
                .add("tweeter", tweet.getTweeter().getUsername())
                .add("message", tweet.getDescription())
                .add("createdOn", tweet.getCreatedAt().toString())
                .build();
        return addMessage.toString();
    }
}
