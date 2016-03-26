package com.maxaer.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.maxaer.gameobjects.Player;

/*
 * Class: GameWorld
 * Author: Peter Kaminski
 * Purpose: GameWorld creates the world for our game. 
 *          *********1. All bodies should be created here*********
 *          2. Update will be called, handle all the body updating in here, such as position changing
 *          
 */
public class GameWorld
{
   
   //Create a player and a world
   private Player player;
   private World world;
   private final float PIXELS_TO_METERS = 100f;
   
   public GameWorld()
   {
      //Initialize the world to have a slight gravitational pull
      world = new World(new Vector2(0, 3f), true);
      player = new Player(world);
      createPlatform(); 
      
   }
   
   private void createPlatform(){
      
      float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
      //The height will be starting at the bottom of the screen, 
      float h = (Gdx.graphics.getHeight()/PIXELS_TO_METERS) - (10/PIXELS_TO_METERS);
      
      //Our platform will be static
      BodyDef bodyDef2 = new BodyDef();
      bodyDef2.type = BodyDef.BodyType.StaticBody;
      //Set at the bottom of the screen, plus a padding of 10 px
      bodyDef2.position.set(0,h);
      
      FixtureDef fixtureDef2 = new FixtureDef();
      PolygonShape edgeShape = new PolygonShape();
      edgeShape.setAsBox(w, 25/PIXELS_TO_METERS);
      fixtureDef2.shape = edgeShape;
      
      
      Body bodyEdgeScreen = world.createBody(bodyDef2);
      bodyEdgeScreen.createFixture(fixtureDef2);
      edgeShape.dispose();
      
   }
   
   public void dispose(){
      world.dispose();
      player.dispose();
   }
   
   public void update(float delta){
      //Any updating for our world should go here
   }
   
   public Body getPlayerBody(){
      return player.getPlayerBody();
   }
   
   public Sprite getPlayerSprite()
   {
      return player.getPlayerSprite();
   }
   
   public World getWorld()
   {
      return world;
   }
   
   public Player getPlayer()
   {
      return player;
   }
   

}
