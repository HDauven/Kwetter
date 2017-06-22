package com.kwetter.websocket;

import com.kwetter.model.Person;
import com.kwetter.model.Tweet;
import com.kwetter.model.comparator.TweetComparator;
import com.kwetter.service.TweetService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hein on 5/26/17.
 */
@ApplicationScoped
public class TweetSessionHandler {

    @Inject
    TweetService tweetService;

    private static Set<Session> sessions = new HashSet<>();
    private static Set<Tweet> tweets = new HashSet<>();

    public TweetSessionHandler() {
    }

    public void addSession(Session session) {
        sessions.add(session);
        for (Tweet tweet: tweets) {
            JsonObject addMessage = createAddMessage(tweet);
            sendToSession(session, addMessage);
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public List<Tweet> getTweets() {
        return TweetComparator.sortByCreationDate(new ArrayList<>(tweets));
    }

    public void addTweet(@Observes Tweet tweet) {
        tweets.add(tweet);
        JsonObject addMessage = createAddMessage(tweet);
        sendToAllConnectedSessions(addMessage);
    }

    private JsonObject createAddMessage(Tweet tweet) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", tweet.getId())
                .add("tweeter", tweet.getTweeter().getUsername())
                .add("message", tweet.getDescription())
                .add("createdOn", tweet.getCreatedAt().toString())
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session: sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException e) {
            sessions.remove(session);
            System.out.println("Disconnected session: " + session.getId());
        }
    }
}
