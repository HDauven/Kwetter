package com.kwetter.dao.jpa;

import com.kwetter.dao.HashtagDao;
import com.kwetter.model.Hashtag;
import com.kwetter.model.Person;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by hein on 5/18/17.
 */
@Stateless @JPA
public class HashtagDaoJPA extends GenericDaoJPA<Hashtag> implements HashtagDao {

    @Override
    public Hashtag findByTag(String tag) {
        try {
            return em.createQuery("SELECT h FROM Hashtag h WHERE LOWER(h.tag) LIKE LOWER(:tag)", Hashtag.class)
                    .setParameter("tag", tag)
                    .getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }
}
