/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cst8218.n041092398.bouncer.rest;

import cst8218.n041092398.bouncer.entity.Bouncer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * RESTful service for managing Bouncer entities, handling CRUD operations via HTTP requests
 * Provides methods to create, update, retrieve, and delete Bouncers using JSON/XML.
 * @author Julian
 */
@Stateless
@Path("cst8218.n041092398.bouncer.entity.bouncer")
public class BouncerFacadeREST extends AbstractFacade<Bouncer> {

    @PersistenceContext(unitName = "MariaDB")
    private EntityManager em;

    public BouncerFacadeREST() {
        super(Bouncer.class);
    }
    
        @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * POST method to create a new Bouncer or update an existing one.
     * - If the Bouncer's ID is null, a new Bouncer is created (returns 201 CREATED).
     * - If the Bouncer's ID exists, the existing Bouncer is updated (returns 200 OK).
     * - If the Bouncer's ID is not null but doesn't exist, it's a bad request (returns 400 BAD_REQUEST).
     * @param entity
     * @return 
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createBouncer(Bouncer entity) {
        if (entity.getId() == null) {
            // Create a new Bouncer
            super.create(entity);
            return Response.status(Response.Status.CREATED).entity(entity).build();
        } else {
            // Update an existing Bouncer
            Bouncer existing = super.find(entity.getId());
            if (existing != null) {
                existing.update(entity); // Merge non-null values from the new entity
                super.edit(existing);
                return Response.ok(existing).build();
            } else {
                // ID is not null but doesn't exist in the database
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
    }

     /**
     * PUT method to update a Bouncer by ID.
     * - If the Bouncer's ID in the URL doesn't match the ID in the body, it's a bad request (returns 400 BAD_REQUEST).
     * - If the Bouncer doesn't exist, it's a bad request (returns 400 BAD_REQUEST).
     * - If the Bouncer exists, it is updated with the new non-null values (returns 200 OK).
     * @param id
     * @param entity
     * @return 
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, Bouncer entity) {
        if (entity.getId() != null && !entity.getId().equals(id)) {
            // ID in URL doesn't match ID in the body
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        entity.setId(id); // Ensure the ID is set
        Bouncer existing = super.find(id);
        if (existing == null) {
            // Bouncer with the given ID doesn't exist
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        existing.update(entity); // Merge non-null values from the new entity
        super.edit(existing);
        return Response.ok(existing).build();
    }
    
        /**
     * POST method to replace a Bouncer by ID.
     * - If the Bouncer's ID in the URL doesn't match the ID in the body, it's a bad request (returns 400 BAD_REQUEST).
     * - If the Bouncer doesn't exist, it's a bad request (returns 400 BAD_REQUEST).
     * - If the Bouncer exists, it is completely replaced with the new entity (returns 200 OK).
     * @param id
     * @param entity
     * @return 
     */
    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postById(@PathParam("id") Long id, @Valid Bouncer entity) {
        if (entity.getId() != null && !entity.getId().equals(id)) {
            // ID in URL doesn't match ID in the body
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Bouncer existing = super.find(id);
        if (existing == null) {
            // Bouncer with the given ID doesn't exist
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        entity.setId(id); // Ensure the ID is set
        super.edit(entity); // Replace the existing Bouncer
        return Response.ok(entity).build();
    }


     /**
     * GET method to retrieve a Bouncer by ID.
     * - If the Bouncer doesn't exist, it returns 404 NOT_FOUND.
     * - If the Bouncer exists, it is returned (returns 200 OK).
     * @param id
     * @return 
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id) {
        Bouncer bouncer = super.find(id);
        if (bouncer == null) {
            // Bouncer with the given ID doesn't exist
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(bouncer).build();
    }

    // retrieves all Bouncer entities in a JSON or XML format
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Bouncer> findAll() {
        return super.findAll();
    }

    // Retrieves a range of Bouncer entities based on idex boundaries
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Bouncer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

     /**
     * GET method to retrieve the count of all Bouncers in the database.
     * - Returns the count as a plain text response (returns 200 OK).
     * @return 
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    private final ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();

     /**
     * DELETE method to remove a Bouncer by ID.
     * - If the Bouncer doesn't exist, it returns 404 NOT_FOUND.
     * - If the Bouncer exists, it is removed (returns 204 NO_CONTENT).
     * @param id
     * @return 
     */
    @DELETE
    public Response remove(@PathParam("id") Long id) {
        Bouncer bouncer = super.find(id);
        if (bouncer == null) {
            // Bouncer with the given ID doesn't exist
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        super.remove(bouncer); // Remove the Bouncer
        return Response.noContent().build();
    }
    
     /**
     * PUT method is not allowed on the root resource.
     * - Attempting to update the entire Bouncer table is not permitted.
     * - Returns 405 METHOD_NOT_ALLOWED with an appropriate message.
     * @return Response indicating that PUT is not allowed.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response putNotAllowed() {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("PUT on the root resource is not allowed").build();
    }

    private Response doRemove(@PathParam("id") Long id) {
        Bouncer bouncer = super.find(id);
        if (bouncer == null) {
            // Bouncer with the given ID doesn't exist
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        super.remove(bouncer); // Remove the Bouncer
        return Response.noContent().build();
    }
    
}
