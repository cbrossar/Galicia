package com.maxaer.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player
{
   private Sprite sprite;
   private Body body;
   private Texture texture;
   private BodyDef bodyDef;
   
   final float PIXELS_TO_METERS = 100f;
   
   public Player(World world)
   {
      //Create the player to have the player image
      texture = new Texture("thinmint2.png");
      sprite = new Sprite(texture);
      //Initialize with position in the middle of the screen
     
      Gdx.app.log("Width", " " + Gdx.graphics.getWidth());
      Gdx.app.log("Height", " " + Gdx.graphics.getHeight());
      Gdx.app.log("originx", " " + sprite.getOriginX());
      Gdx.app.log("originy", + sprite.getOriginY() + " ");
      Gdx.app.log("height", + sprite.getHeight() + " ");
      Gdx.app.log("width", + sprite.getWidth() + " ");
      sprite.setPosition(Gdx.graphics.getWidth()/2 - sprite.getWidth()/2, 
            (Gdx.graphics.getHeight() - sprite.getHeight()));
      
      //Set the body definition for the player
      BodyDef bodyDef = new BodyDef();
      bodyDef.type = BodyDef.BodyType.DynamicBody;
      bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) /PIXELS_TO_METERS,
              (sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS);
      
      //Create the body for the player
      body = world.createBody(bodyDef);
      
      //Create the shape for our player
      PolygonShape shape = new PolygonShape();
      shape.setAsBox(sprite.getWidth()/2 / PIXELS_TO_METERS, sprite.getHeight()
              /2 / PIXELS_TO_METERS);
      //Now add that shape to the body
      FixtureDef playerDef = new FixtureDef();
      playerDef.shape = shape;
      playerDef.density = 0.1f;
      playerDef.restitution = 0f;
      body.createFixture(playerDef);
      //Free up the shape here
      shape.dispose();
   }
   
   public Sprite getPlayerSprite(){
      return sprite;
   }
   
   public Body getPlayerBody()
   {
      return body;
   }
   
   public float getX(){
      return body.getPosition().x;
   }
   
   public float getY(){
      return body.getPosition().y;
   }
   
   //Clean up player here
   public void dispose(){
      texture.dispose();
   }
   
   //Update the position of the sprite based on the body's current position. 
   public void updatePlayerPosition(){
      sprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) - sprite.
            getWidth()/2 ,
    (body.getPosition().y * PIXELS_TO_METERS) - sprite.getHeight()/2 );
   }
   
   public BodyDef getBodyDef()
   {
      return bodyDef;
   }

}
