package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Score drawing, updating and handling.
 */

public class Score {
    final String SCORE_FILE_PATH = "data/score";
    final String FONT_FILE_PATH = "fonts/college.otf";
    final String SOUND_FILE_PATH = "sounds/point.ogg";
    
    private Text text;
    private int score;
    private int bestScore;
    private Collision collision;
    private Vector2 position;
    private boolean newBestScore;
    private Sound pointSound;

    /**
     * Class constructor
     * @param fontSize size of score text in pixels
     * @param numOfPipes pass here how many pipes are in Pipes array.
     */
    public Score(Pipe[] pipes, int fontSize) {
        collision = new Collision(pipes);
        position = new Vector2(FlappyGame.window.x/2, FlappyGame.window.y-100);
        text = new Text(FONT_FILE_PATH, fontSize, 5, Integer.toString(score), position.x, position.y);
        pointSound =  Gdx.audio.newSound(Gdx.files.internal(SOUND_FILE_PATH));
    }

    /**
     * Draws score on the screen.
     * @param batch pass here batch of main render method
     * @see SpriteBatch
     */
    public void drawScore(SpriteBatch batch) {
        text.drawTextCentered(batch);
    }

    /**
     * Score incrementing
     * @param pipes each pipe needs to be checked 
     * @param bird to get bird position
     */
    public void updateScore(Bird bird, Pipe[] pipes) {    
        if (collision.isPipeCrossed(bird, pipes)) {
            pointSound.play(1.0f);
            score++;
            text.setText(Integer.toString(score));
        }       
    }
    
    /**
     * Called when Score object gets removed.
     */
    public void disposeScore() {
        text.disposeText();
        pointSound.dispose();
    }
    
    /**
     * Reads bestScore from file.
     */
    public void readBestScore() {
        FileHandle file = Gdx.files.local(SCORE_FILE_PATH);
        try {
            String str = file.readString();
            bestScore = Integer.parseInt(str);
        } catch (GdxRuntimeException e) {
            bestScore = 0;
            e.getStackTrace();
        } catch (NumberFormatException e) {
            bestScore = 0;
            e.getStackTrace();
        }
    }
    
    /**
     * Sets bestScore and saves it to file.
     */
    public void setBestScore() {
        readBestScore();
        FileHandle file = Gdx.files.local(SCORE_FILE_PATH);
        if (score > bestScore) {
            newBestScore = true;
            bestScore = score;  
        }
        try {
            file.writeString(Integer.toString(bestScore), false);  
        } catch (GdxRuntimeException e) {
            e.getStackTrace();
        }
    }
    
    public void resetScore() {
        score = 0;
        newBestScore = false;
        text.setText(Integer.toString(score));
    }
    
    /* setters & getters */
    public int getScore() {
        return score;
    }
    
    public int getBestScore() {
        return bestScore;
    }
    
    public boolean isNewBestScore() {
        return newBestScore;
    }
    
}
