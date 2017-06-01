package com.kwetter.dao;

import com.kwetter.model.Group;

import java.util.List;

/**
 * Created by hein on 5/28/17.
 */
public interface GroupDao {
    void create(Group entity);

    Group update(Group entity);

    void remove(Group entity);

    void flush();

    Group findById(String id);

    List<Group> findAll();
}
