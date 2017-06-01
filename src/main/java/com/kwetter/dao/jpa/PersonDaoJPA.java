package com.kwetter.dao.jpa;

import com.kwetter.dao.PersonDao;
import com.kwetter.model.Person;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

/**
 * Created by hein on 5/18/17.
 */
@Stateless @JPA
public class PersonDaoJPA extends GenericDaoJPA<Person> implements PersonDao {

    @Override
    public Person findByUsername(String username) {
        try {
            return em.createQuery("SELECT p FROM Person p WHERE LOWER(p.username) LIKE LOWER(:username)", Person.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }

    @Override
    public Long numberOfFollowers(Long userId) {
        try {
            return em.createQuery("SELECT SIZE(p.followers) FROM Person p WHERE p.id = :userId", Long.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }

    @Override
    public Long numberOfFollowing(Long userId) {
        try {
            return em.createQuery("SELECT SIZE(p.following) FROM Person p WHERE p.id = :userId", Long.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }

    @Override
    public Long numberOfTweets(Long userId) {
        try {
            return em.createQuery("SELECT SIZE(p.tweets) FROM Person p WHERE p.id = :userId", Long.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }

    @Override
    public Long numberOfLikes(Long userId) {
        try {
            return em.createQuery("SELECT SIZE(p.liked) FROM Person p WHERE p.id = :userId", Long.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }

    @Override
    public Long numberOfMentions(Long userId) {
        try {
            return em.createQuery("SELECT SIZE(p.mentions) FROM Person p WHERE p.id = :userId", Long.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }
}
