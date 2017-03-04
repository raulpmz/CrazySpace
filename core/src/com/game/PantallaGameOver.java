package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
 * Created by raul on 25/2/17.
 */

public class PantallaGameOver extends PantallaBase {

    private Stage stage;
    private Skin skin;
    private Image gameover, fondo, record;
    private TextButton volverJugar, inicio;
    private Label puntos;
    private Music musicaInicio;


    public PantallaGameOver(final MainGame game) {

        super(game);

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        gameover = new Image(game.getManager().get("gameOver.png", Texture.class));
        fondo = new Image(game.getManager().get("fondoInicio.png", Texture.class));
        record = new Image(game.getManager().get("new.png", Texture.class));

        volverJugar = new TextButton("Volver a Jugar", skin);
        inicio = new TextButton("Inicio", skin);

        gameover.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        gameover.setPosition(Gdx.graphics.getWidth() / 2 - gameover.getWidth() / 2 , Gdx.graphics.getHeight() / 3);

        puntos = new Label("Puntos partida : " + game.preferences.getInteger("puntosPartida"),skin);
        puntos.setColor(Color.WHITE);
        puntos.setFontScale(3,3);
        puntos.setPosition(15, Gdx.graphics.getHeight() - puntos.getHeight() * 3 - 20);

        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        record.setPosition(30 + puntos.getWidth() * 3, Gdx.graphics.getHeight() - record.getHeight() - 20);
        record.setVisible(false);

        if (game.preferences.getInteger("puntosPartida") > game.preferences.getInteger("puntosPreRecord")){
            record.setVisible(true);
        }

        inicio.setSize(Gdx.graphics.getWidth() / 4, gameover.getHeight() / 5);
        inicio.setPosition(Gdx.graphics.getWidth() / 4 + inicio.getWidth() / 2, Gdx.graphics.getHeight()  / 3);

        volverJugar.setSize(Gdx.graphics.getWidth() / 4 , gameover.getHeight() / 5);
        volverJugar.setPosition(Gdx.graphics.getWidth() / 4 + volverJugar.getWidth() / 2, Gdx.graphics.getHeight() / 3 - volverJugar.getHeight() - 20);

        volverJugar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                musicaInicio.stop();
                game.setScreen(new PantallaMainGame(game));
            }
        });

        inicio.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PantallaInicio(game));
            }
        });

        musicaInicio = game.getManager().get("musicaInicio.mp3");

        if (game.preferences.getBoolean("Sonido")) {
            musicaInicio.play();
        }else{
            musicaInicio.stop();
        }

        stage.addActor(fondo);
        stage.addActor(record);
        stage.addActor(puntos);
        stage.addActor(volverJugar);
        stage.addActor(gameover);
        stage.addActor(inicio);

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
