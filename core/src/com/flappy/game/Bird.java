package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

public class Bird {
	
	// bird representation
	 private Texture birdTexture;
	 private Circle birdShape;

	// movement
	private float velocity;
	private float gravity;
	private float lift;
	
	public Bird() {
		
		birdShape = new Circle();
		birdShape.x = Game.window.x/4;
		birdShape.y = Game.window.y/2;
		birdShape.radius = 15;
		
		velocity = 0f;
		gravity = -30f;
		lift = 600f;
		
		birdTexture = new Texture("bird.png");	
	}
	
	public void drawBird(SpriteBatch sprbatch) {
		sprbatch.draw(birdTexture, birdShape.x - birdShape.radius, birdShape.y - birdShape.radius);
	}
	
	public void updateBird(float deltaTimeSeconds) {	
		velocity += gravity;
		birdShape.y += velocity*deltaTimeSeconds;	
		
		// crossing game window handling
		if ( birdShape.y > Game.window.y - 2*birdShape.radius) {
			velocity = 0;
			birdShape.y = Game.window.y - 2*birdShape.radius;
		} 
		if ( birdShape.y < birdShape.radius ) {
			velocity = 0;
			birdShape.y = birdShape.radius;
		}
		
		// input handling 
		if( Gdx.input.justTouched() ) {
				velocity = lift;			
			}	
	}
	
	public void disposeBird() {
		birdTexture.dispose();
	}
	
	 public Circle getBirdShape() {
		return birdShape;
	}

}

