/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package cst8218.n041092398.bouncer.business;

import cst8218.n041092398.bouncer.entity.Bouncer;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Stateless session bean that provides database access and management operations for Bouncer
 * entities, linked to the MariaDB persistence unit
 * @author Julian
 */
@Stateless
@LocalBean
public class BouncerFacade extends AbstractFacade<Bouncer> {

    @PersistenceContext(unitName = "MariaDB")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BouncerFacade() {
        super(Bouncer.class);
    }
}