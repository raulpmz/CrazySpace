package com.game.Actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;


/**
 * Created by raul on 25/2/17.
 */

public class ActorBala extends Actor{

    private Texture bala;

    public ActorBala(Texture bala, float x, float y) {
        this.bala = bala;
        setX(x);
        setY(y);
        setSize(bala.getWidth(), bala.getHeight());
    }

    @Override
    public void act(float delta) {
        if (getX() <= Gdx.graphics.getWidth()) {
            setX(getX() + 900 * delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(bala, getX(), getY());
    }
}
