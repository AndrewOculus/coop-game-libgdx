package com.nocompany.coopgame.screens;

import java.io.IOException;
import java.net.InetAddress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nocompany.coopgame.MyGdxGame;
import com.nocompany.coopgame.network.Network;

public class MenuScreen implements Screen {

	private MyGdxGame myGdxGame;
	
	private Stage stage;
	private Skin skin;
	private Group ipList;
	private Table scrollTable;
	
	public MenuScreen(MyGdxGame mgg) {
		this.myGdxGame = mgg;
		begin();
	}
	
	public void begin() {
		skin = new Skin(Gdx.files.internal("freezing/skin/freezing-ui.json"));
		stage = new Stage();
		
		ipList = new Group();
		
		TextButton makeServerBtn = new TextButton("make sever", skin);
		makeServerBtn.setPosition(30, 100);
		makeServerBtn.addListener(new ClickListener() {
			
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				System.out.println("server");
				myGdxGame.setScreen(myGdxGame.gameScreen);

				Network.getInstance().CreateServer();
			}
			
		});
		
		stage.addActor(makeServerBtn);
		
		TextButton makeClientBtn = new TextButton("connect client", skin);
		makeClientBtn.setPosition(30, 30);
		makeClientBtn.addListener(new ClickListener() {
			
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				try {
					Network.getInstance().connectClient();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myGdxGame.setScreen(myGdxGame.gameScreen);
				
			}
			
		});
		stage.addActor(makeClientBtn);
		
		
		
		TextButton refreshBtn = new TextButton("refresh client", skin);
		refreshBtn.setPosition(30, 170);
		refreshBtn.addListener(new ClickListener() {
			
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				System.out.println("refresh");
				ipList = new Group();
				ipList.setPosition(300, 300);
	
				System.out.println("ok");

				InetAddress adrList = Network.getInstance().GetAdr();
				
				System.out.println(adrList.getHostAddress());

				
//				java.util.List<InetAddress> adrList = Network.getInstance().GetAdrList();
//				System.out.println("ok");
//
//				for (InetAddress ia: adrList) {
//					String ipString = ia.getHostName();
//					System.out.println(ipString);
//					
//				}

			}
			
		});
		stage.addActor(refreshBtn);
	    
		Gdx.input.setInputProcessor(stage);
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(null);
		System.out.println("ok");

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
