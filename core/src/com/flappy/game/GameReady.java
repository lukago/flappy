package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Class for handling GameReady state
 */

public class GameReady {
    private final String[] INFO_STR = {"TAP TO START"};
    private Text[] text;
    private float totalTime;
    
    /**
     * GameReady class constructor.
     * @param fontSize size in pixels
     */
    public GameReady(int fontSize) {
        text = new Text[INFO_STR.length];
        for (int i=0; i < text.length; i++) {
            text[i] = new Text("fonts/college.otf", fontSize, 3, INFO_STR[i], 
                    FlappyGame.window.x / 2, FlappyGame.window.y - 200*(i+1));
        } 
    }
    
    public GameReady() {
        this(40);
    }
    
    /**
     * Checks user input to continue.
     * @return boolean true if tapped.
     */
    public boolean toContinue() {
        if (Gdx.input.justTouched()) {
            return true;
        }
        return false;
    }
    
    public void updateGameReadyMenu(float dt, Bird bird) {
        if (bird.getBirdShape().y <= FlappyGame.window.y / 2 - 100) {
            bird.birdUp(50, 700);
        }
        
        totalTime += dt;
        if (totalTime < 0.5) {
            text[0].setFontColor(Color.GOLD);
        }
        else if (totalTime < 1.0) {    
            text[0].setFontColor(Color.WHITE);
        }
        else {
            totalTime = 0;
        }
    }
    
    public void drawGameReadyMenu(SpriteBatch batch) {     
        for (Text itext : text) {
            itext.drawTextCentered(batch);
        }
    }
    
    /**
     * Called when object gets removed.
     */
    public void disposeGameReady() {
        for (Text itext : text) {
            itext.disposeText();
        }
    }
    
    /**
     * Called when starting new game.
     */
    public void resetGameReady() {
        totalTime = 0;
    }
}
