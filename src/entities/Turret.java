/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import assets.SrasAssetManager;
import com.jme3.math.Vector3f;
import java.util.ArrayList;

/**
 *
 * @author Lakio
 */
public class Turret extends Entity {

    private float range;
    private int cost;
    private int upgradeCost;
    private int sellValue;
    private int level;
    private int maxLevel;
    private boolean isAOE;
    private TargetMode targetMode;
    private EffectType[] effectsType;
    private String description;
    private long fireRate = 100;
    private long fireTimer = 0;
    private long lastShoot =0;
    private ArrayList<Person> personList;

    public Turret(Vector3f position) {
        super(position);
        this.attachChild(SrasAssetManager.getInstance().getTurretModel().clone());
    }

    public Turret(Vector3f position, float range, int cost, int upgradeCost, int sellValue, int level, int maxLevel, boolean isAOE, TargetMode targetMode, EffectType[] effectsType, String description) {
        super(position);
        this.range = range;
        this.cost = cost;
        this.upgradeCost = upgradeCost;
        this.sellValue = sellValue;
        this.level = level;
        this.maxLevel = maxLevel;
        this.isAOE = isAOE;
        this.targetMode = targetMode;
        this.effectsType = effectsType;
        this.description = description;
        this.attachChild(SrasAssetManager.getInstance().getTurretModel().clone());
        this.setLocalTranslation(position);
    }

    @Override
    public void onUpdate(float tpf) {
        fireTimer = System.currentTimeMillis();

        if (fireTimer - lastShoot > fireRate) {
            lastShoot = fireTimer;
            fire();
        }
    }

    @Override
    public void onDeath() {
    }

    public void fire() {
        if (targetMode == TargetMode.Nearest) {
            Person nearest = EntityManager.getInstance().getNearestPersonInRange(position, range);
            if (nearest != null) {
                
                EntityManager.getInstance().registerEntity(new Projectile(position.clone(), nearest, effectsType));
            }
        } else if (targetMode == TargetMode.Farthest) {
            throw new UnsupportedOperationException("Not supported yet.");
        } else if (targetMode == TargetMode.Weakest) {
            EntityManager.getInstance().registerEntity(new Projectile(position, EntityManager.getInstance().getWeakestPersonInRange(getPosition(), range), effectsType));
        } else if (targetMode == TargetMode.AOE) {
            personList = EntityManager.getInstance().getEntitiesInRange(Person.class, getPosition(), range);

            for (Person perso : personList) {
                EntityManager.getInstance().registerEntity(new Projectile(getPosition(), perso, effectsType));
            }
        }
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getRange() {
        return this.range;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return this.cost;
    }

    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }

    public int getUpgradeCost() {
        return this.upgradeCost;
    }

    public void setSellValue(int sellValue) {
        this.sellValue = sellValue;
    }

    public int getSellValue() {
        return this.sellValue;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public void setIsAOE(boolean isAOE) {
        this.isAOE = isAOE;
    }

    public boolean getIsAOE() {
        return this.isAOE;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
