package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

public class Bird {
	
	// bird representation
	private Circle birdShape;

	// movement
	private float velocity;
	private float gravity;
	private float lift;
	private float liftRotation;
	private float fallRotation;
	
	//animations
	private static final int FRAME_COLS = 3;
	private static final int FRAME_ROWS = 1;
	private Animation flyAnimation;
	private Texture birdSheet;
	private TextureRegion[] birdFrames;
	private TextureRegion currentFrame;
	
	float stateTime;
	float rotation;
	
	public Bird() {
		birdShape = new Circle();
		birdShape.x = Game.window.x/4;
		birdShape.y = Game.window.y/2;
		birdShape.radius = 14;
		
		velocity 		= 0f;
		gravity 		= -30f;
		lift 			= 600f;
		liftRotation 	= 50f;
		fallRotation 	= 1.5f;
		rotation 		= 0;
        stateTime 		= 0;
		
		// textures and animation variables initializaton 
		birdSheet = new Texture(Gdx.files.internal("bird.png"));
		birdFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		
		loadBirdFrames();
        
        // set each animation time here
        flyAnimation = new Animation(0.1f, birdFrames);
        currentFrame = flyAnimation.getKeyFrame(stateTime, true);
	}
	
	/**
	 * Bird's render method
	 * @param batch @see SpriteBatch
	 */
	public void drawBird(SpriteBatch batch) {
		batch.draw(currentFrame, birdShape.x - birdShape.radius, birdShape.y - birdShape.radius, 
				birdShape.radius, birdShape.radius, 2*birdShape.radius, 2*birdShape.radius, 1, 1, rotation);
	}
	
	/**
	 * Updates the bird logic
	 * @param dt DeltaTime - Time span between the current frame and the last frame in seconds
	 */
	public void updateBird(float dt) {	
		velocity += gravity;
		birdShape.y += velocity*dt;
		
		if (rotation > -80 ) {
			rotation -= fallRotation;
		}
		
		// choosing the aniamtion frame depending of elapsed time
		stateTime += dt;
		currentFrame = flyAnimation.getKeyFrame(stateTime, true);
		
		// crossing game window handling
		if ( birdShape.y > Game.window.y - 2*birdShape.radius) {
			velocity = 0;
			birdShape.y = Game.window.y - 2*birdShape.radius;
		} 
		
		// input handling 
		if( Gdx.input.justTouched() ) {
				rotation = liftRotation;
				velocity = lift;			
			}	
	}
	
	/**
	 * Called when bird gets removed
	 */
	public void disposeBird() {
		birdSheet.dispose();
	}
	
	
	/**
	 * Loads bird frames for the bird animation
	 */
	private void loadBirdFrames() {
		// temporary array of arrays to use TextureRegion.split() method
		TextureRegion[][] tmp = TextureRegion.split(birdSheet, birdSheet.getWidth()/FRAME_COLS, birdSheet.getHeight()/FRAME_ROWS);
		
		// initialization of birdFrames using tmp[][]
		int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                birdFrames[index++] = tmp[i][j];
            }
        }
	}
	
	 public Circle getBirdShape() {
		return birdShape;
	}

}

