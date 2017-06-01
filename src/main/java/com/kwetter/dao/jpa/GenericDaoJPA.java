package com.kwetter.dao.jpa;

import com.kwetter.dao.BaseDao;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created by hein on 4/7/17.
 */
public abstract class BaseDaoJPA<T> implements BaseDao<T> {

    protected EntityManager em;

    @Override
    public void create(T entity) {
        em.persist(entity);
    }

    @Override
    public T update(T entity) {
        return em.merge(entity);
    }

    @Override
    public void remove(T entity) {
        em.remove(entity);
    }

    @Override
    public void flush() {
        em.flush();
    }

    @Override
    public T findById(Long id) {
        if (id == null) {
            return null;
        }
        return em.find(T.class, id);
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(T.class);
        c.from(T.class);

        return em.;
    }

    @Override
    public boolean alreadyExists() {
        return false;
    }

    @Override
    public boolean alreadyExistsById() {
        return false;
    }
}
