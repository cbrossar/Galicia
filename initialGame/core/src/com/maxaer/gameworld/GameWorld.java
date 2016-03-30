package com.maxaer.gameworld;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
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
   private Block block;
   
   
   public GameWorld()
   {
      //Initialize the world to have a slight gravitational pull
      world = new World(new Vector2(0, 3f), true);
      player = new Player(world);
      platform = new Platform(world);
      block = new Block(world);
      
  }
      
   public void dispose(){
      world.dispose();
      player.dispose();
      platform.dispose();
   }
   
   public void update(float delta){
      //Any updating for our world should go here
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
   
   public Sprite getBlockSprite()
   {
	   return block.getSprite();
   }
   
   public World getWorld()
   {
      return world;
   }
   
   public Player getPlayer()
   {
      return player;
   }
   
   public Block getBlock()
   {
      return block;
   }
   

}
