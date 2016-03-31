package com.maxaer.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.Random;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.maxaer.constants.GameConstants;

public class Block extends Shape {
	private Sprite sprite;
	private Body body;
	private Texture texture;   
	final float PIXELS_TO_METERS = GameConstants.PIXEL_TO_METERS;
	private Random rand = new Random();
	private Boolean isSmall = false;
	
	public Block(World world, int height)
	{
		//Create the player to have the block image
		texture = new Texture("thinmint2.png");
		sprite = new Sprite(texture);
		//Initialize with position in the middle of the screen
	  
		//Randomly determine whether block is small or large
		float small = rand.nextFloat();
		if(small <= .50f) isSmall = true;
		if(isSmall) sprite.setSize(sprite.getWidth()/2, sprite.getHeight()/2);
		
		int p = rand.nextInt(800);
		sprite.setPosition(/*Gdx.graphics.getWidth()/6, 0*/ p, height);
	  
		//Set the body definition for the player
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.fixedRotation = true;
		
		//Randomize drop location on screen
		int pos = rand.nextInt(800);
		bodyDef.position.set((pos) / PIXELS_TO_METERS,
              (sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS);
      
		//Create the body for the player
		body = world.createBody(bodyDef);
		body.setGravityScale(0);		
		body.setLinearVelocity(0, 2f);
	
		
      
		//Create the shape for our player
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(sprite.getWidth()/2 / PIXELS_TO_METERS, sprite.getHeight()/2 / PIXELS_TO_METERS);
		//Now add that shape to the body

		FixtureDef boxDef = new FixtureDef();
		boxDef.shape = shape;
		boxDef.density = 10000f;
		boxDef.restitution = 0f;
		boxDef.friction = 0.1f;
	
      
		body.createFixture(boxDef);

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
	
	public void dispose(){
	   texture.dispose();
	}
	
	@Override
	public String toString(){
		return "Block";
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
