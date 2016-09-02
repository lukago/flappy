package com.flappy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private Bird bird;
	
	// array of pipes
	// set size here to number of displayed pipes
	private Pipe[] pipes = new Pipe[2];
	
	// distance between two pipes
	private int distance;
	
	// variables for time handling
	static final float DT = 1/60.0f;
	static final int MAX_UPDATES_PER_FRAME = 2; 
	private long currentTimeMillis;
	
	@Override
	public void create () {
		/**
		 * initialization of all objects and variables
		 */
		distance = 400;
		for (int i=0; i < pipes.length; i++) {
			pipes[i] = new Pipe();
			pipes[i].setXPos(pipes[i].getPipeLow().x + i*distance);
		}
		batch = new SpriteBatch();
		bird = new Bird();
		currentTimeMillis = System.currentTimeMillis();
	}

	@Override
	public void render () {
		/**
		 * time handling and update section
		 */
		int updateCount = 0;
		long newTimeMillis = System.currentTimeMillis();
	    float frameTimeSeconds = (newTimeMillis - currentTimeMillis) / 1000f;
	    currentTimeMillis = newTimeMillis;
	    
	    while (frameTimeSeconds > 0f && updateCount <= MAX_UPDATES_PER_FRAME) {   	
	        float deltaTimeSeconds = Math.min(frameTimeSeconds, DT);
	        update(deltaTimeSeconds);
	        frameTimeSeconds -= deltaTimeSeconds;
	        ++updateCount;
	    }
		
		/**
		 * draw section
		 */
		Gdx.gl.glClearColor(0, 0, 0, 1);
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
		 * use in render() method in time loop
		 */
		bird.updateBird(deltaTimeSeconds);
		
		for(int i=0; i<pipes.length; i++) {
			pipes[i].updatePipes(deltaTimeSeconds);
		}
        
        //pipes crossing border handling
        for(int i=0; i<pipes.length; i++) {
        	if (pipes[i].getPipeLow().x < -pipes[i].getPipeLow().width ) {
            	pipes[i].setXPos(pipes[Math.floorMod(i-1, pipes.length)].getPipeLow().x+distance);
        	}
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
}
