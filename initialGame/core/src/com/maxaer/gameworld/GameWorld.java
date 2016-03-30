package com.maxaer.gameworld;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.maxaer.game.CollisionListener;
import com.maxaer.gameobjects.Block;
import com.maxaer.gameobjects.Platform;
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
   private Platform platform;
   //private Block block;
   private Vector<Block> blocks;
   private float lastDropTime = TimeUtils.nanoTime();
   int lastHeight = -100;
   
   
   public GameWorld()
   {
      //Initialize the world to have a slight gravitational pull
      world = new World(new Vector2(0, 3f), true);
      world.setContactListener(new CollisionListener(this));
      player = new Player(world);
      platform = new Platform(world);
      blocks = new Vector<Block>();
  
  }
      
   public void dispose(){
      world.dispose();
      player.dispose();
      platform.dispose();
   }
   
   public void update(float delta){
      //Any updating for our world should go here
	  if(TimeUtils.nanoTime() - lastDropTime > 1000000000.0){
		  lastDropTime = TimeUtils.nanoTime();
		  int heightToUse = (int) Math.min(lastHeight, player.getY()-300);
		  Block b = new Block(world, heightToUse);
		  lastHeight-=20;
		  blocks.add(b);
	  }
   }
   
   public Body getPlayerBody(){
      return player.getBody();
   }
   
   public Sprite getPlayerSprite()
   {
      return player.getSprite();
   }
   
   public Sprite getPlatformSprite()
   {
	   return platform.getPlatformSprite();
   }
   
   public World getWorld()
   {
      return world;
   }
   
   public Player getPlayer()
   {
      return player;
   }
   
   public Vector<Block> getBlocks(){
	   return blocks;
   }
   

}
