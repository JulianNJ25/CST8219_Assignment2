/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cst8218.n041092398.bouncer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.io.Serializable;

/**
 * Class that represents a movable entity, it defines its properties and behaviour related to movement
 * @author Julian
 */
@Entity
public class Bouncer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    // Constants
    public static final int INITIAL_SIZE = 50;
    public static final int SIZE_LIMIT = 100;
    public static final int X_LIMIT = 800;
    public static final int Y_LIMIT = 600;
    public static final int TRAVEL_SPEED = 5;
    public static final int MAX_DIR_CHANGES = 10;
    public static final int DECREASE_RATE = 1;
    public static final double CHANGE_RATE = 30.0; // 30 updates per second

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Unique identifier, auto-generated

    @Min(1)
    @Max(SIZE_LIMIT)
    private int size = INITIAL_SIZE; // Size of the bouncer

    @Min(0)
    @Max(X_LIMIT)
    private int x; // X-coordinate

    @Min(0)
    @Max(Y_LIMIT)
    private int y; // Y-coordinate

    @Min(0)
    private int maxTravel = INITIAL_SIZE; // Maximum distance a bouncer can move
    private int currentTravel = 0; // Current travel distance
    private int mvtDirection = 1; // 1 means moving right, -1 means moving left
    private int dirChangeCount = 0; // Tracks direction changes

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMaxTravel() {
        return maxTravel;
    }

    public void setMaxTravel(int maxTravel) {
        this.maxTravel = maxTravel;
    }

    public int getCurrentTravel() {
        return currentTravel;
    }

    public void setCurrentTravel(int currentTravel) {
        this.currentTravel = currentTravel;
    }

    public int getMvtDirection() {
        return mvtDirection;
    }

    public void setMvtDirection(int mvtDirection) {
        this.mvtDirection = mvtDirection;
    }

    public int getDirChangeCount() {
        return dirChangeCount;
    }

    public void setDirChangeCount(int dirChangeCount) {
        this.dirChangeCount = dirChangeCount;
    }
    //end of setters and getters

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Bouncer)) {
            return false;
        }
        Bouncer other = (Bouncer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.n041092398.bouncer.entity.Bouncer[ id=" + id + " ]";
    }

    // Method to update movement over time
    public void timeStep() {
        if (maxTravel > 0) { // Check if movement is allowed
            currentTravel += mvtDirection * TRAVEL_SPEED; // Move in the current direction at a fixed speed
            if (Math.abs(currentTravel) >= maxTravel) { // If the traveled distance reaches or exceeds maxTravel
                mvtDirection = -mvtDirection; // Reverse direction
                dirChangeCount++; // Increment direction change counter
                if (dirChangeCount >= MAX_DIR_CHANGES) { // If direction changes reach the limit
                    maxTravel = Math.max(0, maxTravel - DECREASE_RATE); // Reduce maxTravel but not below 0
                    dirChangeCount = 0; // Reset the direction change counter
                }
            }
        }
    }

    // Method to update properties with non-null values from another Bouncer
    public void update(Bouncer newBouncer) {
        if (newBouncer.getSize() != 0) {
            this.size = newBouncer.getSize();
        }
        if (newBouncer.getX() != 0) {
            this.x = newBouncer.getX();
        }
        if (newBouncer.getY() != 0) {
            this.y = newBouncer.getY();
        }
        if (newBouncer.getMaxTravel() != 0) {
            this.maxTravel = newBouncer.getMaxTravel();
        }
        if (newBouncer.getCurrentTravel() != 0) {
            this.currentTravel = newBouncer.getCurrentTravel();
        }
        if (newBouncer.getMvtDirection() != 0) {
            this.mvtDirection = newBouncer.getMvtDirection();
        }
        if (newBouncer.getDirChangeCount() != 0) {
            this.dirChangeCount = newBouncer.getDirChangeCount();
        }
    }
    
}
