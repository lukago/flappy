package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Class for handling GameOver state
 */

public class GameOver {
    private final String[] INFO_STR = {"GAME OVER!", "BEST SCORE: ", "TAP TO CONTINUE" };
    private Text[] text;
    
    private boolean birdDown;
    float totalTime;
    
    /**
     * GameOver class constructor.
     * @param fontSize font size in pixels
     */
    public GameOver(int fontSize) {
        totalTime = 0;
        birdDown = false;
        text = new Text[INFO_STR.length];
        for (int i=0; i < text.length; i++) {
            text[i] = new Text("college.otf", fontSize, 3, INFO_STR[i], 
                    FlappyGame.window.x / 2, FlappyGame.window.y - 200*(i+1));
        } 
    }
    
    public GameOver() {
        this(40);
    }
    
    /**
     * Updates best score and info string.
     * Call once when switching state.
     */
    public void initGameOver(Score score, Bird bird) {
        bird.setVelocity(0);
        score.setBestScore();
        if(score.isNewBestScore()) {
            text[0].setText("NEW BEST SCORE!");
        }
        text[1].setText(INFO_STR[1]+score.getBestScore());
    }
    
    /**
     * Checks input to countinue.
     * @return boolean true if continue
     */
    public boolean toContinue(Bird bird) {
        if (bird.getBirdShape().y < -2*bird.getBirdShape().radius) {
            birdDown = true;
        }      
        if (Gdx.input.justTouched() && birdDown ) {
            return true;
        }
        return false;
    }
    
    /**
     * Menu animation.
     */
    public void updateGameOverMenu(float dt) {
        totalTime += dt;
        if (totalTime < 0.5) {
            text[2].setFontColor(Color.WHITE);
        }
        else if (totalTime < 1.0) {
            text[2].setFontColor(Color.GOLD);
        }
        else {
            totalTime = 0;
        }
    }
    
    /**
     * draws all texts if bird is below the game screen.
     */
    public void drawGameOverMenu(SpriteBatch batch) {     
        if (birdDown) {
            for (int i=0; i < text.length; i++) {
                text[i].drawTextCentered(batch);
            }
        }
    }
    
    /*
     * Called when object gets removed.
     */
    public void disposeGameOver() {
        for (int i=0; i < text.length; i++) {
            text[i].disposeText();
        }
    }
    
}
