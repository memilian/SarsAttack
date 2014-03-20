/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Lakio
 */
public class EffectHEAL extends Effect{
    
    public EffectHEAL(Person target){
        super(target);
        effectType = EffectType.HEAL;
        duration = 1;
        isFinished = false;
    }

    @Override
    public void onEffectStart() {
        target.setLife(getTarget().getLife()+30);
        isFinished = true;
    }

    @Override
    public void onUpdate() {
        
    }

    @Override
    public void onEffectEnd() {
        
    }
}
