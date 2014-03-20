/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import assets.SrasAssetManager;
import com.jme3.math.Vector3f;
import ia.Seek;

/**
 *
 * @author memilian
 */
public class Projectile extends Entity {

    private Person target;
    private EffectType[] effects;
    private Effect effect;
    private Seek comportement;



    public Projectile(Vector3f position, Person target, EffectType[] effects) {
        super(position);
        this.effects = effects;
        this.maxSpeed = 1.4f;
        this.comportement = new Seek(this);
        this.target = target;
        this.attachChild(SrasAssetManager.getInstance().getProjModel().clone());
    }

    @Override
    public void onUpdate(float tpf) {
        if (isDead) {
            return;
        }
        this.comportement.setTarget(this.target.getPosition());

        /*
         * Move
         */
        float distToTarget = this.position.subtract(target.getPosition()).length();
        if (distToTarget < 1) {
            this.isDead = true;
            onCollision(target);
        }


        velocity = comportement.getSteering();
        position.addLocal(velocity);
        this.setLocalTranslation(position);
    }

    @Override
    public void onDeath() {
        
    }

    public void onCollision(Person person) {
        
        for (EffectType effect : effects) {

            if (effect == EffectType.HEAL) {
                this.effect = new EffectHEAL(target);
                person.addEffect(this.effect);
                this.effect.onEffectStart();
            } else if (effect == EffectType.REGEN) {
                this.effect = new EffectREGEN(target);
                person.addEffect(this.effect);
                this.effect.onEffectStart();
            } else if (effect == EffectType.SPEEDUP) {
                this.effect = new EffectSPEEDUP(target);
                person.addEffect(this.effect);
                this.effect.onEffectStart();
            }
        }
    }
}
