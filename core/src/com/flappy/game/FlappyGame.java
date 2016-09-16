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

public class FlappyGame extends ApplicationAdapter {
    
    public enum GameState {
        GAME_READY,
        GAME_RUNNING,
        GAME_OVER
    }
    
    private GameState state;
    private GameReady gameReady;
    private GameOver gameOver;
    
    private int numOfPipes;
    private SpriteBatch batch;
    private Bird bird;
    private Collision collision;
    private Score score;
    private Background background;
    
    /* game window size */
    static Vector2 window;

    /* array of pipes */
    private PipeArray pipesArr;

    /* variables for time handling */
    static final float DT = 1 / 30.0f;
    
    @Override
    public void create() {
        numOfPipes = 2;
        state = GameState.GAME_READY;
        window = new Vector2();
        window.x = Gdx.graphics.getWidth();
        window.y = Gdx.graphics.getHeight();
        
        batch       = new SpriteBatch();
        pipesArr    = new PipeArray(numOfPipes, 350);    
        bird        = new Bird("textures/bird.png", 30f, 700f, 50f, 1.5f);
        collision   = new Collision(pipesArr.getPipes());
        score       = new Score(pipesArr.getPipes(), 65);   
        background  = new Background("textures/bg.jpg", 140);
        gameOver    = new GameOver(40);
        gameReady   = new GameReady(40);
    }
    
    @Override
    public void render() {
        /* UPDATE SECTION */
        float deltaTimeSeconds = Math.min(Gdx.graphics.getDeltaTime(), DT);
        update(deltaTimeSeconds);
        
        /* DRAW SECTION */
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
            updateReady(deltaTimeSeconds);
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
            presentReady(batch);
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
        gameOver.disposeGameOver();
        gameReady.disposeGameReady();
    }

    public void updateRunning(float dt) {
        background.updateBg(dt);
        bird.updateBird(dt);
        bird.handleBirdInput();
        pipesArr.updatePipesArr(dt);
        score.updateScore(bird, pipesArr.getPipes());
        
        /* SWITCHING STATE */
        if (collision.isbirdCollision(bird, pipesArr.getPipes())) {
            gameOver.initGameOver(score, bird);
            state = GameState.GAME_OVER;
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
        gameOver.updateGameOverMenu(dt);

        /* SWITCHING STATE */
        if (gameOver.toContinue(bird) ) {
            resetGame();
            state = GameState.GAME_READY;
        }
    }
    
    public void presentGameOver(SpriteBatch batch) {
        background.drawBg(batch);
        pipesArr.drawPipesArr(batch);
        bird.drawBird(batch);
        gameOver.drawGameOverMenu(batch);
    }
    
    public void updateReady(float dt) {
        background.updateBg(dt);
        bird.updateBird(dt);
        gameReady.updateGameReadyMenu(dt, bird);
        
        /* SWITCHING STATE */
        if (gameReady.toContinue() ) {
            bird.birdUp();
            state = GameState.GAME_RUNNING;
        }
    }
    
    public void presentReady(SpriteBatch batch) {
        background.drawBg(batch);
        bird.drawBird(batch);
        gameReady.drawGameReadyMenu(batch);
    }
    
    /**
     * Called when starting a new game to reset some objects data to default.
     */
    public void resetGame() {
        bird.resetBird();
        score.resetScore();
        pipesArr.resetPipesArr();
        gameReady.resetGameReady();
        gameOver.resetGameOver();
    }
    
}