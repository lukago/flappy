package com.flappy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private Bird bird;
	private Collision collision;
	
	// array of pipes
	// set size here to number of displayed pipes
	private Pipe[] pipes = new Pipe[2];
	
	// distance between two pipes
	private int distance;
	
	// variables for time handling
	static final float DT = 1/30.0f;
	
	@Override
	public void create () {
		/**
		 * initialization of all objects and variables
		 */
		collision = new Collision();
		batch = new SpriteBatch();
		bird = new Bird();
		distance = 400;
		for (int i=0; i < pipes.length; i++) {
			pipes[i] = new Pipe();
			pipes[i].setXPos(pipes[i].getPipeLow().x + i*distance);
		}
	}

	@Override
	public void render () {
		/**
		 * time handling and update section
		 */
	    
		float deltaTimeSeconds = Math.min(Gdx.graphics.getDeltaTime(), DT);
		update(deltaTimeSeconds);
		
		/**
		 * draw section
		 */
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		bird.drawBird(batch);
		for(int i=0; i<pipes.length; i++) {
			pipes[i].drawPipes(batch);
		}
		batch.end();
	}
	
	public void update(float deltaTimeSeconds) {
		/*
		 * update utility method
		 * use in render() method before drawing
		 */
		bird.updateBird(deltaTimeSeconds);
		
		for(int i=0; i<pipes.length; i++) {
			pipes[i].updatePipes(deltaTimeSeconds);
		}
        
        //pipes crossing game window border handling
        for(int i=0; i<pipes.length; i++) {
        	if (pipes[i].getPipeLow().x < -pipes[i].getPipeLow().width ) {
        		pipes[i].setPipes();
            	pipes[i].setXPos(pipes[myFloorMod(i-1, pipes.length)].getPipeLow().x+distance);
        	}
        }
        
        //collision temporary behavior 
        if ( collision.isbirdPipeCollision(bird, pipes) ) {
        	this.create();
        }
        
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		bird.disposeBird();
		for(int i=0; i<pipes.length; i++) {
			pipes[i].disposePipes();
		}
	}
	
	public int myFloorMod(int num, int mod) {
		return ( ( num%mod ) + mod )%mod;
	}
}


