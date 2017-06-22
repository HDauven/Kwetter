package com.kwetter.model.mapper;

import com.kwetter.model.Tweet;
import com.kwetter.rest.dto.TweetDto;

/**
 * Created by hein on 5/19/17.
 */
public class TweetMapper extends GenericMapper<Tweet, TweetDto> {
    @Override
    public TweetDto to(Tweet tweet) {
        TweetDto tweetDto = new TweetDto();
        tweetDto.setId(tweet.getId());
        tweetDto.setDescription(tweet.getDescription());
        tweetDto.setTweeter(tweet.getTweeter().getUsername());
        tweetDto.setTweeterId(tweet.getTweeter().getId());
        if (tweet.getRepliedTo() == null) {
            tweetDto.setRepliedTo(0L);
        } else {
            tweetDto.setRepliedTo(tweet.getRepliedTo().getId());
        }
        tweetDto.setLikes(tweet.getLikesCount());
        tweetDto.setReplies(tweet.getRepliesCount());
        tweetDto.setCreatedAt(tweet.getCreatedAt());
        return tweetDto;
    }
}
