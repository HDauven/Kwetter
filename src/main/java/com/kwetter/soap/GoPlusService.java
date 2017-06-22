package com.kwetter.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by hein on 5/25/17.
 */
@WebService
public interface GoPlusService {
    @WebMethod
    void createTweet(SimpleTweet tweet);

    @WebMethod
    List<SimpleTweet> getTimeline(String username);
}
