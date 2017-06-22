package com.kwetter.service;

import com.kwetter.model.Person;
import com.kwetter.model.Tweet;

import java.util.List;

/**
 * Created by hein on 5/18/17.
 */
public interface UserService {

    void createUser(Person user);

    void updateUser(Person user);

    void removeUser(Person user);

    Person getUser(String username);

    Person getUser(Long id);

    List<Person> getAllUsers();

    Long getNumberOfFollowers(Long userId);

    Long getNumberOfFollowing(Long userId);

    Long getNumberOfTweets(Long userId);

    Long getNumberOfLikes(Long userId);

    Long getNumberOfMentions(Long userId);

    void followUser(Long userId, Long toFollowUser);

    List<Tweet> getTweets(Long userId);

    List<Person> getFollowers(Long userId);

    List<Person> getFollowing(Long userId);

    List<Tweet> getMentions(Long userId);

    List<Tweet> getLikes(Long userId);
}
