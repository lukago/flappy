package com.flappy.game;

import com.badlogic.gdx.math.Intersector;

/**
 * This class is used to check collision between objects.
 * It does not handle collisions behavior. 
 */

public class Collision {
    private boolean[] scoreFlags;
    
    /**
     * Class constructor
     * @param pipes to get legnth of pipes array
     */
    public Collision(Pipe[] pipes) {
        scoreFlags = new boolean[pipes.length];
    }
    
    /**
     * Checking if bird shape overlaps pipe shape.
     * @return boolean true if collision
     */
    public boolean isbirdCollision(Bird bird, Pipe[] pipes) {
        for (int i = 0; i < pipes.length; i++) {
            if (Intersector.overlaps(bird.getBirdShape(), pipes[i].getPipeLow()))
                return true;
            if (Intersector.overlaps(bird.getBirdShape(), pipes[i].getPipeUp()))
                return true;
        }
        
        if (bird.getBirdShape().y < 0 ) {
            return true;
        }   
        return false;
    }
    
    /**
     * Checking if bird corssed the pipe.
     * @return boolean true if crossed
     */
    public boolean isPipeCrossed(Bird bird, Pipe[] pipes) {
        setFlags(bird, pipes);
        for (int i = 0; i < pipes.length; i++) {
            if (!scoreFlags[i] && pipes[i].getPipeLow().x < bird.getBirdShape().x) {
                scoreFlags[i] = true;
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checking if pipe is already ahead the bird.
     */
    private void setFlags(Bird bird, Pipe[] pipes) {
        for (int i = 0; i < pipes.length; i++) {
            if (scoreFlags[i] && pipes[i].getPipeLow().x > bird.getBirdShape().x) {
                scoreFlags[i] = false;
            }
        }
    }
}
