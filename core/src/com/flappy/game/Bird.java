package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

/**
 * Bird logic, animation, update and render class.
 */

public class Bird {

    /* bird representation */
    private Circle birdShape;

    /* movement */
    private float velocity;
    private float gravity;
    private float lift;
    private float liftRotation;
    private float fallRotation;
    float rotation;
    float stateTime;

    /* animations */
    private static final int FRAME_COLS = 3;
    private static final int FRAME_ROWS = 1;
    private Animation flyAnimation;
    private Texture birdSheet;
    private TextureRegion[] birdFrames;
    private TextureRegion currentFrame;
    
    /* sounds */
    private Sound birdUpSound;

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
        birdShape.x = FlappyGame.window.x / 4;
        birdShape.y = FlappyGame.window.y / 2;
        birdShape.radius = 25;

        this.gravity = -gravity;
        this.lift = lift;
        this.liftRotation = liftRotation;
        this.fallRotation = fallRotation;

        birdSheet = new Texture(Gdx.files.internal(birdImgSrc));
        birdFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        birdUpSound =  Gdx.audio.newSound(Gdx.files.internal("sounds/wing.ogg"));

        /* set each animation time here */
        loadBirdFrames();
        flyAnimation = new Animation(0.1f, birdFrames);
        currentFrame = flyAnimation.getKeyFrame(stateTime, true);    
    }

    public Bird() {
        this("bird.png", 30f, 700f, 50f, 1.5f);
    }

    /**
     * Draws bird on the screen.
     * @param batch pass here batch of main render method
     * @see SpriteBatch
     */
    public void drawBird(SpriteBatch batch) {
        batch.draw(currentFrame, birdShape.x - birdShape.radius, birdShape.y - birdShape.radius, birdShape.radius,
                birdShape.radius, 2 * birdShape.radius, 2 * birdShape.radius, 1, 1, rotation);
    }

    /**
     * Updates bird data.
     * @param dt delta time since previous update
     */
    public void updateBird(float dt) {
        velocity += gravity;
        birdShape.y += velocity * dt;

        if (rotation > -80) {
            rotation -= fallRotation;
        }

        /* choosing the aniamtion frame depending of elapsed time */
        stateTime += dt;
        currentFrame = flyAnimation.getKeyFrame(stateTime, true);

        /* crossing game window handling */
        if (birdShape.y > FlappyGame.window.y - birdShape.radius) {
            velocity = 0;
            birdShape.y = FlappyGame.window.y - birdShape.radius;
        }
    }

    /**
     * Input handling.
     */
    public void handleBirdInput() {
        if (Gdx.input.justTouched()) {
            birdUp();
        }
    }

    /**
     * Called when Bird object gets removed.
     */
    public void disposeBird() {
        birdSheet.dispose();
        birdUpSound.dispose();
    }
    
    /*
     * Called when starting new game.
     */
    public void resetBird() {
        birdShape.x = FlappyGame.window.x / 4;
        birdShape.y = FlappyGame.window.y / 2;
        velocity = 0f;
        rotation = 0f;
        stateTime = 0f;
    }

    /**
     * Sets up birdFrames from birdSheet.
     */
    private void loadBirdFrames() {
        /* temporary array of arrays to use TextureRegion.split() method */
        TextureRegion[][] tmp = TextureRegion.split(birdSheet, birdSheet.getWidth() / FRAME_COLS,
                birdSheet.getHeight() / FRAME_ROWS);

        /* initialization of birdFrames using tmp[][] */
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                birdFrames[index++] = tmp[i][j];
            }
        }
    }
    
    /*
     * bird jump default handling
     */
    public void birdUp() {
        birdUpSound.play(1.0f);
        rotation = liftRotation;
        velocity = lift;
    }
    
    /*
     * bird jump different behaviour
     */
    public void birdUp(float rotation, float lift) {
        this.rotation = rotation;
        this.velocity = lift;
    }

    /* setters & getters */
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
