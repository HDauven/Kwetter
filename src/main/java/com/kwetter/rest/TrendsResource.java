package com.kwetter.rest;

import com.kwetter.model.Hashtag;
import com.kwetter.rest.dto.HashtagDto;
import com.kwetter.service.TweetService;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hein on 5/19/17.
 */
@Path("/trends")
@PermitAll
public class TrendsResource {

    @Inject
    TweetService tweetService;

    @GET
    @Produces
    public Response getTrends() {
        LinkedHashMap<Hashtag, Long> trendingHash = tweetService.getTrendingHashtags();
        List<HashtagDto> trendingHashtags = new ArrayList<>();
        for (Map.Entry<Hashtag, Long> entry : trendingHash.entrySet()) {
            HashtagDto hashtag = new HashtagDto();
            hashtag.setId(entry.getKey().getId());
            hashtag.setTag(entry.getKey().getTag());
            hashtag.setOccurrences(entry.getValue());
            trendingHashtags.add(hashtag);
        }
        GenericEntity<List<HashtagDto>> trendingHashtagList = new GenericEntity<List<HashtagDto>>(trendingHashtags){};
        return Response.ok(trendingHashtagList).build();
    }
}
