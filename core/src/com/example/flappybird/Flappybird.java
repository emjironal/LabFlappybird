package com.example.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.ArrayList;
import java.util.Random;

public class Flappybird extends ApplicationAdapter
{
	private SpriteBatch batch;
    private Texture background;
    private Texture bird;
    private Texture bird2;
    private Texture pipeTop;
    private Texture pipeBottom;
    private Texture gameOver;
    private float width;
    private float height;
    private float birdX;
    private float birdY;
    private boolean isBird2;
    private float gravity;
    private float velocity;
    private BitmapFont font;
    private Integer puntos;
    private float puntosY;
    private float puntosX;
    private ArrayList<Pipe> topPipes;
    private ArrayList<Pipe> bottomPipes;
    private float distanciaEntrePipes;
    private float distanciaEntrePipesTopBottom;
    private float velocidadPipes;
    private boolean gameStarted;
    private Figura birdFigura;
    private ArrayList<Figura> topPipesFigura;
    private ArrayList<Figura> bottomPipesFigura;
    private boolean perdio;
    private ArrayList<Boolean> isPunto;
    private Sound choque;
    private Sound hacerPunto;
    private Music music;
	
	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		choque = new Gdx.audio.newSound(Gdx.files.getFileHandle(""))
        initialize();
	}

	@Override
	public void render ()
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!gameStarted)
			gameStarted = Gdx.input.justTouched();
		batch.begin();
		batch.draw(background, 0, 0, width, height);
		if(perdio && !gameStarted)
        {
            batch.draw(gameOver, (width / 2F) - (gameOver.getWidth() / 2F), (height / 2F) - (gameOver.getHeight() / 2F));
        }
		else
        {
            if(gameStarted)
            {
                moverBird();
                moverPipes();
                drawPipes(topPipes);
                drawPipes(bottomPipes);
            }
            font.draw(batch, puntos.toString(), puntosX, puntosY);
            if(isBird2)
            {
                batch.draw(bird2, birdX, birdY);
            }
            else
            {
                batch.draw(bird, birdX, birdY);
            }
            isBird2 = !isBird2;
        }
		batch.end();
	}

    private void drawPipes(ArrayList<Pipe> pipes)
    {
        for(Pipe pipe : pipes)
        {
            batch.draw(pipe.getPipe(), pipe.getX(), pipe.getY());
        }
    }

    private void perder()
    {
        initialize();
        perdio = true;
    }

    private boolean checkCollision(Figura pipe)
    {
        return birdFigura.contacto(pipe);
    }

    private void moverPipes()
	{
		for(int i = 0; i < topPipes.size(); i++)
		{
			Pipe top = topPipes.get(i);
			Pipe bottom = bottomPipes.get(i);
			float x = top.getX();
			float tempX = x;
            float tempTopY = top.getY(), tempBottomY = bottom.getY();
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
            topPipesFigura.get(i).mover(x - tempX, top.getY()- tempTopY);
            bottomPipesFigura.get(i).mover(x - tempX, bottom.getY()- tempBottomY);
            tempX = x + (pipeTop.getWidth() / 2F);
            if(birdX <= tempX && tempX <= birdX + bird.getWidth())
            {
                if(!isPunto.get(i))
                    puntos++;
                isPunto.set(i, true);
            }
			else
            {
                isPunto.set(i, false);
            }
            if(checkCollision(topPipesFigura.get(i)) || checkCollision(bottomPipesFigura.get(i)))
            {
                perder();
            }
		}
	}

	private void moverBird()
	{
		if(Gdx.input.justTouched())
		{
			birdY += height / 10F;
			velocity = 0F;
			birdFigura.mover(0F, height / 10F);
		}
		else
		{
			velocity += gravity;
			birdY -= velocity;
            birdFigura.mover(0F, -velocity);
		}
		if(birdY <= 0)
        {
            perder();
        }
	}

	private void initialize()
    {
        //Imágenes
        initializeTextures();
        //Bird
        initializeBird();
        //Gravedad
        setGravity(height / 5000F);
        velocity = 0F;
        //Puntuación
        initializePuntos();
        //Pipes
        setDistanciaEntrePipes(400F);
        setDistanciaEntrePipesTopBottom(400F);
        initializePipes(3);
        setVelocidadPipes(5F);
        gameStarted = false;
        perdio = false;
    }

    private void initializeTextures()
    {
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
    }

    private void initializeBird()
    {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        birdX = (width / 2F) - (bird.getWidth() / 2F);
        birdY = (height / 2F) - (bird.getHeight() / 2F);
        birdFigura = new Figura(bird, birdX, birdY);
        isBird2 = false;
    }

    private void initializePuntos()
    {
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(5);
        puntos = 0;
        puntosX = 10F;
        puntosY = 75F;
        isPunto = new ArrayList<Boolean>();
    }

    private void initializePipes(int cantidad)
    {
        topPipes = new ArrayList<Pipe>();
        bottomPipes = new ArrayList<Pipe>();
        topPipesFigura = new ArrayList<Figura>();
        bottomPipesFigura = new ArrayList<Figura>();
        float temp = Gdx.graphics.getWidth();
        for(int i = 0; i < cantidad; i++)
        {
            temp += pipeTop.getWidth() + distanciaEntrePipes;
            topPipes.add(new Pipe(pipeTop, temp, 100));
            bottomPipes.add(new Pipe(pipeBottom, temp, -100));
            setYPipes(topPipes.get(i), bottomPipes.get(i));
            topPipesFigura.add(new Figura(pipeTop, topPipes.get(i).getX(), topPipes.get(i).getY(), 1000000F));
            bottomPipesFigura.add(new Figura(pipeBottom, bottomPipes.get(i).getX(), bottomPipes.get(i).getY()));
            isPunto.add(false);
        }
    }

    private void setVelocidadPipes(float valor)
    {
        velocidadPipes = valor;
    }

    private void setDistanciaEntrePipesTopBottom(float valor)
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
		font.dispose();
	}
}
