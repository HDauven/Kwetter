package com.kwetter.dao;

import com.kwetter.domain.Person;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by hein on 3/11/17.
 */
@Stateless
public class PersonDao {

    @PersistenceContext
    private EntityManager em;

    public List<Person> getAll() {
        return em.createNamedQuery("Person.getAll", Person.class).getResultList();
    }
}
