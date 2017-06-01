package com.kwetter.dao.jpa;

import com.kwetter.dao.GroupDao;
import com.kwetter.model.Group;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created by hein on 5/28/17.
 */
@Stateless
public class GroupDaoJPA implements GroupDao {

    @PersistenceContext(unitName = "kwetterPU")
    protected EntityManager em;

    @Override
    public void create(Group entity) {
        em.persist(entity);
    }

    @Override
    public Group update(Group entity) {
        return em.merge(entity);
    }

    @Override
    public void remove(Group entity) {
        em.remove(entity);
    }

    @Override
    public void flush() {
        em.flush();
    }

    @Override
    public Group findById(String id) {
        if (id == null) {
            return null;
        }
        return em.find(Group.class, id);
    }

    @Override
    public List<Group> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Group> cq = cb.createQuery(Group.class);
        cq.from(Group.class);
        TypedQuery<Group> query = em.createQuery(cq);
        return query.getResultList();
    }
}
