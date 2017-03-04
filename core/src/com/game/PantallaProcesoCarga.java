package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by raul on 25/2/17.
 */

public class PantallaProcesoCarga extends PantallaBase{

    private Stage stage;
    private Skin skin;
    private Label carga;
    private Image logo;
    private ProgressBar pb;

    public PantallaProcesoCarga(MainGame game) {
        super(game);

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        logo = new Image(game.getManager().get("logoCarga.png", Texture.class));

        logo.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        logo.setPosition(Gdx.graphics.getWidth() / 2 - logo.getWidth() / 2 , Gdx.graphics.getHeight() / 3);

        carga = new Label("Cargando...",skin);
        carga.setPosition(Gdx.graphics.getWidth() / 2 - carga.getWidth() / 3 - 5, Gdx.graphics.getHeight() / 5);

        pb = new ProgressBar(0, 100, 1, false, skin);
        pb.setPosition(Gdx.graphics.getWidth() / 2 - carga.getWidth() / 2  , Gdx.graphics.getHeight() / 6);

        stage.addActor(pb);
        stage.addActor(carga);
        stage.addActor(logo);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(game.getManager().update()){
            game.setScreen(new PantallaInicio(game));
        }else{
            int procesoCarga = (int) (game.getManager().getProgress() * 100);
            pb.setValue(procesoCarga);
            carga.setText("Cargando... " + procesoCarga + "%");
            System.out.println(procesoCarga);
        }

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
