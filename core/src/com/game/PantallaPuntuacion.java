package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Created by raul on 28/2/17.
 */

public class PantallaPuntuacion extends PantallaBase {

    private Stage stage;
    private Skin skin;
    private Label puntos;
    private TextButton Atras, resetPuntos;
    private Image fondo;


    public PantallaPuntuacion(final MainGame game) {
        super(game);

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        puntos = new Label("Record : " + game.preferences.getInteger("puntosRecord"),skin);
        puntos.setColor(Color.WHITE);
        puntos.setFontScale(3,3);
        puntos.setPosition(Gdx.graphics.getWidth() / 2 - puntos.getWidth() * 1.5f , Gdx.graphics.getHeight() / 2);

        fondo = new Image(game.getManager().get("fondoInicio.png", Texture.class));
        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Atras = new TextButton("Atras", skin);
        Atras.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getWidth() / 14);
        Atras.setPosition(10 , Gdx.graphics.getHeight() - Atras.getHeight() - 10);

        resetPuntos = new TextButton("Resetear Puntuacion", skin);
        resetPuntos.setSize(Gdx.graphics.getWidth() / 4, Gdx.graphics.getWidth() / 14);
        resetPuntos.setPosition(Gdx.graphics.getWidth() - resetPuntos.getWidth() - 10 , Gdx.graphics.getHeight() - resetPuntos.getHeight() - 10);

        Atras.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PantallaInicio(game));
            }
        });

        resetPuntos.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.preferences.putInteger("puntosRecord", 0);
                game.preferences.flush();
                game.setScreen(new PantallaInicio(game));
            }
        });

        stage.addActor(fondo);
        stage.addActor(puntos);
        stage.addActor(Atras);
        stage.addActor(resetPuntos);

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
    }
}
