package com.example.flappybird;

import com.badlogic.gdx.graphics.Texture;

public class Pipe
{
    private Texture pipe;
    private float x;
    private float y;

    public Pipe(Texture texture, float x, float y)
    {
        setPipe(texture);
        this.setX(x);
        this.setY(y);
    }

    public Texture getPipe() {
        return pipe;
    }

    public void setPipe(Texture pipe) {
        this.pipe = pipe;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
