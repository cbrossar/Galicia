package com.maxaer.gameworld;

import java.util.Random;
import java.util.Vector;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.maxaer.constants.GameConstants;
import com.maxaer.database.User;
import com.maxaer.game.CollisionListener;
import com.maxaer.game.GameWindow;
import com.maxaer.game.UserInputListener;
import com.maxaer.gameobjects.Block;
import com.maxaer.gameobjects.Platform;
import com.maxaer.gameobjects.Player;
import com.maxaer.screens.MenuScreen;


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
   private boolean gameOver;
   private boolean justDied;
   private GameWindow window;
   private boolean lavaDeath, blockDeath;
   private Music musicPlayer;
   private Random rand;
   
   private User user;
   
   private boolean isRunning;

   //Game speeds
   private int GAME_SPEED;
   private int seed;
   private static final int FAST_SPEED = 45;
   private static final int DEFAULT_SPEED = 40;
   private static final int SLOW_SPEED = 35;
   final float PIXELS_TO_METERS = GameConstants.PIXEL_TO_METERS;
      
   
   public GameWorld(GameWindow window, User user, int seed)
   {
      this.user = user;
      this.window = window;
      this.rand = new Random(seed);
      this.musicPlayer = window.getMusicPlayer();
      if(user.getMusic()) {
    	  window.getMusicPlayer().play();
      }
      createNewGame(); 
      this.seed = seed;
      
  
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
      lava = new Rectangle(0, Gdx.graphics.getHeight() + 300,Gdx.graphics.getWidth(), Gdx.graphics.getHeight() + 1000);
      blocks = new Vector<Block>();
      gameOver = false;
      justDied = true;
      lavaDeath = false;
      blockDeath = false;
      GAME_SPEED = DEFAULT_SPEED;
      isRunning = true;
      
      
      
      //Set the input listener for this screen
      Gdx.input.setInputProcessor(new UserInputListener(this));
   }
      
   public void dispose(){
      if(world != null){
    	  world.dispose();
      }
      if(player != null) player.dispose();
      if(platform != null) platform.dispose();
   }
   
   public Music getMusicPlayer()
   {
      return musicPlayer;
   }
   
   public void setRunningWorld(boolean running)
   {
	   isRunning = running;
   }
   
   public boolean getRunningWorld() {
	   return isRunning;
   }
   public void update(float delta){
	   
 

	   if(isRunning){
		   
      //Any updating for our world should go here
		  if(TimeUtils.nanoTime() - lastDropTime > 1000000000.0){
			  lastDropTime = TimeUtils.nanoTime();
			  
			  int heightToUse = (int) Math.min(player.getSprite().getY()-800, lava.getY()-1300);
			  Block b = new Block(world, heightToUse, rand);
			  blocks.add(b);
		  }		
		  // a bullshit try at this
		  float heightDifference = (player.getY() - lava.getY()/PIXELS_TO_METERS);
		//  System.out.println(heightDifference);
		  
		  
		  if(lastDropTime > 25000000000.0){
			  
			  if(heightDifference <= -10)
			  {
				  lava.setPosition(lava.getX(), lava.getY() - (60 * delta));
			  }
			  else{
			     lava.setPosition(lava.getX(), lava.getY()- (40 * delta));
			  }
		  }
	   }

   }
   
   //Method to start the menu screen from game
   public void showMenuScreen(){

      window.setScreen(new MenuScreen(window, user));
      this.dispose();
   }
   
   public void setFast(){
      GAME_SPEED = FAST_SPEED;
   }
   
   public void setSlow(){
      GAME_SPEED = SLOW_SPEED;
   }
   
   public void setDefault(){
      GAME_SPEED = DEFAULT_SPEED;
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
   
   public Platform getPlatform() {
	   return platform;
   }
   
   public void setGameOver(boolean gameOver)
   {
      this.gameOver = gameOver;
   }
   
   public boolean isGameOver()
   {
      return gameOver;
   }
   
   public boolean isJustDied()
   {
      return justDied;
   }
   
   public void setJustDied(boolean justDied)
   {
      this.justDied = justDied;
   }
   
   public User getUser()
   {
      return user;
   }
   
   public boolean isLavaDeath()
   {
      return lavaDeath;
   }
   
   public boolean isBlockDeath()
   {
      return blockDeath;
   }
   
   public void setBlockDeath(boolean blockDeath)
   {
      this.blockDeath = blockDeath;
   }
   
   public void setLavaDeath(boolean lavaDeath)
   {
      this.lavaDeath = lavaDeath;
   }
   
   
   
   

}
