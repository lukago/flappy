package com.flappy.game;

import com.badlogic.gdx.math.Intersector;

public class Collision {

	public boolean isbirdPipeCollision(Bird bird, Pipe[] pipes)
	{
		for (int i=0; i<pipes.length; i++) {
			if ( Intersector.overlaps( bird.getBirdShape(), pipes[i].getPipeLow() ))
				return true;
			if ( Intersector.overlaps( bird.getBirdShape(), pipes[i].getPipeUp() ))
				return true;
			}		
		return false;
	}
}
