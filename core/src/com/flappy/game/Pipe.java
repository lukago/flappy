package com.flappy.game;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

/**
 * This class represents single Pipe including upper and lower pipe 
 * on the same X position.
 */

public class Pipe {
    private Sprite pipeUpSprite;
    private Sprite pipeLowSprite;
    private TextureRegion pipeTextureReg;
    private Texture pipeSheet;
    private Rectangle pipeUp;
    private Rectangle pipeLow;
    private ShapeRenderer borderRenderer;

    private int gapHeight;
    private int speed;
    private int pipeAnimSpeed;
    private float startPos;

    private Random generator;

    /**
     * Pipe class constructor.
     * @param gapHeight height of gap between pipes pair
     * @param speed speed of pipes
     * @param pipeAnimSpeed speed of pipe animation
     */
    public Pipe(int gapHeight, int speed, int pipeAnimSpeed) {  
        this.gapHeight = gapHeight;
        this.speed = speed;
        this.pipeAnimSpeed = pipeAnimSpeed;
        startPos = FlappyGame.window.x + 200;
        
        generator = new Random();
        pipeSheet = new Texture(Gdx.files.internal("textures/pipes.png"));
        pipeSheet.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        pipeUpSprite = new Sprite();
        pipeLowSprite = new Sprite();
        pipeTextureReg = new TextureRegion(pipeSheet);
        pipeUpSprite.setRegion(pipeTextureReg);
        pipeLowSprite.setRegion(pipeTextureReg);

        pipeUp = new Rectangle();
        pipeLow = new Rectangle();
        setPipes();
        
        borderRenderer = new ShapeRenderer();
        Gdx.gl20.glLineWidth(5);
        borderRenderer.setColor(Color.BLACK);           
    }
    
    public Pipe() {
        this(200, 200, 1000);
    }
    
    /**
     * Gets random pipe height value.
     * @return float random pipe height 
     */
    public float getRandHeight() {
        return generator.nextFloat() * (FlappyGame.window.y - (gapHeight+200)) + 100;
    }
    
    /**
     * Sets up pipes data. It does not include 
     * loading textures or creating new objects.
     */
    public void setPipes() {    
        pipeLow.width = 100;
        pipeLow.height = getRandHeight();
        pipeLow.x = startPos;
        pipeLow.y = 0;

        pipeUp.width = pipeLow.width;
        pipeUp.height = FlappyGame.window.y - pipeLow.height - gapHeight;
        pipeUp.x = startPos;
        pipeUp.y = pipeLow.height + gapHeight;
        
        pipeTextureReg.setRegion(0, 0, (int) pipeLow.width, (int) pipeLow.height);
        pipeLowSprite.setRegion(pipeTextureReg);
        pipeTextureReg.setRegion(0, 0, (int) pipeUp.width, (int) pipeUp.height);
        pipeUpSprite.setRegion(pipeTextureReg);

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
        drawBorders(batch);  
    }
    
    /**
     * Draws black borders of pipes
     * @param batch pass here batch of main render method
     * @see SpriteBatch
     */
    public void drawBorders(SpriteBatch batch) { 
        batch.end();
        borderRenderer.begin(ShapeType.Line);      
        borderRenderer.rect(pipeLow.x, pipeLow.y-10, pipeLow.width, pipeLow.height+10);
        borderRenderer.rect(pipeUp.x, pipeUp.y, pipeUp.width, pipeUp.height+10);
        borderRenderer.end();
        batch.begin();
    }
    
    /**
     * Updates pipes animation.
     * @param dt delta time since previous update
     */
    public void pipeAnim(float dt) {
        //TODO: optymize 
        if (pipeLow.x >= FlappyGame.window.x && pipeLow.y == 0) {
            pipeLow.y -= pipeLow.height;
        }
        
        if (pipeUp.x >= FlappyGame.window.x && pipeUp.y == pipeLow.height + gapHeight) {
            pipeUp.y += pipeUp.height;
        }   
        
        if (pipeLow.y < 0 && pipeLow.x < FlappyGame.window.x ) {
            pipeLow.y += pipeAnimSpeed * dt;
        }
        
        if (pipeLow.y > 0) {
            pipeLow.y = 0;
        }
        
        if (pipeUp.y > pipeLow.height + gapHeight && pipeUp.x < FlappyGame.window.x ) {
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
        pipeSheet.dispose();
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
