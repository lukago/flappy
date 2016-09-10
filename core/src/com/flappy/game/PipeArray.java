package com.flappy.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * this class handle array of Pipe class 
 * array is used to draw more than one pipe on the screen
 * also handles game screen crossing 
 */

public class PipeArray {
	// array of pipes
	private Pipe[] pipes;

	// distance between two pipes
	private int distance;
	
	public PipeArray(int numOfPipes, int distance) {
		this.distance = distance;
		
		// pipes array size initialize
		pipes = new Pipe[numOfPipes];
		
		// for each pipe initialization
		for (int i=0; i < pipes.length; i++) {
			pipes[i] = new Pipe();
			pipes[i].setXPos(pipes[i].getPipeLow().x + i*distance);
		}
	}
	
	/**
	 * Updates pipes
	 * @param dt DeltaTime
	 */
	public void updatePipesArr(float dt) 
	{
		for(int i=0; i<pipes.length; i++) {
			pipes[i].updatePipes(dt);
		}
        
        // pipes crossing game window border handling
		// it depends on previous member of an array
		// allows to get equal distance between members
        for(int i=0; i<pipes.length; i++) {
        	if (pipes[i].getPipeLow().x < -pipes[i].getPipeLow().width ) {
        		pipes[i].setPipes();
            	pipes[i].setXPos(pipes[myFloorMod(i-1, pipes.length)].getPipeLow().x+distance);
        	}
        }
	}
	
	/**
	 * Render pipes
	 * @param batch @see SpriteBatch
	 */
	public void drawPipesArr(SpriteBatch batch)
	{
		for(int i=0; i<pipes.length; i++) {
			pipes[i].drawPipes(batch);
		}
	}
	
	/**
	 * Called when pipes get removed
	 */
	public void disposePipesArr() {
		for(int i=0; i<pipes.length; i++) {
			pipes[i].disposePipes();
		}		
	}
	
	// utility method
	private int myFloorMod(int num, int mod) {
		return ( ( num%mod ) + mod )%mod;
	}
	
	// getters & setters
	public Pipe[] getPipes() {
		return pipes;
	}
}
