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
    private SpriteBatch batch;
    private Bird bird;
    private Collision collision;
    private Score score;

    // game window size
    static Vector2 window;

    // array of pipes
    // set size here to number of displayed pipes
    private PipeArray pipes;

    // variables for time handling
    static final float DT = 1 / 30.0f;
    
    /**
     * (non-Javadoc)
     * @see com.badlogic.gdx.ApplicationAdapter#create()
     */
    @Override
    public void create() {
        window = new Vector2();
        window.x = Gdx.graphics.getWidth();
        window.y = Gdx.graphics.getHeight();

        collision = new Collision();
        batch = new SpriteBatch();
        bird = new Bird();
        pipes = new PipeArray(2, 400);
        score = new Score(65, 2);
    }
    
    /**
     * (non-Javadoc)
     * @see com.badlogic.gdx.ApplicationAdapter#render()
     */
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
        bird.drawBird(batch);
        pipes.drawPipesArr(batch);
        score.drawScore(batch);
        batch.end();
    }
    

    /**
     * update utility method use in render() method before drawing
     * @param deltaTimeSeconds time elapsed since last update (frame)
     */
    public void update(float deltaTimeSeconds) { 
        bird.updateBird(deltaTimeSeconds);
        pipes.updatePipesArr(deltaTimeSeconds);
        score.updateScore(pipes.getPipes(), bird);

        /*
         * COLLISION AND GAME STATE UPDATE SECTION temporary behavior 
         * TODO: menu game over summary
         */
        if (collision.isbirdCollision(bird, pipes.getPipes())) {
            this.create();
        }
    }
    
    /**
     * Removing objects.
     */
    @Override
    public void dispose() {
        batch.dispose();
        bird.disposeBird();
        pipes.disposePipesArr();
        score.disposeScore();
    }
}
