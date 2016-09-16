package com.flappy.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Score drawing, updating and handling.
 */

public class Score {
    final String SCORE_FILE_PATH = "score";
    
    private Text text;
    private int score;
    private int bestScore;
    private Collision collision;
    private Vector2 position;
    private boolean newBestScore;

    /**
     * Class constructor
     * @param fontSize size of score text in pixels
     * @param numOfPipes pass here how many pipes are in Pipes array.
     */
    public Score(Pipe[] pipes, int fontSize) {
        collision = new Collision(pipes);
        position = new Vector2(FlappyGame.window.x/2, FlappyGame.window.y-100);
        score = 0;
        bestScore = 0;
        newBestScore = false;
        text = new Text("college.otf", fontSize, 5, Integer.toString(score), position.x, position.y);
        readBestScore();
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
    
    /**
     * Reads bestScore from file.
     */
    public void readBestScore() {
        BufferedReader inputStream = null;
        
        try {
            inputStream = new BufferedReader(new FileReader(SCORE_FILE_PATH));
            String text;

            if ((text = inputStream.readLine()) != null) {
                bestScore = Integer.parseInt(text);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Sets bestScore and saves it to file.
     */
    public void setBestScore() {
        PrintWriter outputStream = null;
        
        try {
            outputStream = new PrintWriter(new FileWriter(SCORE_FILE_PATH));

            if (score > bestScore) {
                newBestScore = true;
                bestScore = score;
                outputStream.println(bestScore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
    
    /* setters & getters */
    public int getBestScore() {
        return bestScore;
    }
    
    public boolean isNewBestScore() {
        return newBestScore;
    }
    
}
