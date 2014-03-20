/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import assets.SrasAssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import ia.Seek;
import java.util.ArrayList;
import map.Waypoint;

/**
 *
 * @author memilian
 */
public class Person extends Entity {

    private float life;
    private final float maxLife;
    private float regenRate;
    private String description;
    private int saveIncome;
    private ArrayList<Effect> effectList;
    //The waypoint this person is walking toward
    private Waypoint target;
    private Seek seek;
    private Geometry model;
    private Material mat;

    public Person(Waypoint spawnPoint) {
        super(spawnPoint.getPosition());
        this.target = spawnPoint;
        this.maxLife = 1000;
        this.maxSpeed = 1.10f;
        this.life = 1000;
        this.regenRate = -0.08f;
        this.description = "A basic Person";
        this.saveIncome = 1;

        //clone and attach the model;
        this.model = (Geometry) SrasAssetManager.getInstance().getPersonModel().clone();
        this.mat = model.getMaterial();

        mat.setTransparent(true);
        mat.setColor("Color", ColorRGBA.Green);
        mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);

        model.setQueueBucket(RenderQueue.Bucket.Transparent);
        this.attachChild(model);
        seek = new Seek(this);
        effectList = new ArrayList<Effect>();
    }

    float getLife() {
        return life;
    }

    /**
     * @param life the life to set
     */
    public void setLife(float life) {
        this.life = life;
    }

    /**
     * @return the maxLife
     */
    public float getMaxLife() {
        return maxLife;
    }

    /**
     * @return the regenRate
     */
    public float getRegenRate() {
        return regenRate;
    }

    /**
     * @param regenRate the regenRate to set
     */
    public void setRegenRate(float regenRate) {
        this.regenRate = regenRate;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the saveIncome
     */
    public int getSaveIncome() {
        return saveIncome;
    }

    /**
     * @param saveIncome the saveIncome to set
     */
    public void setSaveIncome(int saveIncome) {
        this.saveIncome = saveIncome;
    }

    public void addEffect(Effect effect) {
        effectList.add(effect);
    }

    public void removeEffect(Effect effect) {
        effectList.remove(effect);
    }

    @Override
    public void onUpdate(float tpf) {
        if (isDead) {
            return;
        }

        ArrayList<Effect> toRemove = new ArrayList<Effect>();
        for (Effect effect : effectList) {
            if (effect.isFinished == true) {
                toRemove.add(effect);
            } else {
                effect.onUpdate();
            }
        }

        for (Effect e : toRemove) {
            effectList.remove(e);
        }
        toRemove.clear();

        //update life :
        this.life += regenRate;

        if (this.life <= 0) {
            this.isDead = true;
        }
        if (life > 0) {
            mat.setColor("Color", new ColorRGBA(1 - (life / maxLife), (life / maxLife), 0, 0.5f+(life / maxLife) / 2));
        }

        /*
         * Move
         */
        float distToWaypoint = this.position.subtract(target.getPosition()).length();
        //Si le waypoint est atteint, passer au suivant
        if (distToWaypoint < 1) {
            target = target.getNextWaypoint();
            if (target != null) {
                seek.setTarget(target.getPosition());
            } else {
                this.isDead = true;
            }
        }

        velocity = seek.getSteering().mult(tpf);

        position.addLocal(velocity);
        this.setLocalTranslation(position);
    }

    @Override
    public void onDeath() {
    }
}
