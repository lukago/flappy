package com.flappy.game;

import com.badlogic.gdx.math.Intersector;

public class Collision {

	public boolean isbirdCollision(Bird bird, Pipe[] pipes) {
		for (int i = 0; i < pipes.length; i++) {
			if(Intersector.overlaps(bird.getBirdShape(), pipes[i].getPipeLow()) || 
					Intersector.overlaps( bird.getBirdShape(), pipes[i].getPipeUp()))
				return true;
			}	

		if (bird.getBirdShape().y < bird.getBirdShape().radius) {
			return true;
		}
		return false;
	}
}