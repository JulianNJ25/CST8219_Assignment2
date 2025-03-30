/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SingletonEjbClass.java to edit this template
 */
package cst8218.n041092398.bouncer.game;

import cst8218.n041092398.bouncer.business.BouncerFacade;
import cst8218.n041092398.bouncer.entity.Bouncer;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Startup;
import java.util.List;

/**
 * Singleton that manages the movement of all Bouncer entities, running continuosly in the background
 * initiating the game loop
 * @author Julian
 */
@Singleton
@Startup
@LocalBean
public class BouncerGame {
    @EJB
    private BouncerFacade bouncerFacade;  //inject facade into bouncer

    private List<Bouncer> bouncers;

    /**
     * Methods that updates all bouncer entities
     */
    
    @PostConstruct
    public void go() {
        new Thread(() -> {                          // Create a new thread to run the game loop
            while (true) {                          // Infinite loop to continuously update bouncers
                bouncers = bouncerFacade.findAll(); // Retrieve all bouncers from the database
                for (Bouncer bouncer : bouncers) {
                    bouncer.timeStep();             // Update the bouncer's movement
                        bouncerFacade.edit(bouncer);// Save the updated state in the database
                }

                try {
                    // Pause execution to control the update rate
                    Thread.sleep((long) (1000 / Bouncer.CHANGE_RATE));
                } catch (InterruptedException e) {
                    e.printStackTrace();// Print error if the thread is interrupted
                }
            }
        }).start(); // Start the background thread
    }
}
