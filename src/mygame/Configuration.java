/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 *
 * @author dtrimoulet
 */
public class Configuration {
    
    private int difficulty;
    private boolean musique;
    //private Joke
    private boolean zombiemode;
    //lol
    private int resolutionX;
    private int resolutionY;
    
    //Set the default configuration
    public Configuration()
    {
        this.difficulty = 2;
        this.musique = true;
        //Penser à prévoir un bouton caché pour le Zombiemode
        this.zombiemode = false;
        this.resolutionX = 800;
        this.resolutionY = 600;
    }

    /**
     * @return the difficulty
     * 
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * @param difficulty the difficulty to set
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return the musique
     */
    public boolean isMusique() {
        return musique;
    }

    /**
     * @param musique the musique to set
     */
    public void setMusique(boolean musique) {
        this.musique = musique;
    }

    /**
     * @return the zombiemode
     */
    public boolean isZombiemode() {
        return zombiemode;
    }

    /**
     * @param zombiemode the zombiemode to set
     */
    public void setZombiemode(boolean zombiemode) {
        this.zombiemode = zombiemode;
    }

    /**
     * @return the resolutionX
     */
    public int getResolutionX() {
        return resolutionX;
    }

    /**
     * @param resolutionX the resolutionX to set
     */
    public void setResolutionX(int resolutionX) {
        this.resolutionX = resolutionX;
    }

    /**
     * @return the resolutionY
     */
    public int getResolutionY() {
        return resolutionY;
    }

    /**
     * @param resolutionY the resolutionY to set
     */
    public void setResolutionY(int resolutionY) {
        this.resolutionY = resolutionY;
    }

    /**
     * @return the résolution
     */
    
    
    
    
    
}
