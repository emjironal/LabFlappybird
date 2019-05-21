package com.example.flappybird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Figura
{
    private Vector2 p0;
    private Vector2 p1;
    private Vector2 p2;
    private Vector2 p3;

    public Figura(float x, float y, float ancho)
    {
        setP0(new Vector2(x, y));
        setP1(new Vector2(x, y));
        setP2(new Vector2(x, y + ancho));
        setP3(new Vector2(x, y + ancho));
    }

    public Figura(Texture texture, float x, float y)
    {
        setP0(new Vector2(x, y));
        setP1(new Vector2(x + texture.getWidth(), y));
        setP2(new Vector2(x, y + texture.getHeight()));
        setP3(new Vector2(x + texture.getWidth(), y + texture.getHeight()));
    }

    public Figura(Texture texture, float x, float y, float alto)
    {
        setP0(new Vector2(x, y));
        setP1(new Vector2(x + texture.getWidth(), y));
        setP2(new Vector2(x, y + texture.getHeight() + alto));
        setP3(new Vector2(x + texture.getWidth(), y + texture.getHeight() + alto));
    }

    public boolean contacto(Figura figura)
    {
        return (figura.p0.x <= p0.x && p0.x <= figura.p1.x && figura.p0.y <= p0.y && p0.y <= figura.p2.y) ||
               (figura.p0.x <= p1.x && p1.x <= figura.p1.x && figura.p0.y <= p1.y && p1.y <= figura.p2.y) ||
               (figura.p0.x <= p2.x && p2.x <= figura.p1.x && figura.p0.y <= p2.y && p2.y <= figura.p2.y) ||
               (figura.p0.x <= p3.x && p3.x <= figura.p1.x && figura.p0.y <= p3.y && p3.y <= figura.p2.y);
    }
    
    public void mover(float desplazamientoX, float desplazamientoY)
    {
        p0.x += desplazamientoX;
        p0.y += desplazamientoY;
        p1.x += desplazamientoX;
        p1.y += desplazamientoY;
        p2.x += desplazamientoX;
        p2.y += desplazamientoY;
        p3.x += desplazamientoX;
        p3.y += desplazamientoY;
    }

    public Vector2 getP0() {
        return p0;
    }

    public void setP0(Vector2 p0) {
        this.p0 = p0;
    }

    public Vector2 getP1() {
        return p1;
    }

    public void setP1(Vector2 p1) {
        this.p1 = p1;
    }

    public Vector2 getP2() {
        return p2;
    }

    public void setP2(Vector2 p2) {
        this.p2 = p2;
    }

    public Vector2 getP3() {
        return p3;
    }

    public void setP3(Vector2 p3) {
        this.p3 = p3;
    }
}
