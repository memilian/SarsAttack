/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Lakio
 */
public class EffectSPEEDUP extends Effect{
    
    public EffectSPEEDUP(Person target){
        super(target);
        effectType = EffectType.SPEEDUP;
        duration = 5;
        isFinished = false;
    }

    @Override
    public void onEffectStart() {
        target.setMaxSpeed(target.getMaxSpeed()+2);
        timeStarted = System.currentTimeMillis();
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
        target.setMaxSpeed(target.getMaxSpeed()-2);
    }

}
