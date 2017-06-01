package com.kwetter.dao;

import java.util.List;

/**
 * Created by hein on 4/7/17.
 */
public interface BaseDao<T> {

    /**
     * Creates an object and saves it to the data store, if it passes validation.
     * @param entity The object that should be created.
     */
    void create(T entity);

    /**
     * Updates an object and saves it to the data store, if it passes validation.
     * The resulting object is returned, no matter if the update was successful or not.
     * @param entity The object that should be updated, with updated values.
     * @return The resulting object, successful or not.
     */
    T update(T entity);

    /**
     * Removes an object from the data store.
     * @param entity The object to be removed.
     */
    void remove(T entity);

    /**
     *  Persists the objects that are currently created in memory, but not yet stored in the data store.
     */
    void flush();

    /**
     *
     * @param id
     * @return
     */
    T findById(Long id);

    /**
     *
     * @return
     */
    List<T> findAll();

    /**
     *
     * @return
     */
    boolean alreadyExists();

    /**
     *
     * @return
     */
    boolean alreadyExistsById();
}
