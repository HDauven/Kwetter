package com.kwetter.batch;

import com.kwetter.model.Tweet;
import com.kwetter.service.TweetService;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by hein on 5/25/17.
 */
@Dependent
@Named
public class TweetWriter extends AbstractItemWriter {

    @Inject
    TweetService tweetService;
    @Override
    public void writeItems(List<Object> items) throws Exception {
        for (Object tweet : items) {
            if (tweet != null) {
                System.out.println("Tweet to send: " + ((Tweet) tweet).getDescription());
                tweetService.sendTweet((Tweet) tweet);
            }
        }
    }
}
