/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ia;

import com.jme3.math.Vector3f;
import entities.Entity;

/**
 *
 * @author memilian
 */
public abstract class SteeringBehavior {
    
    //the entity this steering behavior is acting on
    protected Entity entity;
    
    public SteeringBehavior(Entity entity){
        this.entity = entity;
    }
    
    public abstract Vector3f getSteering();    
}
