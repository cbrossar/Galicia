package com.maxaer.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Block {
	private Sprite sprite;
	private Body body;
	private Texture texture;   
	final float PIXELS_TO_METERS = 100f;
	
	public Block(World world)
	{
		//Create the player to have the block image
		texture = new Texture("thinmint2.png");
		sprite = new Sprite(texture);
		//Initialize with position in the middle of the screen
	  
		sprite.setPosition(Gdx.graphics.getWidth()/6, 0);
	  
		//Set the body definition for the player
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.fixedRotation = true;
		bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) / PIXELS_TO_METERS,
              (sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS);
      
		//Create the body for the player
		body = world.createBody(bodyDef);
		body.setGravityScale(0);
		
		body.setLinearVelocity(0, 2f);
      
		//Create the shape for our player
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(sprite.getWidth()/2 / PIXELS_TO_METERS, sprite.getHeight()/2 / PIXELS_TO_METERS);
		//Now add that shape to the body
		FixtureDef playerDef = new FixtureDef();
		playerDef.shape = shape;
		playerDef.density = 0.02f;
		playerDef.restitution = 0f;
		playerDef.friction = 0f;
      
		body.createFixture(playerDef);
		//Free up the shape here
		shape.dispose();
		
	}
	
	public void updatePosition(){
	      sprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) - sprite.
	            getWidth()/2 ,
	    (body.getPosition().y * PIXELS_TO_METERS) - sprite.getHeight()/2 );
	   }
	
	public Sprite getSprite(){
	      return sprite;
    }
	
	public Body getBody()
	{
		return body;
	}

}
