package com.flappy.game;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * This class represents single Pipe including upper and lower pipe 
 * on the same X position.
 */

public class Pipe {
    private Sprite pipeUpSprite;
    private Sprite pipeLowSprite;
    private Texture pipeTexture;
    private Rectangle pipeUp;
    private Rectangle pipeLow;

    private int gapHeight;
    private int speed;
    private int pipeAnimSpeed;
    private float startPos;

    private Random generator;

    public Pipe() {
        generator = new Random();
        pipeTexture = new Texture(Gdx.files.internal("pipe.png"));

        pipeUpSprite = new Sprite();
        pipeLowSprite = new Sprite();
        pipeUpSprite.setTexture(pipeTexture);
        pipeLowSprite.setTexture(pipeTexture);

        pipeUp = new Rectangle();
        pipeLow = new Rectangle();
        setPipes();
    }
    
    /**
     * Gets random pipe height value.
     * @return float random pipe height 
     */
    public float getRandHeight() {
        return generator.nextFloat() * (Game.window.y - 400) + 200;
    }
    
    /**
     * Sets up pipes data. It does not include 
     * loading textures or creating new objects.
     */
    public void setPipes() {
        startPos = Game.window.x + 200;
        gapHeight = 180;
        speed = 200;
        pipeAnimSpeed = 1000;
        
        pipeLow.width = 80;
        pipeLow.height = getRandHeight();
        pipeLow.x = startPos;
        pipeLow.y = 0;

        pipeUp.width = pipeLow.width;
        pipeUp.height = Game.window.y - pipeLow.height - gapHeight;
        pipeUp.x = startPos;
        pipeUp.y = pipeLow.height + gapHeight;

        pipeLowSprite.setBounds(pipeLow.x, pipeLow.y, pipeLow.width, pipeLow.height);
        pipeUpSprite.setBounds(pipeUp.x, pipeUp.y, pipeUp.width, pipeUp.height);
    }

    /**
     * Updates pipes data
     * @param dt delta time since previous update
     */
    public void updatePipes(float dt) {
        pipeLow.x -= speed * dt;
        pipeUp.x = pipeLow.x;
        
        pipeAnim(dt);
        
        pipeLowSprite.setPosition(pipeLow.x, pipeLow.y);
        pipeUpSprite.setPosition(pipeUp.x, pipeUp.y);
    }

    /**
     * Draws pipes on the screen.
     * @param batch pass here batch of main render method
     * @see SpriteBatch
     */
    public void drawPipes(SpriteBatch batch) {
        pipeLowSprite.draw(batch);
        pipeUpSprite.draw(batch);
    }
    
    /**
     * Updates pipes animation.
     * @param dt delta time since previous update
     */
    public void pipeAnim(float dt) {
        if (pipeLow.x >= Game.window.x && pipeLow.y == 0) {
            pipeLow.y -= pipeLow.height;
        }
        
        if (pipeUp.x >= Game.window.x && pipeUp.y == pipeLow.height + gapHeight) {
            pipeUp.y += pipeUp.height;
        }   
        
        if (pipeLow.y < 0 && pipeLow.x < Game.window.x - pipeLow.width ) {
            pipeLow.y += pipeAnimSpeed * dt;
        }
        
        if (pipeLow.y > 0) {
            pipeLow.y = 0;
        }
        
        if (pipeUp.y > pipeLow.height + gapHeight && pipeUp.x < Game.window.x - pipeLow.width ) {
            pipeUp.y -= pipeAnimSpeed * dt;
        }
        
        if (pipeUp.y < pipeLow.height + gapHeight) {
            pipeUp.y = pipeLow.height + gapHeight;
        }
    }

    /**
     * Called when Pipe object get removed.
     */
    public void disposePipes() {
        pipeTexture.dispose();
    }

    // setters & getters
    public void setXPos(float x) {
        pipeUp.x = x;
        pipeLow.x = x;
    }

    public Rectangle getPipeUp() {
        return pipeUp;
    }

    public Rectangle getPipeLow() {
        return pipeLow;
    }
}
