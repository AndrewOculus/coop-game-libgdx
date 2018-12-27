package com.nocompany.coopgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.nocompany.coopgame.MyGdxGame;
import com.nocompany.coopgame.controller.UIGamepad;
import com.nocompany.coopgame.physics.Action;
import com.nocompany.coopgame.physics.GameObject;
import com.nocompany.coopgame.physics.Physics;
import com.nocompany.coopgame.physics.ShapeType;

public class GameScreen implements Screen, InputProcessor {

	private MyGdxGame myGdxGame;
	private UIGamepad uiGamepad;
	private Physics physics;
	private GameObject.Builder builder;
	private OrthographicCamera camera;
	
	public GameScreen(MyGdxGame mgg) {
		this.myGdxGame = mgg;
		this.uiGamepad = new UIGamepad();
		this.physics = new Physics(new Vector2());
		
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		this.camera.zoom = 0.23f;
		
		builder = new GameObject.Builder(physics);
		builder.setPos(new Vector2(0, 0))
		.setShape(ShapeType.box)
		.setType(BodyType.DynamicBody)
		.setSize(new Vector2(1,1))
		.setAction(new Action() {
			
			float velocity = 1000;
			Vector3 pos = new Vector3();
			
			@Override
			public void act(float dt, GameObject self) {
				self.body.setLinearVelocity(uiGamepad.getDx()*dt*velocity, uiGamepad.getDy()*dt*velocity);
				pos.set(self.body.getPosition(), 0);
				camera.position.lerp(pos, 0.2f);
			}
		})
		.build();
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float dt) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		physics.render(camera);
		physics.update(dt);
		uiGamepad.render(dt);
		
		camera.update();
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
