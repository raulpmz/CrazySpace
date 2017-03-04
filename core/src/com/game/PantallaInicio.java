package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Created by raul on 25/2/17.
 */

public class PantallaInicio extends PantallaBase {

    private Stage stage;
    private Skin skin;
    private Image logo, fondo;
    private TextButton jugar, puntos, mute;
    private Music musicaInicio;

    public PantallaInicio(final MainGame game) {
        super(game);

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        logo = new Image(game.getManager().get("titulo.png", Texture.class));
        fondo = new Image(game.getManager().get("fondoInicio.png", Texture.class));
        jugar = new TextButton("Jugar", skin);
        puntos = new TextButton("Record Puntos", skin);
        mute = new TextButton("Sonido",skin);
        musicaInicio = game.getManager().get("musicaInicio.mp3");

        logo.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        logo.setPosition(Gdx.graphics.getWidth() / 2 - logo.getWidth() / 2 , Gdx.graphics.getHeight() / 3);

        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        jugar.setSize(Gdx.graphics.getWidth() / 4 , logo.getHeight() / 5);
        jugar.setPosition(Gdx.graphics.getWidth() / 4 + jugar.getWidth() / 2, Gdx.graphics.getHeight()  / 5);

        puntos.setSize(Gdx.graphics.getWidth() / 6 , logo.getHeight() / 6);
        puntos.setPosition(10, Gdx.graphics.getHeight() - puntos.getHeight() - 10);

        mute.setSize(Gdx.graphics.getWidth() / 6 , logo.getHeight() / 6);
        mute.setPosition(Gdx.graphics.getWidth() - mute.getWidth() - 10 , Gdx.graphics.getHeight() - mute.getHeight() - 10);

        if(game.preferences.getBoolean("Sonido")) {
            mute.setText("Sonido: On.");
            musicaInicio.play();
        }else{
            mute.setText("Sonido: Off.");
            musicaInicio.stop();
        }

        jugar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                musicaInicio.pause();
                game.setScreen(new PantallaMainGame(game));

            }
        });

        puntos.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PantallaPuntuacion(game));
            }
        });

        mute.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.preferences.getBoolean("Sonido")) {
                    game.preferences.putBoolean("Sonido", false);
                    mute.setText("Sonido: Off.");
                    musicaInicio.stop();
                }else{
                    game.preferences.putBoolean("Sonido", true);
                    mute.setText("Sonido: On.");
                    musicaInicio.play();
                }
                game.preferences.flush ();
            }
        });

        stage.addActor(fondo);
        stage.addActor(logo);
        stage.addActor(jugar);
        stage.addActor(puntos);
        stage.addActor(mute);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1f, 0.2f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
        musicaInicio.dispose();
    }
}
