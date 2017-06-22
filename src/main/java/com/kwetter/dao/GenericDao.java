package com.kwetter.dao;

import org.eclipse.persistence.queries.CursoredStream;

import java.util.List;

/**
 * Created by hein on 4/7/17.
 */
public interface GenericDao<E> {

    /**
     * Creates an object and saves it to the data store, if it passes validation.
     * @param entity The object that should be created.
     */
    void create(E entity);

    /**
     * Updates an object and saves it to the data store, if it passes validation.
     * The resulting object is returned, no matter if the update was successful or not.
     * @param entity The object that should be updated, with updated values.
     * @return The resulting object, successful or not.
     */
    E update(E entity);

    /**
     * Removes an object from the data store.
     * @param entity The object to be removed.
     */
    void remove(E entity);

    /**
     *  Persists the objects that are currently created in memory, but not yet stored in the data store.
     */
    void flush();

    /**
     * Retrieve an object from the data store with the corresponding ID.
     * @param id The ID of the object requested from the data store.
     * @return The object with a matching ID.
     */
    E findById(Long id);

    /**
     * Retrieve all objects from the data store.
     * @return A list of all the objects.
     */
    List<E> findAll();

    CursoredStream getAll();

    /**
     * Check whether an object exists in the data store. Finds the object by passing in the ID of the object.
     * @param id The ID of the object that is queried.
     * @return boolean true if object exists, false if object does not exist.
     */
    boolean alreadyExists(Long id);
}
