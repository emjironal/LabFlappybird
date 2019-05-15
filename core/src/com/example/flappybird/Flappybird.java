package com.example.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.TextComponent;
import java.util.ArrayList;
import java.util.Random;

public class Flappybird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture bird2;
	Texture pipeTop;
	Texture pipeBottom;
	Texture gameOver;
	float width;
	float height;
	float birdX;
	float birdY;
	boolean isBird2;
	float gravity;
	float velocity;
	BitmapFont font;
	Integer puntos;
	float puntosY;
	float puntosX;
	ArrayList<Pipe> topPipes;
	ArrayList<Pipe> bottomPipes;
	
	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		//Imagenes
		background = new Texture("bg.png");
		logMessage(String.format("BG Width: %d Height: %d",background.getWidth(), background.getHeight()));
		bird = new Texture("bird.png");
		logMessage(String.format("Bird Width: %d Height: %d",background.getWidth(), background.getHeight()));
		bird2 =  new Texture("bird2.png");
		pipeBottom = new Texture("bottomtube.png");
		logMessage(String.format("Bottom pipe Width: %d Height: %d",background.getWidth(), background.getHeight()));
		pipeTop = new Texture("toptube.png");
		logMessage(String.format("Top pipe Width: %d Height: %d",background.getWidth(), background.getHeight()));
		gameOver = new Texture("game_over.png");
		logMessage(String.format("Game over Width: %d Height: %d",background.getWidth(), background.getHeight()));
		//X Y
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		birdX = (width / 2) - (bird.getWidth() / 2);
		birdY = (height / 2) - (bird.getHeight() / 2);
		isBird2 = false;
		gravity = height / 5000;
		velocity = 0;
		//Puntuación
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(5);
		puntos = 0;
		puntosX = 10F;
		puntosY = 75F;
		//Pipes
		float pipeHeight = pipeBottom.getHeight();
		float pipeWidth = pipeBottom.getWidth();
		topPipes = new ArrayList<Pipe>();
		topPipes.add(new Pipe(pipeTop, 100, 100));//usar /numero para definir más estandar y  más facilidad
		topPipes.add(new Pipe(pipeTop, 200, 100));
		topPipes.add(new Pipe(pipeTop, 300, 100));
		topPipes.add(new Pipe(pipeTop, 400, 100));
		bottomPipes = new ArrayList<Pipe>();
		bottomPipes.add(new Pipe(pipeBottom, 100, -100));
		bottomPipes.add(new Pipe(pipeBottom, 200, -100));
		bottomPipes.add(new Pipe(pipeBottom, 300, -100));
		bottomPipes.add(new Pipe(pipeBottom, 400, -100));
	}

	@Override
	public void render ()
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0, width, height);
		font.draw(batch, puntos.toString(), puntosX, puntosY);
		if(isBird2)
		{
			batch.draw(bird2, birdX, birdY);
		}
		else
		{
			batch.draw(bird, birdX, birdY);
		}
		drawPipes(topPipes);
		drawPipes(bottomPipes);
		moverBird();
		//moverPipes(topPipes);
		//moverPipes(bottomPipes);
		isBird2 = !isBird2;
		batch.end();
	}

	private void logMessage(String message)
	{
		Gdx.app.log("Textures", message);
	}

	private void drawPipes(ArrayList<Pipe> pipes)
	{
		for(Pipe pipe : pipes)
		{
			batch.draw(pipe.getPipe(), pipe.getX(), pipe.getY());
		}
	}

	private void moverPipes(ArrayList<Pipe> pipes)
	{
		for(Pipe pipe : pipes)
		{
			float x = pipe.getX();
			x -= 10;
			pipe.setX(x);
		}
	}

	private void moverBird()
	{
		if(Gdx.input.justTouched())
		{
			birdY += height / 10;
			velocity = 0;
		}
		else
		{
			velocity += gravity;
			birdY -= velocity;
		}
	}
	
	@Override
	public void dispose ()
	{
		batch.dispose();
		background.dispose();
		bird.dispose();
		bird2.dispose();
		pipeBottom.dispose();
		pipeTop.dispose();
		gameOver.dispose();
	}
}
