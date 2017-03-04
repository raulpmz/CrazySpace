package com.game.Actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by raul on 25/2/17.
 */

public class ActorJugador extends Actor {

    private Texture jugador;
    private boolean vivo = true;
    private int y;

    public ActorJugador(Texture jugador) {
        this.jugador = jugador;
        this.y = Gdx.graphics.getHeight()/2;
        setSize(jugador.getWidth(), jugador.getHeight());
    }

    @Override
    public void act(float delta) {
        int my = (int) Gdx.input.getAccelerometerY() * 4;
        if (y + my < Gdx.graphics.getHeight() - jugador.getHeight() - my && y + my >= 0) {
            y += my;
        }
        setY(y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(10, getY());
        batch.draw(jugador, getX(), getY());
    }


    public void setJugador(Texture jugador) {
        this.jugador = jugador;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
}
