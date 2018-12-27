package com.nocompany.coopgame.physics;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.*;
import java.util.*;

public class GameObject{

	public Body body;
	public DistanceJoint joint;
	private World world;
	private List<Action> actions;

	public GameObject(){
		actions = new ArrayList<Action>();
	}

	public void addAction(Action act){
		actions.add(act);
	}

	public void updateActions(float dt){
		for(Action act : actions){
			act.act(dt,this);
		}
	}

	public void setWorld(World World){
		this.world=World;
	}

	public void makeDistanceJoint(GameObject B){
		makeDistanceJoint(B, this.body.getPosition(),B.body.getPosition());
	}

	public void makeDistanceJoint(GameObject B, Vector2 p1, Vector2 p2){
		if(joint!=null)
			return;
		DistanceJointDef defJoint = new DistanceJointDef ();
		defJoint.length = 0;
		defJoint.frequencyHz=10;
		defJoint.dampingRatio=0;
		//defJoint.collideConnected=true;
		defJoint.initialize(this.body, B.body, p1, p2);
		joint = (DistanceJoint) world.createJoint(defJoint);
	}

	public void destroyDistanceJoint(){
		if(joint!=null){
			world.destroyJoint(joint);
			joint = null;
		}
	}
	
public static class Builder{

	private GameObject gameObject;
	private Physics physicsWorld;
	private Vector2 position = new Vector2(0,0);
	private Vector2 size = new Vector2(10,10);
	private BodyDef.BodyType type = BodyDef.BodyType.DynamicBody;
	private ShapeType shapeType = ShapeType.box;
	private List<Action> actions = new ArrayList<Action>();

	public Builder(Physics pw){
		this.physicsWorld = pw;
	}

	public Builder setPos(Vector2 pos){
		this.position = pos;
		return this;
	}

	public Builder setSize(Vector2 size){
		this.size = size;
		return this;
	}

	public Builder setType(BodyDef.BodyType type){
		this.type = type;
		return this;
	}

	public Builder setShape(ShapeType shapeType){
		this.shapeType=shapeType;
		return this;
	}
	
	public Builder setAction(Action act) {
		this.actions.add(act);
		return this;
	}

	public GameObject build(){
		gameObject = new GameObject();
		gameObject.setWorld(physicsWorld.getWorld());
		gameObject.body = physicsWorld.istantiate(position,size,type,shapeType);
		gameObject.actions = actions;
		physicsWorld.addGameObject(gameObject);
		return gameObject;
	}
}
}
