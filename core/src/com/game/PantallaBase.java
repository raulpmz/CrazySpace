package com.game;

import com.badlogic.gdx.Screen;

/**
 * Created by raul on 25/2/17.
 */

public abstract class PantallaBase implements Screen {

    protected MainGame game;

    public PantallaBase(MainGame game){
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
