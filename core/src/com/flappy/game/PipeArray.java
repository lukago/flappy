package com.flappy.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This class handle array of Pipe objects.
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
		pipes = new Pipe[numOfPipes];
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
		for(Pipe pipe : pipes) {
			pipe.updatePipes(dt);
		}
        
        /*
         * pipes crossing game window border handling
         * gets equal distance between members
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
		for(Pipe pipe : pipes) {
			pipe.drawPipes(batch);
		}
	}
	
	/**
	 * Called when PipesArray gets removed.
	 */
	public void disposePipesArr() {
		for(Pipe pipe : pipes) {
			pipe.disposePipes();
		}		
	}
	
    /*
     * Called when starting new game.
     */
	public void resetPipesArr() {
	    for (int i=0; i < pipes.length; i++) {
	        pipes[i].setPipes();
            pipes[i].setXPos(pipes[i].getPipeLow().x + i*distance);
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
	
	/* getters & setters */
	public Pipe[] getPipes() {
		return pipes;
	}
}
