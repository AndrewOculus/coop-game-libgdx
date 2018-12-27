package com.nocompany.coopgame.physics;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.physics.box2d.joints.*;
import java.util.*;

public class Physics implements Disposable
{

	@Override
	public void dispose(){
		world.dispose();
		box2ddr.dispose();
	}

	private World world;
	private Box2DDebugRenderer box2ddr = new Box2DDebugRenderer();
	private List<GameObject> gameObjects = new ArrayList<GameObject>();

	public Physics(Vector2 gravity){
		this.world = new World(gravity, false);
	}
	
	public Physics(){
		this.world = new World(new Vector2(0,-9.81f), false);
	}

	public void update(float dt){
		world.step(dt,2,6);
		for( GameObject go : gameObjects){
			go.updateActions(dt);
		}
	}
	
	public void render(OrthographicCamera camera){
		box2ddr.render(world,camera.combined);
	}
	
	public Body istantiate(Vector2 pos, Vector2 size, BodyDef.BodyType type, ShapeType shType){
		BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(pos.x, pos.y);
		Body body = this.world.createBody(bodyDef);

		if(shType == ShapeType.box){
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(size.x, size.y);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.density = 1f;
			Fixture fixture = body.createFixture(fixtureDef);
			shape.dispose();
		}
       	else{
			CircleShape shape = new CircleShape();
			shape.setRadius(size.x);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = shape;
			fixtureDef.density = 1f;
			Fixture fixture = body.createFixture(fixtureDef);
			shape.dispose();
		}
		return body;
	}

	public World getWorld(){
		return world;
	}
	
	public void addGameObject(GameObject go){
		gameObjects.add(go);
	}
}



