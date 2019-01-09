package com.nocompany.coopgame.network.protocol;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.nocompany.coopgame.physics.ShapeType;

public class InstantiateRequest {
	public Vector2 position;
	public String name;
}
