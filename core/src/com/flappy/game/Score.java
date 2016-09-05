package com.flappy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Score {

	/*
	 * FreeTypeFont is libGDX extension
	 * allows to use .ttf fonts and flexible font size setting
	 */
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;
	private BitmapFont font;
	
	/*
	 * score representation variables
	 * scoreFlags array is for checking if pipe is already 
	 * crossed by bird. It is used for proper score icrementing
	 */
	private int score;
	private boolean[] scoreFlags;
	private String scoreStr;
	
	//text position
	private float x, y;
	
	public Score(int fontSize, int numOfPipes) {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
		parameter = new FreeTypeFontParameter();
		
		// set font size (pixels) here
		parameter.size = fontSize;
		
		font = generator.generateFont(parameter); 
		score = 0;
		scoreStr = new String();
		scoreStr = Integer.toString(score);
		
		// flags initialization
		scoreFlags = new boolean[numOfPipes];
		for (int i=0; i<scoreFlags.length; i++) {
			scoreFlags[i] = false;
		}
		
		// set score window position here
		x = Game.window.x / 2 - 30;
		y = Game.window.y - 100;
	}
	
	public void disposeScore() {
		generator.dispose();
	}
	
	public void drawScore(SpriteBatch batch) {
		font.draw(batch, scoreStr, x, y);
	}
	
	public void updateScore(Pipe[] pipes, Bird bird) {
		for (int i=0; i<pipes.length; i++) {
			if ( !scoreFlags[i] && pipes[i].getPipeLow().x < bird.getBirdShape().x ) {
				score++; 
				scoreFlags[i] = true;
			}
		}
		
		for (int i=0; i<pipes.length; i++) {
			if ( scoreFlags[i] && pipes[i].getPipeLow().x > bird.getBirdShape().x ) {
				scoreFlags[i] = false;
			}
		}
	
		scoreStr = Integer.toString(score);
	}
		
}
