/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import sun.awt.geom.AreaOp;

/**
 *
 * @author memilian
 */
public class EntityManager {

    private static EntityManager instance;
    private ArrayList<Entity> entities;
    private ArrayList<Person> persons;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Turret> turrets;
    private Stack<Entity> toAdd;
    private ArrayList<Entity> toRemove;
    private Node entityNode;

    public Node getEntityNode() {
        return entityNode;
    }

    public static EntityManager getInstance() {
        if (instance == null) {
            instance = new EntityManager();
        }
        return instance;
    }

    private EntityManager() {
        entities = new ArrayList<Entity>();
        toAdd = new Stack<Entity>();
        toRemove = new ArrayList<Entity>();
        entityNode = new Node("entityNode");

        projectiles = new ArrayList<Projectile>();
        turrets = new ArrayList<Turret>();
        persons = new ArrayList<Person>();
    }

    /**
     *
     * @param <T> Generic function to get entities from a specified class that
     * are within a range around the specified position
     * @param tClass the class of the entities we want
     * @param position center position of the search
     * @param radius radius of the search
     * @return
     */
    public <T extends Entity> ArrayList<T> getEntitiesInRange(Class<T> tClass, Vector3f position, float radius) {
        ArrayList<T> result = new ArrayList<T>();
        int rangeSquared = (int) (radius * radius);

        for (Entity ent : entities) {
            //skip entities that are not instance of the class T
            if (!ent.getClass().isAssignableFrom(tClass)) {
                continue;
            }
            //Get distance from position to entity
            float distSquared = position.subtract(ent.getPosition()).lengthSquared();

            if (distSquared < rangeSquared) {
                result.add((T) ent);
            }
        }
        return result;
    }

    public Person getNearestPersonInRange(Vector3f position, float radius) {

        int rangeSquared = (int) (radius * radius);
        Person nearest = null;
        float minDist = Float.MAX_VALUE;
        for (Entity ent : entities) {
            //skip entities that are not instance of Person
            if (!ent.getClass().isAssignableFrom(Person.class)) {
                continue;
            }
            //Get distance from position to entity
            float distSquared = position.subtract(ent.getPosition()).lengthSquared();

            //keep the nearest
            if (distSquared < rangeSquared && distSquared < minDist) {
                nearest = (Person) ent;
                minDist = distSquared;
            }
        }
        return nearest;
    }

    public Person getWeakestPersonInRange(Vector3f position, float radius) {

        int rangeSquared = (int) (radius * radius);
        Person weakest = null;
        float minLife = Float.MAX_VALUE;
        for (Entity ent : entities) {
            //skip entities that are not instance of Person
            if (!ent.getClass().isAssignableFrom(Person.class)) {
                continue;
            }
            //Get distance from position to entity
            float distSquared = position.subtract(ent.getPosition()).lengthSquared();

            //keep the nearest
            if (distSquared < rangeSquared && distSquared < minLife) {
                weakest = (Person) ent;
                minLife = weakest.getLife();
            }
        }
        return weakest;
    }

    public void registerEntity(Entity entity) {
        toAdd.add(entity);
    }
    /*public void registerPerson(Person entity) {
     persons.add(entity);
     entityNode.attachChild(entity);
     }
     */

    public void registerTurret(Turret entity) {
        turrets.add(entity);
        entityNode.attachChild(entity);
    }
    /*
     public void registerProjectile(Projectile entity) {
     projectiles.add(entity);
     entityNode.attachChild(entity);
     }
     */

    public void onUpdate(float tpf) {
        int index = entities.size();
        // entities.addAll((Collection<? extends Entity>) toAdd.clone());


        for (Entity ent : toAdd) {
            int i = entities.size();
            entities.add(ent);
            entityNode.attachChild(entities.get(i));
        }
        // toAdd.clear();

        for (Entity ent : toRemove) {
            entities.remove(ent);
            entityNode.detachChild(ent);
        }

        ArrayList<Entity> deadEntities = new ArrayList<Entity>();
        for (Entity ent : entities) {
            ent.onUpdate(tpf);
            if (ent.isDead()) {
                ent.onDeath();
                deadEntities.add(ent);
            }
        }

        for (Entity ent : deadEntities) {
            entities.remove(ent);
            entityNode.detachChild(ent);
        }

        toRemove.clear();
        deadEntities.clear();

        ArrayList<Turret> deadturret = new ArrayList<Turret>();
        for (Turret ent : turrets) {
            ent.onUpdate(tpf);
            if (ent.isDead()) {
                ent.onDeath();
                deadturret.add(ent);
            }
        }

        for (Turret ent : deadturret) {
            turrets.remove(ent);
            entityNode.detachChild(ent);
        }

        deadturret.clear();
        /*
         ArrayList<Projectile> deadproj = new ArrayList<Projectile>();
         for (Projectile ent : projectiles) {
         ent.onUpdate(tpf);
         if (ent.isDead()) {
         ent.onDeath();
         deadproj.add(ent);
         }
         }

         for (Projectile ent : deadproj) {
         projectiles.remove(ent);
         entityNode.detachChild(ent);
         }

         deadproj.clear();


         ArrayList<Turret> deadturret = new ArrayList<Turret>();
         for (Turret ent : turrets) {
         ent.onUpdate(tpf);
         if (ent.isDead()) {
         ent.onDeath();
         deadturret.add(ent);
         }
         }

         for (Turret ent : deadturret) {
         turrets.remove(ent);
         entityNode.detachChild(ent);
         }

         deadturret.clear();

         ArrayList<Person> deadpers = new ArrayList<Person>();
         for (Person ent : persons) {
         ent.onUpdate(tpf);
         if (ent.isDead()) {
         ent.onDeath();
         deadpers.add(ent);
         }
         }

         for (Person ent : deadpers) {
         persons.remove(ent);
         entityNode.detachChild(ent);
         }

         deadpers.clear();
         */
    }
}
