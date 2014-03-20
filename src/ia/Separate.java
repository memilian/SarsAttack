/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ia;

import com.jme3.math.Vector3f;
import entities.Entity;
import entities.Person;
import entities.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author memilian
 */
public class Separate extends SteeringBehavior {

    private int range = 100;

    public Separate(Entity entity) {
        super(entity);
    }

    @Override
    public Vector3f getSteering() {
        
        ArrayList<Entity> targets = (ArrayList<Entity>) EntityManager.getInstance().getEntitiesInRange(
                entity.getClass(),
                entity.getPosition(),
                range);
        
        Vector3f steer = Vector3f.ZERO.clone();
        int count = 0;

        //For each person in range, check if it's too close
        for (Entity other : targets) {
            if (other == entity) {
                continue;
            }

            //Get the vector from the entity pointing toward the other entity
            Vector3f dir = entity.getPosition().subtract(other.getPosition());
            float dist = dir.lengthSquared();

            //minimum separation between 2 adjacent entities so they don't overlap
            //we take the squared value to avoid calculation of the root square
            float requiredSeparation = entity.getSize() * other.getSize();

            if (dist > 0 && dist < requiredSeparation) {
                //TODO: tweak strengh value for best results
                float strengh = 200 / dist; //strengh of repulsion
                steer.addLocal(dir.multLocal(strengh));
                count++;
            }
        }
        if (count > 0) {
            steer.divideLocal(count);
        }
        return steer;
    }
}
