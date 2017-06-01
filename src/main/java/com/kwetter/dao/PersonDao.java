package com.kwetter.dao;

import com.kwetter.dao.jpa.GenericDaoJPA;
import com.kwetter.model.Person;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by hein on 3/11/17.
 */
public interface PersonDao extends GenericDao<Person> {

    /**
     * Retrieve a Person from the data store with the corresponding username.
     *
     * @param username The username of the Person requested from the data store.
     * @return The Person with a matching username.
     */
    Person findByUsername(String username);

    Long numberOfFollowers(Long userId);

    Long numberOfFollowing(Long userId);

    Long numberOfTweets(Long userId);

    Long numberOfLikes(Long userId);

    Long numberOfMentions(Long userId);
}
