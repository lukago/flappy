package com.flappy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Flappy Bird game clone
 * 
 * @author https://github.com/glbwsk
 */

public class Game extends ApplicationAdapter {
    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_OVER = 2;
    private int state;

    private SpriteBatch batch;
    private Bird bird;
    private Collision collision;
    private Score score;
    private Background background;
    
    // game window size
    static Vector2 window;

    // array of pipes
    private PipeArray pipesArr;

    // variables for time handling
    static final float DT = 1 / 30.0f;
    
    @Override
    public void create() {
        state = 1;
        window = new Vector2();
        window.x = Gdx.graphics.getWidth();
        window.y = Gdx.graphics.getHeight();
        
        batch       = new SpriteBatch();
        pipesArr    = new PipeArray(2, 400);    
        bird        = new Bird("bird.png", 30f, 700f, 50f, 1.5f);
        collision   = new Collision(pipesArr.getPipes());
        score       = new Score(pipesArr.getPipes(), 65);   
        background  = new Background("bg.jpg", 140);         
    }
    
    @Override
    public void render() {
        /*
         * UPDATE SECTION
         */
        float deltaTimeSeconds = Math.min(Gdx.graphics.getDeltaTime(), DT);
        update(deltaTimeSeconds);
        
        /*
         * DRAW SECTION
         */
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        draw(batch);
        batch.end();
    }
    
    /**
     * Use in render() method before drawing.
     * @param deltaTimeSeconds time elapsed since last update (frame)
     */
    public void update(float deltaTimeSeconds) { 
        switch (state) {
        case GAME_READY:
            //updateReady(deltaTimeSeconds);
            break;
        case GAME_RUNNING:
            updateRunning(deltaTimeSeconds);
            break;
        case GAME_OVER:
            updateGameOver(deltaTimeSeconds);
            break;
        }
    }
    
    /**
     * Use in render() after updating.
     * @param batch pass here batch of main render method
     * @see SpriteBatch
     */
    public void draw (SpriteBatch batch) {
        switch (state) {
        case GAME_READY:
            //presentReady(batch);
            break;
        case GAME_RUNNING:
            presentRunning(batch);
            break;
        case GAME_OVER:
            presentGameOver(batch);
            break;
        }
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        bird.disposeBird();
        pipesArr.disposePipesArr();
        score.disposeScore();
    }

    public void updateRunning(float dt) {
        background.updateBg(dt);
        bird.updateBird(dt);
        bird.handleBirdInput();
        pipesArr.updatePipesArr(dt);
        score.updateScore(bird, pipesArr.getPipes());
        
        if (collision.isbirdCollision(bird, pipesArr.getPipes())) {
            bird.setVelocity(0);
            state = GAME_OVER;
        }
    }
    
    public void presentRunning(SpriteBatch bath) {
        background.drawBg(bath);
        pipesArr.drawPipesArr(batch);
        bird.drawBird(batch);
        score.drawScore(batch);
    }
    
    public void updateGameOver(float dt) {
        bird.updateBird(dt);       
        //TODO: menu
        if (bird.getBirdShape().y < -2*bird.getBirdShape().radius ) {
            this.create();
            state = GAME_RUNNING;
        }
    }
    
    public void presentGameOver(SpriteBatch batch) {
        background.drawBg(batch);
        pipesArr.drawPipesArr(batch);
        bird.drawBird(batch);
    }
    
    public void updateReady(float dt) {
        bird.updateBird(dt);
    }
    
    public void presentReady(SpriteBatch batch) {
        bird.drawBird(batch);
        pipesArr.drawPipesArr(batch);
    }
      
}