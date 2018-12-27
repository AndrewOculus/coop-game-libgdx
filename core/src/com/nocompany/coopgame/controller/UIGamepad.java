package com.nocompany.coopgame.controller;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class UIGamepad implements Disposable
{
	private ShapeRenderer shapeRenderer;
	private final float propX = 0.5f;
	private final float propY = 0.3f;
	private final float bigR = 0.16f;
	private final float smallR = 0.08f;
	private float x;
	private float y;
	private float bx;
	private float by;
	private float h;
	private float w;
	private int touchNum = -1;
	private boolean isTouched;
	
	public  UIGamepad()	{
		shapeRenderer = new ShapeRenderer();
		h = Gdx.graphics.getHeight();
		w = Gdx.graphics.getWidth();
	}
	
	public void render(float dt){
		
		if(!isTouched)
		for(int i = 0 ; i < 5; i++)	{
			
			float tx = Gdx.input.getX(i);
			float ty = Gdx.graphics.getHeight()- Gdx.input.getY(i);
			
			if(Gdx.input.isTouched(i))
			if(tx < propX*w && ty < propY*h){
				isTouched = true;
				touchNum = i;
				//TODO put base here
				bx = tx;
				by = ty;
				break;
			}
		
		}
		
		if(isTouched){
			if(Gdx.input.isTouched(touchNum)){
				//TODO draw stick here
				x = Gdx.input.getX(touchNum);
				y = Gdx.graphics.getHeight()- Gdx.input.getY(touchNum);
			
			}else{
				x=bx;
				y=by;
				isTouched=false;
			}
		}
		
		if(isTouched){
			shapeRenderer.setColor(Color.BLACK);
		}else{
			shapeRenderer.setColor(Color.LIGHT_GRAY);
		}
		
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.circle(x,y,smallR*w);
		shapeRenderer.circle(bx,by,bigR*w);
		shapeRenderer.circle(bx,by,bigR*w*0.98f);
		shapeRenderer.end();
	}
	
	public float getDx(){
		return -bx + x;
	}
	
	public float getDy(){
		return -by + y;
	}
	
	@Override
	public void dispose()
	{
		shapeRenderer.dispose();
	}
	
}
