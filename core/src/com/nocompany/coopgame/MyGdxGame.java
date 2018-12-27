package com.nocompany.coopgame;

import com.badlogic.gdx.Game;
import com.nocompany.coopgame.screens.GameScreen;
import com.nocompany.coopgame.screens.MenuScreen;

public class MyGdxGame extends Game {

	public  GameScreen gameScreen;
	public  MenuScreen menuScreen;	

	@Override
	public void create() {		
		gameScreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}


}
