/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import com.jme3.math.Vector3f;
import java.util.ArrayList;

/**
 *
 * @author memilian
 */
public class Waypoint {

    private Vector3f position;
    private ArrayList<Waypoint> nextWaypoint;
    private int nextDispatch = 0;
    private boolean isLastWaypoint = false;
    
    

    public Waypoint(Vector3f position) {
        this.position = position;
        nextWaypoint = new ArrayList<Waypoint>();
    }
    
    public boolean isLastWayPoint(){
        return isLastWaypoint;
    }

    public void addNextWaypoint(Waypoint waypoint) {
        nextWaypoint.add(waypoint);
    }

    /**
     * @return the position
     */
    public Vector3f getPosition() {
        return position.clone();
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Waypoint getNextWaypoint() {
        if (nextWaypoint.isEmpty()) {
            return null;
        }
        if (nextWaypoint.size() > 1) {
            return nextWaypoint.get((nextDispatch++) % nextWaypoint.size());
        }
        return nextWaypoint.get(0);
    }

    void setIsLastWaypoint(boolean b) {
        isLastWaypoint = b;
    }
}
