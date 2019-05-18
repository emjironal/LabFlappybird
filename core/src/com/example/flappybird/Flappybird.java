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
	float distanciaEntrePipes;
	float distanciaEntrePipesTopBottom;
	float velocidadPipes;
	
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
		setGravity(height / 5000);
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
		bottomPipes = new ArrayList<Pipe>();
		float temp = Gdx.graphics.getWidth();
		setDistanciaEntrePipes(300F);
		temp += pipeTop.getWidth() + distanciaEntrePipes;
		topPipes.add(new Pipe(pipeTop, temp, 100));//usar /numero para definir más estandar y  más facilidad
		bottomPipes.add(new Pipe(pipeBottom, temp, -100));
		temp += pipeTop.getWidth() + distanciaEntrePipes;
		topPipes.add(new Pipe(pipeTop, temp, 100));
		bottomPipes.add(new Pipe(pipeBottom, temp, -100));
		temp += pipeTop.getWidth() + distanciaEntrePipes;
		topPipes.add(new Pipe(pipeTop, temp, 100));
		bottomPipes.add(new Pipe(pipeBottom, temp, -100));
		temp += pipeTop.getWidth() + distanciaEntrePipes;
		topPipes.add(new Pipe(pipeTop, temp, 100));
		bottomPipes.add(new Pipe(pipeBottom, temp, -100));
		velocidadPipes = 5F;
	}

	public void setDistanciaEntrePipesTopBottom(float valor)
	{
		distanciaEntrePipesTopBottom = valor;
	}

	private void setGravity(float valor)
	{
		gravity = valor;
	}

	private void setDistanciaEntrePipes(float valor)
	{
		distanciaEntrePipes = valor;
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
		moverPipes(topPipes);
		moverPipes(bottomPipes);
		isBird2 = !isBird2;
		batch.end();
	}

	private void setYPipes()
	{
		for(int i = 0; i < topPipes.size(); i++)
		{
			Pipe top = topPipes.get(i);
			Pipe bottom = bottomPipes.get(i);
			/*float y1 = ;
			float y2 = ;
			top.setY();
			bottom.setY();*/
		}
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
			x -= velocidadPipes;
			if(x <= -pipe.getPipe().getWidth())
			{
				x = Gdx.graphics.getWidth() + distanciaEntrePipes + distanciaEntrePipes;
			}
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
