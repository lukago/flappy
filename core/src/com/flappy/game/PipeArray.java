package com.flappy.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This class handle array of Pipe objects.
 * This is used to draw more than one pipe on the screen.
 * Also handles game screen crossing.
 * @see Pipe
 */

public class PipeArray {
	private Pipe[] pipes;
	private int distance;
	
	/**
	 * Class constructor
	 * @param numOfPipes how many pipes put into an array
	 * @param distance distance between two pipes
	 */
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
	 * Upadtes all pipes. 
	 * @param dt delta time since previous update
	 */
	public void updatePipesArr(float dt) 
	{
		for(int i=0; i<pipes.length; i++) {
			pipes[i].updatePipes(dt);
		}
        
        /*
         * pipes crossing game window border handling
         * it depends on previous member of an array
         * allows to get equal distance between members
         */
        for(int i=0; i<pipes.length; i++) {
        	if (pipes[i].getPipeLow().x < -pipes[i].getPipeLow().width ) {
        		pipes[i].setPipes();
            	pipes[i].setXPos(pipes[myFloorMod(i-1, pipes.length)].getPipeLow().x+distance);
        	}
        }
	}
	
	/**
	 * Drawing all pipes of an array on the screen. 
	 * @param batch pass here batch of main render method
     * @see SpriteBatch
	 */
	public void drawPipesArr(SpriteBatch batch)
	{
		for(int i=0; i<pipes.length; i++) {
			pipes[i].drawPipes(batch);
		}
	}
	
	/**
	 * Called when PipesArray gets removed.
	 */
	public void disposePipesArr() {
		for(int i=0; i<pipes.length; i++) {
			pipes[i].disposePipes();
		}		
	}
	
	/**
	 * utility method calculating floor mod
	 * @param num number
	 * @param mod modulo
	 * @return integer floor mod form num%mod
	 */
	private int myFloorMod(int num, int mod) {
		return ( ( num%mod ) + mod )%mod;
	}
	
	// getters & setters
	public Pipe[] getPipes() {
		return pipes;
	}
}
