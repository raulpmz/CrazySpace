package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.game.Actores.ActorBala;
import com.game.Actores.ActorEnemigo;
import com.game.Actores.ActorJugador;

/**
 * Created by raul on 25/2/17.
 */

public class PantallaMainGame extends PantallaBase {

    private Stage stage;
    private Texture texturaJugador, texturaBala, texturaEnemigo;
    private ActorJugador jugador;
    private Array<ActorBala> listaBalas;
    private Array<ActorEnemigo> listasenemigos;
    private Sound disparo, muerteEnemigo;
    private Music musicaJuego, gameOver;
    private Skin skin;
    private Image fondo, infectado;
    private Label lPuntos, lNivel;
    private int velocidad, alatorio, probabilidad, cPuntos;
    private String cNivel;

    public PantallaMainGame(MainGame mainGame) {

        super(mainGame);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        velocidad = 200;
        probabilidad = 30;
        cPuntos = 0;
        alatorio = 0;
        cNivel = "Basico.";

        lNivel = new Label("Nivel   :   " + cNivel, skin);
        lNivel.setColor(Color.WHITE);
        lNivel.setPosition(15, Gdx.graphics.getHeight() - lNivel.getHeight() - 20);
        lNivel.setFontScale(3,3);

        lPuntos = new Label("Puntos   :   " + cPuntos, skin);
        lPuntos.setColor(Color.WHITE);
        lPuntos.setPosition(15 + lNivel.getWidth() * 4, Gdx.graphics.getHeight() - lPuntos.getHeight() - 20);
        lPuntos.setFontScale(3,3);

        listaBalas = new Array<ActorBala>();
        listasenemigos = new Array<ActorEnemigo>();

    }


    @Override
    public void show() {
        stage = new Stage();

        texturaJugador = game.getManager().get("pony.gif");
        texturaBala = game.getManager().get("bala.png");
        texturaEnemigo = game.getManager().get("patricio.png");
        disparo = game.getManager().get("disparo.mp3");
        muerteEnemigo = game.getManager().get("punto.mp3");
        gameOver = game.getManager().get("gameOver.mp3");
        musicaJuego = game.getManager().get("musicaJuego.mp3");

        musicaJuego.setVolume(0.55f);

        jugador = new ActorJugador(texturaJugador);

        fondo = new Image(game.getManager().get("fondoJuego.png", Texture.class));
        infectado = new Image(game.getManager().get("ponyTriste.png", Texture.class));

        infectado.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        infectado.setPosition(Gdx.graphics.getWidth() / 2 - infectado.getWidth() / 2 , Gdx.graphics.getHeight() / 3);
        infectado.setVisible(false);

        if (game.preferences.getBoolean("Sonido")) {
            musicaJuego.play();
        }

        stage.addActor(fondo);
        stage.addActor(infectado);
        stage.addActor(jugador);
        stage.addActor(lPuntos);
        stage.addActor(lNivel);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        disparar();
        crearEnemigos();
        moverEnemigos();
        comprobarColision();
        comprobarJugador();
        ajustarDificultard();

        stage.draw();
    }

    public void disparar(){
        if(Gdx.input.justTouched()){
            if(jugador.isVivo()){
                if (game.preferences.getBoolean("Sonido")) {
                    disparo.play();
                }
                ActorBala bala = new ActorBala(texturaBala, jugador.getX() + jugador.getWidth(), jugador.getY() + jugador.getHeight() / 4 );
                listaBalas.add(bala);
                stage.addActor(bala);
            }
        }
    }

    public void crearEnemigos(){
        alatorio =  MathUtils.random(0,probabilidad);

        if(alatorio == 10){
            ActorEnemigo enemigo = new ActorEnemigo(texturaEnemigo, velocidad);
            listasenemigos.add(enemigo);
            stage.addActor(enemigo);
        }
    }

    public void moverEnemigos() {
        float y;
        if(jugador.isVivo()){
            for(ActorEnemigo enemigos : listasenemigos){
                if(jugador.getY() > enemigos.getY()){
                    y = 1.1f;
                }else if(jugador.getY() < enemigos.getY()){
                    y = - 1.1f;
                }else {
                    y = 1;
                }
                enemigos.moverY(y);
            }
        }
    }

    public void comprobarColision(){
        Rectangle jugadorColision = new Rectangle(jugador.getX(),jugador.getY(), jugador.getWidth(),jugador.getHeight());
        Rectangle enemigosColision = new Rectangle();
        Rectangle balasColision = new Rectangle();

        for (ActorBala bala : listaBalas){
            balasColision.set(bala.getX(),bala.getY(),bala.getWidth(),bala.getHeight());
            for(ActorEnemigo enemigo : listasenemigos){
                enemigosColision.set(enemigo.getX(),enemigo.getY(),enemigo.getWidth(),enemigo.getHeight());
                if(balasColision.overlaps(enemigosColision)){
                    if (game.preferences.getBoolean("Sonido")) {
                        muerteEnemigo.play();
                    }
                    bala.remove();
                    enemigo.remove();
                    listaBalas.removeValue(bala,true);
                    listasenemigos.removeValue(enemigo,true);
                    cPuntos += 10;
                    lPuntos.setText("Puntos  :  " + cPuntos);
                }
            }
        }

        for (ActorEnemigo enemigo : listasenemigos) {
            enemigosColision.set(enemigo.getX(), enemigo.getY(), enemigo.getWidth(), enemigo.getHeight());
            if (enemigosColision.overlaps(jugadorColision)) {
                jugador.setVivo(false);
                enemigo.remove();
                listasenemigos.removeValue(enemigo,true);
                listasenemigos.clear();
                if (game.preferences.getBoolean("Sonido")) {
                    gameOver.play();
                }
            }
        }
    }

    private void comprobarJugador() {
        if(!jugador.isVivo()){
            game.preferences.putInteger("puntosPartida",cPuntos);
            if(cPuntos > game.preferences.getInteger("puntosRecord")){
                game.preferences.putInteger("puntosPreRecord", game.preferences.getInteger("puntosRecord"));
                game.preferences.putInteger("puntosRecord", cPuntos);
            }
            game.preferences.flush();
            infectado.setVisible(true);
            musicaJuego.stop();
            jugador.remove();
            stage.addAction(
                Actions.sequence(
                    Actions.delay(3),
                    Actions.run(new Runnable() {
                        @Override
                        public void run() {
                        game.setScreen(new PantallaGameOver(game));
                        }
                    })
                )
            );
        }
    }

    private void ajustarDificultard() {
        switch (cPuntos){
            case 500:
                velocidad = 300;
                break;
            case 1000:
                velocidad = 350;
                probabilidad = 20;
                cNivel = "Medio.";
                lNivel.setText("Nivel   :   " + cNivel);
                break;
            case 1500:
                velocidad = 500;
                break;
            case 1750:
                velocidad = 555;
                break;
            case 2250:
                velocidad = 600;
                probabilidad = 10;
                cNivel = "Hard.";
                lNivel.setText("Nivel   :   " + cNivel);
                break;
            case 3000:
                velocidad = 650;
                break;
            case 3500:
                velocidad = 700;
                break;
        }
    }

    @Override
    public void dispose() {
        texturaJugador.dispose();
        texturaBala.dispose();
        texturaEnemigo.dispose();
        disparo.dispose();
        muerteEnemigo.dispose();
        gameOver.dispose();
        musicaJuego.dispose();
    }

    @Override
    public void hide() {
        stage.dispose();
    }
}
