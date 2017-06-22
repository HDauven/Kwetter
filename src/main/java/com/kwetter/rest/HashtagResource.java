package com.kwetter.rest;

import com.kwetter.model.mapper.HashtagMapper;
import com.kwetter.rest.dto.HashtagDto;
import com.kwetter.service.TweetService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by hein on 5/21/17.
 */
@Path("/hashtags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class HashtagResource {

    @Inject
    TweetService tweetService;

    @GET
    public List<HashtagDto> getAllHashtags() {
        return new HashtagMapper().to(tweetService.getHashtags());
    }

    @GET
    @Path("/{hashtagId}")
    public HashtagDto getHashtag(@PathParam("hashtagId") Long hashtagId) {
        return new HashtagMapper().to(tweetService.getHashtag(hashtagId));
    }
}
