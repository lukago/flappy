package com.flappy.game;

import com.badlogic.gdx.math.Intersector;

/**
 * This class is used to check collision between objects.
 * It does not handle collisions behavior. 
 */

public class Collision {

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
}
