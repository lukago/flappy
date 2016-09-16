package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
 * Background handling: scrolling, drawing etc.
 */

public class Background extends Texture {
    private float scroll;
    private float scrollSpeed;

    /**
     * Class constructor
     * @param imgSrc path to backroung image file
     * @param scrollSpeed how fast background is scrolling
     * @param (optional) scroll initial scroll value
     */
    public Background(String imgSrc, float scrollSpeed, float scroll) {
        super(imgSrc);
        setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
        this.scrollSpeed = scrollSpeed;
        this.scroll = scroll;
    }
    
    public Background(String imgSrc, float scrollSpeed) {
        this(imgSrc, scrollSpeed, 0);
    }

    /**
     * Scrolls background.
     * @param dt delta time in seconds
     */
    public void updateBg(float dt) {
        scroll += scrollSpeed * dt;
    }

    /**
     * Draws background texture.
     * @param batch SpriteBatch of main render method.
     * @see SpriteBatch
     */
    public void drawBg(SpriteBatch batch) {
        batch.draw(this, 0, 0, (int) scroll, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Called when backgorund texture gets removed.
     */
    public void disposeBg() {
        dispose();
    }
}
