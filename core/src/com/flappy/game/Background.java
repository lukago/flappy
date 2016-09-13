package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
    private Texture bgTexture;
    private float scroll;
    private float scrollSpeed;

    /**
     * Class constructor
     * @param imgSrc path to backroung image file
     * @param scrollSpeed how fast background is scrolling
     * @param (optional) scroll initial scroll value
     */
    public Background(String imgSrc, float scrollSpeed, float scroll) {
        bgTexture = new Texture(Gdx.files.internal(imgSrc));
        bgTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        this.scrollSpeed = scrollSpeed;
        this.scroll = scroll;
    }
    
    public Background(String imgSrc, float scrollSpeed) {
        this(imgSrc, scrollSpeed, 0);
    }

    public void updateBg(float dt) {
        scroll += scrollSpeed * dt;
    }

    public void drawBg(SpriteBatch batch) {
        batch.draw(bgTexture, 0, 0, (int) scroll, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void disposeBg() {
        bgTexture.dispose();
    }
}
