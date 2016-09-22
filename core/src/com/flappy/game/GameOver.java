package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Class for handling GameOver state
 */

public class GameOver {
    private final String[] INFO_STR = {"GAME OVER!", "SCORE: ", "BEST SCORE: ", "TAP TO CONTINUE" };
    private Text[] text;
    private Sound hitSound;
    private Sound dieSound;
    
    private boolean birdDown;
    float totalTime;
    
    /**
     * GameOver class constructor.
     * @param fontSize font size in pixels
     */
    public GameOver(int fontSize) {     
        birdDown = false; 
        hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.ogg"));
        dieSound = Gdx.audio.newSound(Gdx.files.internal("sounds/die.ogg"));
        
        text = new Text[INFO_STR.length];
        for (int i=0; i < text.length; i++) {
            text[i] = new Text("fonts/college.otf", fontSize, 3, INFO_STR[i], 
                    FlappyGame.window.x / 2, FlappyGame.window.y - 100*(i+1));
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
        /* sounds */
        hitSound.play(1.0f);      
        if (bird.getBirdShape().y > bird.getBirdShape().radius) {
            dieSound.play(1.0f);  
        }
        
        /* display */
        bird.setVelocity(0);
        score.setBestScore();
        
        /* modify texts here */
        text[1].setText(INFO_STR[1]+score.getScore());
        text[2].setText(INFO_STR[2]+score.getBestScore());
        if(score.isNewBestScore()) {
            text[0].setText("NEW BEST SCORE!");
        } 
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
            text[3].setFontColor(Color.GOLD);
        }
        else if (totalTime < 1.0) {
            text[3].setFontColor(Color.WHITE);
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
            for (Text itext : text) {
                itext.drawTextCentered(batch);
            }
        }
    }
    
    /**
     * Called when object gets removed.
     */
    public void disposeGameOver() {
        for (Text itext : text) {
            itext.disposeText();
        }
        hitSound.dispose();
        dieSound.dispose();
    }
    
    /**
     * Called when starting new game.
     */
    public void resetGameOver() {
        birdDown = false;
        totalTime = 0;
    }
    
}
