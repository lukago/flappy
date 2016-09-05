package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Score {

	/**
	 * FreeTypeFont is libGDX extension
	 * allows to use .ttf fonts and flexible font size setting
	 * glyphLayout is to get text width
	 */
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;
	private BitmapFont font;
	private GlyphLayout glyphLayout;
	
	/**
	 * score representation variables
	 * scoreFlags array is for checking if pipe is already 
	 * crossed by bird. 
	 */
	private int score;
	private boolean[] scoreFlags;
	private String scoreStr;
	
	//text position
	private float x, y;
	
	public Score(int fontSize, int numOfPipes) {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("dlacruz.ttf"));
		parameter = new FreeTypeFontParameter();
		
		// set font size (pixels) here
		parameter.size = fontSize;
		
		font = generator.generateFont(parameter); 
		score = 0;
		scoreStr = new String();
		scoreStr = Integer.toString(score);
		glyphLayout = new GlyphLayout();
		
		// flags initialization
		scoreFlags = new boolean[numOfPipes];
		for (int i=0; i<scoreFlags.length; i++) {
			scoreFlags[i] = false;
		}
		
		// set score window position here
		x = Game.window.x / 2;
		y = Game.window.y - 100;
	}
	
	public void disposeScore() {
		generator.dispose();
	}
	
	public void drawScore(SpriteBatch batch) {
		font.draw(batch, glyphLayout, x - glyphLayout.width / 2, y);
	}
	
	public void updateScore(Pipe[] pipes, Bird bird) {	
		// score increment checking
		for (int i=0; i<pipes.length; i++) {
			if ( !scoreFlags[i] && pipes[i].getPipeLow().x < bird.getBirdShape().x ) {
				score++; 
				scoreFlags[i] = true;
			}
		}
		
		// checking if pipe is already ahead the bird
		for (int i=0; i<pipes.length; i++) {
			if ( scoreFlags[i] && pipes[i].getPipeLow().x > bird.getBirdShape().x ) {
				scoreFlags[i] = false;
			}
		}
	
		scoreStr = Integer.toString(score);
		glyphLayout.setText(font, scoreStr);
	}
}
