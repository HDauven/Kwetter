package com.kwetter.service;

import com.kwetter.dao.GroupDao;
import com.kwetter.dao.PersonDao;
import com.kwetter.dao.jpa.JPA;
import com.kwetter.model.Group;
import com.kwetter.model.Person;
import com.kwetter.model.Tweet;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by hein on 5/18/17.
 */
@Stateless
public class UserServiceImpl implements UserService {

    @Inject @JPA
    private PersonDao personDao;

    @Inject
    private GroupDao groupDao;

    @Override
    public void createUser(Person user) {
        personDao.create(user);
        Group userGroup = groupDao.findById("UserGroup");
        userGroup.addUserToGroup(user);
    }

    @Override
    public void updateUser(Person user) {
        personDao.update(user);
    }

    @Override
    public void removeUser(Person user) {

    }

    @Override
    public Person getUser(String username) {
        return personDao.findByUsername(username);
    }

    @Override
    public Person getUser(Long id) {
        return personDao.findById(id);
    }

    @Override
    public List<Person> getAllUsers() {
        return personDao.findAll();
    }

    @Override
    public Long getNumberOfFollowers(Long userId) {
        return personDao.numberOfFollowers(userId);
    }

    @Override
    public Long getNumberOfFollowing(Long userId) {
        return personDao.numberOfFollowing(userId);
    }

    @Override
    public Long getNumberOfTweets(Long userId) {
        return personDao.numberOfTweets(userId);
    }

    @Override
    public Long getNumberOfLikes(Long userId) {
        return personDao.numberOfLikes(userId);
    }

    @Override
    public Long getNumberOfMentions(Long userId) {
        return personDao.numberOfMentions(userId);
    }

    @Override
    public void followUser(Long userId, Long toFollowUser) {
        Person user = personDao.findById(userId);
        if (user == null) {
            throw new NullPointerException("The user does not exist!");
        }
        Person toFollowPerson =  personDao.findById(toFollowUser);
        if (toFollowPerson == null) {
            throw new NullPointerException("The user does not exist!");
        }
        user.addFollowing(toFollowPerson);
    }

    @Override
    public List<Tweet> getTweets(Long userId) {
        Person user = personDao.findById(userId);
        if (user == null) {
            throw new NullPointerException("The user does not exist!");
        }
        return user.getTweets();
    }

    @Override
    public List<Person> getFollowers(Long userId) {
        Person user = personDao.findById(userId);
        if (user == null) {
            throw new NullPointerException("The user does not exist!");
        }
        return user.getFollowers();
    }

    @Override
    public List<Person> getFollowing(Long userId) {
        Person user = personDao.findById(userId);
        if (user == null) {
            throw new NullPointerException("The user does not exist!");
        }
        return user.getFollowing();
    }

    @Override
    public List<Tweet> getMentions(Long userId) {
        Person user = personDao.findById(userId);
        if (user == null) {
            throw new NullPointerException("The user does not exist!");
        }
        return user.getMentions();
    }

    @Override
    public List<Tweet> getLikes(Long userId) {
        Person user = personDao.findById(userId);
        if (user == null) {
            throw new NullPointerException("The user does not exist!");
        }
        return user.getLikes();
    }
}
