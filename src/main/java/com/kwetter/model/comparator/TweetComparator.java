package com.kwetter.model.comparator;

import com.kwetter.model.Tweet;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by hein on 5/22/17.
 */
public class TweetComparator {

    public static List<Tweet> sortByCreationDate(List<Tweet> tweets) {
        Collections.sort(tweets, (o1, o2) -> {
            if (o1.getCreatedAt() == null || o2.getCreatedAt() == null)
                return 0;
            return o2.getCreatedAt().compareTo(o1.getCreatedAt());
        });
        return tweets;
    }
}

