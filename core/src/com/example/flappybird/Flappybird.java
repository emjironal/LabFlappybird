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
		//Imágenes
		background = new Texture("bg.png");
		logMessage(String.format("BG Width: %d Height: %d",background.getWidth(), background.getHeight()));
		bird = new Texture("bird.png");
		logMessage(String.format("Bird Width: %d Height: %d",bird.getWidth(), bird.getHeight()));
		bird2 =  new Texture("bird2.png");
		pipeBottom = new Texture("bottomtube.png");
		logMessage(String.format("Bottom pipe Width: %d Height: %d",pipeBottom.getWidth(), pipeBottom.getHeight()));
		pipeTop = new Texture("toptube.png");
		logMessage(String.format("Top pipe Width: %d Height: %d",pipeTop.getWidth(), pipeTop.getHeight()));
		gameOver = new Texture("game_over.png");
		logMessage(String.format("Game over Width: %d Height: %d",gameOver.getWidth(), gameOver.getHeight()));
		//Bird
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		birdX = (width / 2F) - (bird.getWidth() / 2F);
		birdY = (height / 2F) - (bird.getHeight() / 2F);
		isBird2 = false;
		//Gravedad
		setGravity(height / 5000F);
		velocity = 0F;
		//Puntuación
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(5);
		puntos = 0;
		puntosX = 10F;
		puntosY = 75F;
		//Pipes
		setDistanciaEntrePipes(400F);
		setDistanciaEntrePipesTopBottom(400F);
		topPipes = new ArrayList<Pipe>();
		bottomPipes = new ArrayList<Pipe>();
		float temp = Gdx.graphics.getWidth();
		for(int i = 0; i < 3; i++)
		{
			temp += pipeTop.getWidth() + distanciaEntrePipes;
			topPipes.add(new Pipe(pipeTop, temp, 100));//usar /numero para definir más estandar y  más facilidad
			bottomPipes.add(new Pipe(pipeBottom, temp, -100));
			setYPipes(topPipes.get(i), bottomPipes.get(i));
		}

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
		moverPipes();
		isBird2 = !isBird2;
		batch.end();
	}

	private void setYPipes(Pipe top, Pipe bottom)
	{
		Random random = new Random();
		Integer rnd = random.nextInt(51) + 20;
		float yBottom = Float.parseFloat(rnd.toString() + ".0");
		yBottom -= 100F;
		yBottom /= 80F;
		yBottom *= bottom.getPipe().getHeight();
		float yTop = yBottom + bottom.getPipe().getHeight() + distanciaEntrePipesTopBottom;
		top.setY(yTop);
		bottom.setY(yBottom);
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

	private void moverPipes()
	{
		for(int i = 0; i < topPipes.size(); i++)
		{
			Pipe top = topPipes.get(i);
			Pipe bottom = bottomPipes.get(i);
			float x = top.getX();
			x -= velocidadPipes;
			if(x <= -bottom.getPipe().getWidth())
			{
				if(i == 0)
					x = bottomPipes.get(2).getX() + distanciaEntrePipes + bottom.getPipe().getWidth();
				else if(i == 1)
					x = bottomPipes.get(0).getX() + distanciaEntrePipes + bottom.getPipe().getWidth();
				else
					x = bottomPipes.get(1).getX() + distanciaEntrePipes + bottom.getPipe().getWidth();
				setYPipes(top, bottom);
			}
			top.setX(x);
			bottom.setX(x);
		}
	}

	private void moverBird()
	{
		if(Gdx.input.justTouched())
		{
			birdY += height / 10F;
			velocity = 0F;
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
