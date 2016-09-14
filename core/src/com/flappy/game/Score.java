package com.flappy.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Score drawing, updating and handling
 */

public class Score {
    private Text text;
    private int score;
    private Collision collision;
    private Vector2 position;

    /**
     * Class constructor
     * @param fontSize size of score text in pixels
     * @param numOfPipes pass here how many pipes are in Pipes array.
     */
    public Score(Pipe[] pipes, int fontSize) {
        collision = new Collision(pipes);
        position = new Vector2(Game.window.x/2, Game.window.y-100);
        score = 0;
        text = new Text("college.otf", fontSize, 5, Integer.toString(score), position.x, position.y); 
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
            score++;
            text.setText(Integer.toString(score));
        }       
    }
    
    /**
     * Called when Score object gets removed.
     */
    public void disposeScore() {
        text.disposeText();
    }
    
}
