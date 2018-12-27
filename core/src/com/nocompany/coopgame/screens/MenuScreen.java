package com.nocompany.coopgame.screens;

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
		
		TextButton makeClientBtn = new TextButton("stop server", skin);
		makeClientBtn.setPosition(30, 30);
		makeClientBtn.addListener(new ClickListener() {
			
			@Override
	        public void clicked(InputEvent event, float x, float y) {
				System.out.println("client");
				Network.getInstance().DestroyServer();
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
	
				Gdx.app.postRunnable(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						java.util.List<InetAddress> adrList = Network.getInstance().GetAdrList();
						
						for (InetAddress ia: adrList) {
							String ipString = ia.getHostName();
							System.out.println(ipString);
							Label label = new Label(ipString, skin);
							
							scrollTable.add(label);
						    scrollTable.row();
						}
					}
				});
				
				
				
				
				//stage.addActor(ipList);
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
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
