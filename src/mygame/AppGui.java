/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.ui.Picture;

/**
 *
 * @author dtrimoulet
 */
public class AppGui {
    
    private Picture startButton;
    private Picture PauseButton;
    private Picture resumeButton;
    private Picture towerButton;

    
    public AppGui(Main app)
    {
        this.startButton = new Picture("Buttonstart");
        this.startButton.setImage(app.getAssetManager(), "/Boutons/Buttonstart.png", true);
        this.startButton.setWidth(app.getMySettings().getWidth()/10);
        this.startButton.setHeight(app.getMySettings().getHeight()/10);
        this.startButton.setPosition(0, app.getMySettings().getHeight() - app.getMySettings().getHeight()/10);
        
        this.PauseButton = new Picture("PauseButton");
        this.PauseButton.setImage(app.getAssetManager(), "/Boutons/Buttonpause.png", true);
        this.PauseButton.setWidth(app.getMySettings().getWidth()/10);
        this.PauseButton.setHeight(app.getMySettings().getHeight()/10);
        this.PauseButton.setPosition(0, app.getMySettings().getHeight() - ((app.getMySettings().getHeight()/10)*2));
        
        this.resumeButton = new Picture("resumeButton");
        this.resumeButton.setImage(app.getAssetManager(), "/Boutons/ButtonResume.png", true);
        this.resumeButton.setWidth(app.getMySettings().getWidth()/10);
        this.resumeButton.setHeight(app.getMySettings().getHeight()/10);
        this.resumeButton.setPosition(0, app.getMySettings().getHeight() - ((app.getMySettings().getHeight()/10)*2));
        
        this.towerButton = new Picture("towerButton");
        this.towerButton.setImage(app.getAssetManager(), "/Boutons/ButtonResume.png", true);
        this.towerButton.setWidth(app.getMySettings().getWidth()/10);
        this.towerButton.setHeight(app.getMySettings().getHeight()/10);
        this.towerButton.setPosition(app.getMySettings().getWidth() - app.getMySettings().getWidth()/10 , 0);
    }
    /**
     * @return the startButton
     */
    public Picture getStartButton() {
        return startButton;
    }

    /**
     * @return the PauseButton
     */
    public Picture getPauseButton() {
        return PauseButton;
    }

    /**
     * @return the resumeButton
     */
    public Picture getResumeButton() {
        return resumeButton;
    }

    /**
     * @return the towerButton
     */
    public Picture getTowerButton() {
        return towerButton;
    }
    
}
