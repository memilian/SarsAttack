/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Lakio
 */
public abstract class Effect {
    
    protected Person target;
    protected long duration;
    protected long timeStarted;
    protected EffectType effectType;
    protected boolean isFinished;
    
    public Effect(Person target){
        this.target = target;
    }
    
    public abstract void onEffectStart();
    
    public abstract void onUpdate();
    
    public abstract void onEffectEnd();



    /**
     * @return the duration
     */
    public long getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return the timeStarted
     */
    public long getTimeStarted() {
        return timeStarted;
    }

    /**
     * @param timeStarted the timeStarted to set
     */
    public void setTimeStarted(long timeStarted) {
        this.timeStarted = timeStarted;
    }

    /**
     * @return the effectType
     */
    public EffectType getEffectType() {
        return effectType;
    }

    /**
     * @param effectType the effectType to set
     */
    public void setEffectType(EffectType effectType) {
        this.effectType = effectType;
    }

    /**
     * @return the target
     */
    public Person getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(Person target) {
        this.target = target;
    }
}
