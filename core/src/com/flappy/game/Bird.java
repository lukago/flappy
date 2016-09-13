package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

/**
 * Bird logic, animation, update and render class.
 */

public class Bird {

    // bird representation
    private Circle birdShape;

    // movement
    private float velocity;
    private float gravity;
    private float lift;
    private float liftRotation;
    private float fallRotation;
    float rotation;
    float stateTime;
 
    // animations
    private static final int FRAME_COLS = 3;
    private static final int FRAME_ROWS = 1;
    private Animation flyAnimation;
    private Texture birdSheet;
    private TextureRegion[] birdFrames;
    private TextureRegion currentFrame;

    /**
     * Class constructor
     * @param birdImgSrc path to bird texture
     * @param gravity strenght of gravity
     * @param lift strength of lift
     * @param liftRotation rotation after lift
     * @param fallRotation rotation per update
     */
    public Bird(String birdImgSrc, float gravity, float lift, float liftRotation, float fallRotation) {
        birdShape = new Circle();
        birdShape.x = Game.window.x / 4;
        birdShape.y = Game.window.y / 2;
        birdShape.radius = 14;
      
        this.gravity = -gravity;
        this.lift = lift;
        this.liftRotation = liftRotation;
        this.fallRotation = fallRotation;
        
        velocity = 0f;
        rotation = 0f;
        stateTime = 0f;

        // textures and animation variables initializaton
        birdSheet = new Texture(Gdx.files.internal("bird.png"));
        birdFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        loadBirdFrames();

        // set each animation time here
        flyAnimation = new Animation(0.1f, birdFrames);
        currentFrame = flyAnimation.getKeyFrame(stateTime, true);
    }

    public Bird() {
        this("bird.png", 30f, 700f, 50f, 1.5f);
    }

    /**
     * Draws bird on the screen.
     * 
     * @param batch
     *            pass here batch of main render method
     * @see SpriteBatch
     */
    public void drawBird(SpriteBatch batch) {
        batch.draw(currentFrame, birdShape.x - birdShape.radius, birdShape.y - birdShape.radius, birdShape.radius,
                birdShape.radius, 2 * birdShape.radius, 2 * birdShape.radius, 1, 1, rotation);
    }

    /**
     * Updates bird data.
     * 
     * @param dt
     *            delta time since previous update
     */
    public void updateBird(float dt) {
        velocity += gravity;
        birdShape.y += velocity * dt;

        if (rotation > -80) {
            rotation -= fallRotation;
        }

        // choosing the aniamtion frame depending of elapsed time
        stateTime += dt;
        currentFrame = flyAnimation.getKeyFrame(stateTime, true);

        // crossing game window handling
        if (birdShape.y > Game.window.y - 2 * birdShape.radius) {
            velocity = 0;
            birdShape.y = Game.window.y - 2 * birdShape.radius;
        }
    }

    /**
     * Input handling.
     */
    public void handleBirdInput() {
        if (Gdx.input.justTouched()) {
            rotation = liftRotation;
            velocity = lift;
        }
    }

    /**
     * Called when Bird object gets removed.
     */
    public void disposeBird() {
        birdSheet.dispose();
    }

    /**
     * Sets up birdFrames from birdSheet.
     */
    private void loadBirdFrames() {
        // temporary array of arrays to use TextureRegion.split() method
        TextureRegion[][] tmp = TextureRegion.split(birdSheet, birdSheet.getWidth() / FRAME_COLS,
                birdSheet.getHeight() / FRAME_ROWS);

        // initialization of birdFrames using tmp[][]
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                birdFrames[index++] = tmp[i][j];
            }
        }
    }

    // setters & getters
    public Circle getBirdShape() {
        return birdShape;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

}
