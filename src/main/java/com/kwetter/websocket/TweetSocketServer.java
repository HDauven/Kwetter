package com.kwetter.websocket;

import com.kwetter.model.Person;
import com.kwetter.model.Tweet;
import com.kwetter.service.TweetService;
import com.kwetter.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.StringReader;

/**
 * Created by hein on 5/26/17.
 */
@ApplicationScoped
@ServerEndpoint(value = "/websocket/tweet")
public class TweetSocketServer {

    @Inject
    private TweetSessionHandler sessionHandler;

    @Inject
    private UserService userService;

    @Inject
    private TweetService tweetService;

    @OnOpen
    public void open(Session session) {
        sessionHandler.addSession(session);
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            if (jsonMessage.getString("action").equals("add")) {
                String description = jsonMessage.getString("message");
                Person user = userService.getUser(jsonMessage.getString("username"));
                Tweet tweet = new Tweet();
                if (jsonMessage.containsKey("repliedTo")) {
                    Tweet parent = tweetService.getTweet((long) jsonMessage.getInt("repliedTo"));
                    if (parent != null) {
                        tweet.setRepliedTo(parent);
                    }
                }
                tweet.setDescription(description);
                tweet.setTweeter(user);
                tweetService.sendTweet(tweet);
            }
        }
    }
}
