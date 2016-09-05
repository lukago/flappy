package com.flappy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * Flappy Bird game clone 
 * @author https://github.com/glbwsk
 */

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private Bird bird;
	private Collision collision;
	
	// array of pipes
	// set size here to number of displayed pipes
	private PipeArray pipes;
	
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
		pipes = new PipeArray(2, 400);
	}

	@Override
	public void render () {	
		/**
		 * UPDATE SECTION
		 */
		float deltaTimeSeconds = Math.min(Gdx.graphics.getDeltaTime(), DT);
		update(deltaTimeSeconds);
		
		/**
		 * DRAW SECTION
		 */
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		bird.drawBird(batch);
		pipes.drawPipesArr(batch);
		batch.end();
	}
	
	public void update(float deltaTimeSeconds) {
		/**
		 * update utility method
		 * use in render() method before drawing
		 */
		bird.updateBird(deltaTimeSeconds);
		pipes.updatePipesArr(deltaTimeSeconds);
        
        /**
         * COLLISION AND GAME STATE UPDATE SECTION
         * temporary behavior
         * TODO: menu / game over summary
         */
        if ( collision.isbirdPipeCollision(bird, pipes.getPipes()) ) {
        	this.create();
        }    
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		bird.disposeBird();
		pipes.disposePipesArr();
	}
}


