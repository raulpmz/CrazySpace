package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class MainGame extends Game {

	private AssetManager manager;
	public Preferences preferences;

	@Override
	public void create () {

		manager = new AssetManager();

		manager.load("logoCarga.png", Texture.class);

		manager.finishLoading();

		manager.load("titulo.png", Texture.class);
		manager.load("fondoInicio.png", Texture.class);
		manager.load("fondoJuego.png", Texture.class);
		manager.load("gameOver.png", Texture.class);
		manager.load("new.png", Texture.class);
		manager.load("pony.gif", Texture.class);
		manager.load("ponyTriste.png", Texture.class);
		manager.load("bala.png", Texture.class);
		manager.load("patricio.png", Texture.class);



		manager.load("disparo.mp3", Sound.class);
		manager.load("punto.mp3", Sound.class);


		manager.load("musicaJuego.mp3", Music.class);
		manager.load("musicaInicio.mp3", Music.class);
		manager.load("gameOver.mp3", Music.class);


		preferences = Gdx.app.getPreferences("pref");


		setScreen(new PantallaProcesoCarga(this));

	}

	public AssetManager getManager() {
		return manager;
	}
}
