/*
 * Copyright (c), Eclipse Foundation, Inc. and its licensors.
 *
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v1.0, which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package cst8218.n041092398.bouncer.business;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 * AbstractFacade is a generic base class for managing entity persistence
 * It provides common CRUD operations and query methods for entities
 * @author Julian
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    // Constructor that initializes the entity class type
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    // Abstract method to be implemented by subclasses to return an EntityManager
    protected abstract EntityManager getEntityManager();

    // Creates a new entity and persists it in the database
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    // Updates an existing entity in the database
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    // Removes an entity from the database
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    // Finds an entity by its ID
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    // Retrieves all entities of the given type from the database
    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    // Retrieves a subset of entities within the specified range
    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    // Returns the total count of entities in the database
    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
