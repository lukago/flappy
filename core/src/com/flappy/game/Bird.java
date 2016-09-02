package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;


public class Bird {
	
	// bird representation
	 private Texture birdTexture;
	 private Circle birdShape;
	 
	 // movement
	private float velocity;
	private float gravity;
	private float lift;
	
	// game window size
	private Vector2 window;	
	
	public Bird() {
		window = new Vector2();
		window.x = Gdx.graphics.getWidth();
		window.y = Gdx.graphics.getHeight();
		
		birdShape = new Circle();
		birdShape.x = window.x/4;
		birdShape.y = window.y/2;
		birdShape.radius = 15;
		
		velocity = 0f;
		gravity = -20f;
		lift = 500f;
		
		birdTexture = new Texture("bird.png");	
	}
	
	public void drawBird(SpriteBatch sprbatch) {
		sprbatch.draw(birdTexture, birdShape.x, birdShape.y);		
	}
	
	public void updateBird(float deltaTimeSeconds) {	
		velocity += gravity;
		birdShape.y += velocity*deltaTimeSeconds;	
		
		// crossing game window handling
		if ( birdShape.y > window.y - 2*birdShape.radius) {
			velocity = 0;
			birdShape.y = window.y - 2*birdShape.radius;
		} 
		if ( birdShape.y < 0 ) {
			velocity = 0;
			birdShape.y = 0;
		}
		
		// input handling 
		if( Gdx.input.justTouched() ) {
				velocity = lift;			
			}	
	}
	
	public void disposeBird() {
		birdTexture.dispose();
	}

}

