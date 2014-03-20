/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Lakio
 */
public class EffectREGEN extends Effect{
    
    public EffectREGEN(Person target){
        super(target);
        effectType = EffectType.REGEN;
        duration = 5;
        isFinished = false;
    }
    
    @Override
    public void onEffectStart() {
        target.setRegenRate(target.getRegenRate()+0.01f);
    }

    @Override
    public void onUpdate() {
        if(timeStarted+duration > System.currentTimeMillis()){
            onEffectEnd();
            isFinished = true;
        }
    }

    @Override
    public void onEffectEnd() {
        target.setRegenRate(target.getRegenRate()-0.01f);
    }

    
}
