package com.kwetter.rest;

import com.kwetter.exception.DataNotFoundException;
import com.kwetter.exception.UserUnauthorizedException;
import com.kwetter.model.Hashtag;
import com.kwetter.model.Person;
import com.kwetter.model.Tweet;
import com.kwetter.model.mapper.HashtagMapper;
import com.kwetter.model.mapper.TweetMapper;
import com.kwetter.model.mapper.UserMapper;
import com.kwetter.rest.dto.HashtagDto;
import com.kwetter.rest.dto.TweetDto;
import com.kwetter.rest.dto.UserDto;
import com.kwetter.service.TweetService;
import com.kwetter.service.UserService;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * Created by hein on 5/21/17.
 */
@Path("/tweets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@DeclareRoles({"UserRole", "AdminRole"})
public class TweetResource {

    @Inject
    TweetService tweetService;

    @Inject
    UserService userService;

    @GET
    public Response getAllTweets(@Context SecurityContext sc) {
        List<TweetDto> tweets = new TweetMapper().to(tweetService.getAllTweets());
        GenericEntity<List<TweetDto>> tweetList = new GenericEntity<List<TweetDto>>(tweets){};
        return Response.ok(tweetList).build();
    }

    @POST
    public Response createTweet(TweetDto tweetDto, @Context UriInfo uriInfo) {
        Tweet tweet = createTweetWithTweetDto(tweetDto);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(tweet.getId()));
        return Response.created(builder.build()).build();
    }

    @GET
    @Path("/{tweetId}")
    public Response getTweet(@PathParam("tweetId") Long tweetId) {
        Tweet tweet = findTweet(tweetId);
        TweetDto tweetDto = new TweetMapper().to(tweet);
        return Response.ok(tweetDto).build();
    }

    @PUT
    @Path("/{tweetId}")
    public Response updateTweet(@PathParam("tweetId") Long tweetId, TweetDto tweetDto, @Context UriInfo uriInfo) {
        Tweet tweet = findTweet(tweetId);
        tweet.setDescription(tweetDto.getDescription());
        tweetService.updateTweet(tweet);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(tweet.getId()));
        return Response.ok().location(builder.build()).build();
    }

    @GET
    @Path("/{tweetId}/replies")
    public Response getTweetReplies(@PathParam("tweetId") Long tweetId) {
        findTweet(tweetId);
        List<TweetDto> tweets = new TweetMapper().to(tweetService.getReplies(tweetId));
        GenericEntity<List<TweetDto>> tweetList = new GenericEntity<List<TweetDto>>(tweets){};
        return Response.ok(tweetList).build();
    }

    @GET
    @Path("/{tweetId}/hashtags")
    public Response getTweetHashtags(@PathParam("tweetId") Long tweetId) {
        findTweet(tweetId);
        List<HashtagDto> hashtags = new HashtagMapper().to(tweetService.getHashtags(tweetId));
        GenericEntity<List<HashtagDto>> hashtagList = new GenericEntity<List<HashtagDto>>(hashtags){};
        return Response.ok(hashtagList).build();
    }

    @GET
    @Path("/{tweetId}/likes")
    public Response getTweetLikes(@PathParam("tweetId") Long tweetId) {
        findTweet(tweetId);
        List<UserDto> users = new UserMapper().to(tweetService.getLikers(tweetId));
        GenericEntity<List<UserDto>> userList = new GenericEntity<List<UserDto>>(users){};
        return Response.ok(userList).build();
    }

    private Tweet findTweet(Long tweetId) {
        Tweet tweet = tweetService.getTweet(tweetId);
        if (tweet == null) {
            throw new DataNotFoundException("Tweet does not exist!");
        }
        return tweet;
    }

    private Tweet createTweetWithTweetDto(TweetDto tweetDto) {
        if (tweetDto.getDescription() == null || tweetDto.getTweeter() == null) {
            throw new DataNotFoundException("Tweet is invalid, missing data.");
        }
        String message = tweetDto.getDescription();
        Person tweeter = userService.getUser(tweetDto.getTweeter());
        Tweet parent = tweetService.getTweet(tweetDto.getRepliedTo());
        Tweet tweet;
        if (parent != null) {
            tweet = new Tweet(message, tweeter, parent);
        } else {
            tweet = new Tweet(message, tweeter);
        }
        tweetService.sendTweet(tweet);
        return tweet;
    }
}
