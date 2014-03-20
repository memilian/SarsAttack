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
public class Seek extends SteeringBehavior {

    Vector3f target;

    public Seek(Entity entity) {
        super(entity);
    }

    public void setTarget(Vector3f target) {
        this.target = target;
    }

    @Override
    public Vector3f getSteering() {
        if (target == null) {
            return Vector3f.ZERO.clone();
        }
        
        //We create a vector pointing from the entity's location to the target
        Vector3f desired = target.subtract(entity.getPosition());
        
        //Normalize desired and scale it to entity's maximum speed;
        desired.normalizeLocal();
        desired.multLocal(entity.getMaxSpeed());
        
        return desired;
    }
}
