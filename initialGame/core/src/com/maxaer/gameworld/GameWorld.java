package com.maxaer.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
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
   private Body platform;
   
   public GameWorld()
   {
      //Initialize the world to have a slight gravitational pull
      world = new World(new Vector2(0, 0.0f), true);
      player = new Player(world);
      createPlatform(); 
      
   }
   
   private void createPlatform(){
    //Now create our platform shape here
      BodyDef bodyDef2 = new BodyDef();
      bodyDef2.type = BodyDef.BodyType.StaticBody;
      float w = Gdx.graphics.getWidth()/100f;
      // Set the height to just 50 pixels above the bottom of the screen so we can see the edge in the
      // debug renderer
      float h = Gdx.graphics.getHeight()/100f- 50/100f;
      //bodyDef2.position.set(0,
//              h-10/PIXELS_TO_METERS);
      bodyDef2.position.set(0,0);
      FixtureDef fixtureDef2 = new FixtureDef();

      EdgeShape edgeShape = new EdgeShape();
      edgeShape.set(-w/2,-h/2,w/2,-h/2);
      fixtureDef2.shape = edgeShape;

      platform = world.createBody(bodyDef2);
      platform.createFixture(fixtureDef2);
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
