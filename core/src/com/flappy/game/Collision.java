package com.flappy.game;

import com.badlogic.gdx.math.Intersector;

public class Collision {
	
	/**
	 * Detects collision between a bird and pipes
	 * @param bird Bird to check collision with
	 * @param pipes Pipes to check for collision
	 * @return boolean Is bird colliding with a pipe
	 */
	public boolean isbirdCollision(Bird bird, Pipe[] pipes)
	{
		for (int i=0; i<pipes.length; i++) {
			if ( Intersector.overlaps( bird.getBirdShape(), pipes[i].getPipeLow() ))
				return true;
			if ( Intersector.overlaps( bird.getBirdShape(), pipes[i].getPipeUp() ))
				return true;
			}		

		if ( bird.getBirdShape().y < bird.getBirdShape().radius) {
			return true;
		}
		
		return false;
	}
}
