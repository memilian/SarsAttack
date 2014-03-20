/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author memilian
 */
public abstract class Entity extends Node{
    
    protected Vector3f position;
    protected Vector3f velocity;
    protected Vector3f heading;
    protected float maxSpeed;
    protected boolean isDead;

    public Entity(Vector3f position){
        this.position = position;
        this.velocity = Vector3f.ZERO.clone();
        this.heading = Vector3f.ZERO.clone();
        this.maxSpeed = 0;
        this.isDead = false;
    }
    
    public abstract void onUpdate(float time);
    
    public abstract void onDeath();
    
    
    
    //Getters & setters 
    
    
    public float getMaxSpeed() {
        return maxSpeed;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * @return the velocity
     */
    public Vector3f getVelocity() {
        return velocity;
    }

    /**
     * @return the heading
     */
    public Vector3f getHeading() {
        return heading;
    }

    /**
     * @param maxSpeed the maxSpeed to set
     */
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * @return the isDead
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * @param isDead the isDead to set
     */
    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }
    
}
