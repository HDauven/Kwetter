package com.kwetter.dao.jpa;

import com.kwetter.dao.GenericDao;
import org.eclipse.persistence.queries.CursoredStream;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by hein on 4/7/17.
 */
@Stateless
public abstract class GenericDaoJPA<E> implements GenericDao<E> {

    @PersistenceContext(unitName = "kwetterPU")
    protected EntityManager em;

    protected Class<E>entityClass;

    protected GenericDaoJPA() {
        ParameterizedType genericClass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericClass.getActualTypeArguments()[0];
    }

    @Override
    public void create(E entity) {
        em.persist(entity);
    }

    @Override
    public E update(E entity) {
        return em.merge(entity);
    }

    @Override
    public void remove(E entity) {
        if (!em.contains(entity)) {
            entity = em.merge(entity);
        }
        em.remove(entity);
    }

    @Override
    public void flush() {
        em.flush();
    }

    @Override
    public E findById(Long id) {
        if (id == null) {
            return null;
        }
        return em.find(entityClass, id);
    }

    @Override
    public List<E>findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<E>cq = cb.createQuery(entityClass);
        cq.from(entityClass);
        TypedQuery<E>query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public boolean alreadyExists(Long id) {
        E result = em.find(entityClass, id);
        if (result != null) {
            return true;
        }
        return false;
    }

    @Override
    public CursoredStream getAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(entityClass);
        cq.from(entityClass);
        TypedQuery<E> scrollableCursor = em.createQuery(cq)
                .setHint("eclipselink.cursor", true)
                .setHint("eclipselink.cursor.page-size", 10);
        return (CursoredStream) scrollableCursor.getSingleResult();
    }
}
