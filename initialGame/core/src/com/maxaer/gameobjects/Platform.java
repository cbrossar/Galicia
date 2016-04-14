package com.maxaer.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.maxaer.constants.GameConstants;

public class Platform {
	private Sprite sprite;
	private Body body;
	private Texture texture;
	private BodyDef bd;
   
	final float PIXELS_TO_METERS = GameConstants.PIXEL_TO_METERS;
   
	public Platform (World world)
	{
		texture = new Texture("platform.jpg");
		sprite = new Sprite(texture);
		sprite.setPosition(0, 500);		//hard code position
        
        
        bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set((sprite.getX() + sprite.getWidth()/2) / PIXELS_TO_METERS,
        (sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS);
        
        body = world.createBody(bd);
        
        //creates the bottom platform
        FixtureDef fd = new FixtureDef();
        PolygonShape recShape = new PolygonShape();
        recShape.setAsBox(sprite.getWidth()/2 / PIXELS_TO_METERS, 
        		sprite.getHeight() /2 / PIXELS_TO_METERS);
       
        
        fd.shape = recShape;
//        fd.filter.categoryBits = GameConstants.CATEGORY_PLATFORM;
//        fd.filter.maskBits = GameConstants.MASK_PLATFORM;
        
        body.createFixture(fd);
        recShape.dispose();
		
	}
	
	public void dispose(){
	      texture.dispose();
	}
	
	public Sprite getPlatformSprite() {
		return sprite;
	}
	
	public Body getBody() {
		return body;
	}
}
