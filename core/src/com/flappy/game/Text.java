package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;

/**
 * Class for text drawing, setting, displaying etc.
 */

public class Text {
    /*
     * FreeTypeFont is libGDX extension allows to use .ttf fonts and flexible
     * font size setting. GlyphLayout is to get text width.
     */
    private FreeTypeFontGenerator generator;
    private FreeTypeFontParameter parameter;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private String textStr;
    
    /* text position */
    private Vector2 position;
    
    /**
     * Text class constructor.
     * @param fontFile path to font file
     * @param fontSize size in pixels
     * @param fontBorderWidth width in pixels
     * @param textStr (optional) text string to display
     * @param x (optional) postition X coord
     * @param y (optional) position Y coord
     */
    public Text(String fontFile, int fontSize, int fontBorderWidth, String textStr, float x, float y) {
        position        = new Vector2(x, y);     
        generator       = new FreeTypeFontGenerator(Gdx.files.internal(fontFile));
        parameter       = new FreeTypeFontParameter();
        glyphLayout     = new GlyphLayout();
        this.textStr    = new String();
        this.textStr    = textStr;
        
        parameter.size = fontSize;
        parameter.borderWidth = fontBorderWidth;
        parameter.borderColor = Color.BLACK;
        
        font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
        
        glyphLayout.setText(font, this.textStr);
    }
    
    public Text(String fontFile, int fontSize, int fontBorderWidth) {
        this(fontFile, fontSize, fontBorderWidth, "", 0, 0);
    }
    
    /**
     * Draws text in center of set position
     * @param batch pass here batch of main render method
     * @see SpriteBatch
     */
    public void drawTextCentered(SpriteBatch batch) {
        font.draw(batch, glyphLayout, position.x - glyphLayout.width / 2, position.y);
    }
    
    /**
     * Called when Text gets removed.
     */
    public void disposeText() {
        generator.dispose();
    }
    
    /* getters & setters */
    public void setText(String textStr) {
        this.textStr = textStr;
        glyphLayout.setText(font, this.textStr);
    }
    
    public void setPosition(int x, int y) {
        position.x = x;
        position.y = y;
    }
    
    public  void setFontColor(Color color) {
        font.setColor(color);
        glyphLayout.setText(font, this.textStr);
    }
}
