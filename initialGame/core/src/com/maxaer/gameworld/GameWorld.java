package com.maxaer.gameworld;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.maxaer.game.CollisionListener;
import com.maxaer.game.UserInputListener;
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
   private Rectangle lava;
   private Sprite background;
   //private Block block;
   private Vector<Block> blocks;
   private float lastDropTime = TimeUtils.nanoTime();
   float lastHeight = -500;
   private boolean gameOver;
   
   
   public GameWorld()
   {
      createNewGame(); 
  
  }
   
   public void createNewGame(){
      //Get rid of any preexisting components
      dispose();
      //Initialize the world to have a slight gravitational pull
      world = new World(new Vector2(0, 3f), true);
      world.setContactListener(new CollisionListener(this));
      player = new Player(world);
      platform = new Platform(world);
      //Create the lava as a rectangle with the width of the screen and with 
      lava = new Rectangle(0, Gdx.graphics.getHeight() + 200,Gdx.graphics.getWidth(), Gdx.graphics.getHeight() + 1000);
      blocks = new Vector<Block>();
      gameOver = false;
      
      //Set the input listener for this screen
      Gdx.input.setInputProcessor(new UserInputListener(this));
   }
      
   public void dispose(){
      if(world != null) world.dispose();
      if(player != null) player.dispose();
      if(platform != null) platform.dispose();
   }
   
   public void update(float delta){
      //Any updating for our world should go here
	  if(TimeUtils.nanoTime() - lastDropTime > 1000000000.0){
		  lastDropTime = TimeUtils.nanoTime();
		  int heightToUse = (int) Math.min(lastHeight, player.getSprite().getY()-600);
		  Block b = new Block(world, heightToUse);
		  lastHeight=heightToUse;
		  blocks.add(b);
	  }
	  //Update the position of the lava by a few pixels
	  if(lava.getY() > player.getSprite().getY() - (Gdx.graphics.getHeight()/2))
	     lava.setPosition(lava.getX(), lava.getY() - (30 * delta));
	  
	  
   }
   
   public Sprite getBackground()
   {
      return background;
   }
   
   public Rectangle getLava()
   {
      return lava;
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
   
   public void setGameOver(boolean gameOver)
   {
      this.gameOver = gameOver;
   }
   
   public boolean isGameOver()
   {
      return gameOver;
   }
   
   
   

}
