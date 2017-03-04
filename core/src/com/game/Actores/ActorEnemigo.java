package com.game.Actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by raul on 25/2/17.
 */

public class ActorEnemigo extends Actor {

    private Texture enemigo;
    float y, velocidad;

    public ActorEnemigo(Texture enemigo, int velocidad) {
        this.enemigo = enemigo;
        this.velocidad = velocidad;
        setX(Gdx.graphics.getWidth() - enemigo.getWidth());
        setY(MathUtils.random(0 + enemigo.getWidth(),Gdx.graphics.getHeight()) - enemigo.getWidth());
        setSize(enemigo.getWidth(),enemigo.getHeight());
    }

    @Override
    public void act(float delta) {
        setX(getX() - velocidad * delta);
        setY(getY() + y * delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(enemigo, getX(), getY());
    }

    public void moverY(float y){
       this.y = y * 25;
    }

    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }
}
