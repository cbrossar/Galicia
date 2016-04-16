package com.maxaer.gameworld;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.maxaer.database.User;
import com.maxaer.game.CollisionListener;
import com.maxaer.game.GameWindow;
import com.maxaer.game.UserInputListener;
import com.maxaer.gameobjects.Block;
import com.maxaer.gameobjects.Platform;
import com.maxaer.gameobjects.Player;
import com.maxaer.screens.MenuScreen;
import com.maxaer.server.ServerConstants;


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
   private Rectangle lava, opponent;
   private Sprite background;
   //private Block block;
   private Vector<Block> blocks;
   private float lastDropTime = TimeUtils.nanoTime();
   private float lastHeight = -500;
   private volatile boolean gameOver;
   private boolean justDied;
   private GameWindow window;
   private boolean lavaDeath, blockDeath;
   private Music musicPlayer;
   
   private User user;
   
   private boolean isRunning, isMultiplayer;
   private volatile boolean multiplayerReady, multiplayerFinished;
   private volatile int currentScore;
   private volatile int opponentsScore; 

   //Game speeds
   private int GAME_SPEED;
   private static final int FAST_SPEED = 45;
   private static final int DEFAULT_SPEED = 40;
   private static final int SLOW_SPEED = 35;
   
   
   public GameWorld(GameWindow window, User user, boolean isMultiplayer)
   {
      this.user = user;
      this.window = window;
      this.isMultiplayer = isMultiplayer;
      multiplayerReady = false;
      multiplayerFinished = false;
      createNewGame(); 
      this.musicPlayer = window.getMusicPlayer();
      window.getMusicPlayer().play();
      
      
      //Set up the connection once the player starts playing
      if(isMultiplayer){
         
         //Create the opponent rectangle here
         opponent = new Rectangle(player.getX() + player.getSprite().getWidth() + 30, player.getY(), player.getSprite().getWidth(), player.getSprite().getHeight());
         
         //We need to create a thread for this client so we can communicate with the game server. 
         new Thread(new Runnable(){

             @Override
             public void run() {
                 Socket client = null;
                 DataInputStream is = null;
                 DataOutputStream os = null; 
                 int checkScore = 0; 
                 try
                {
                   //Connect to the server and set up our streams
                   client = new Socket("localhost", 6789);
                   is = new DataInputStream(client.getInputStream());
                   os = new DataOutputStream(client.getOutputStream());
                   
                   //Wait for the signal to start the game here
                   multiplayerReady = is.readBoolean();
                   
                   //Set the initial game status
                   multiplayerFinished = false;
                   float oppX = 0; 
                   float oppY = 0; 
                   
                   /*
                    * There are three conditions where we should continue talking with the server
                    *   1. !gameOver -- meaning we are still alive
                    *   2. checkScore > 0 -- meaning the opponent has not yet died
                    *   3. currentScore > 0 -- this allows us to know exactly when we have died
                    */
                   while(!gameOver || checkScore > 0 || currentScore > 0){
                      /*
                       * Write the information necessary to the server
                       *    1. currentScore
                       *    2. X and Y coordinates of our player
                       */
                      os.writeInt(currentScore);
                      os.writeFloat(player.getSprite().getX());
                      os.writeFloat(player.getSprite().getY());
                      os.flush();
                      
                      /*
                       * Read the necessary information here
                       *    1. Opponents score
                       *    2. Opponent's X Y coordinate
                       */
                      checkScore = is.readInt();
                      oppX = is.readFloat();
                      oppY = is.readFloat();
                      
                      //Update the opponents position to be re-rendered and the score
                      opponent.setPosition(oppX, oppY);
                      
                      
                      //if the score is negative, don't update it.
                      if(checkScore > 0) opponentsScore = checkScore;
                      
                   }
                   //Finally send our final information to the opponent
                   os.writeInt(currentScore);
                   os.writeFloat(player.getSprite().getX());
                   os.writeFloat(player.getSprite().getY());
                   os.flush();
                   
                   
                   System.out.println("Multiplayer finished against" + opponentsScore);
                   //The multiplayer game is now finished. And so is our communication with the server. 
                   multiplayerFinished = true;
                   
                }
                catch (UnknownHostException e)
                {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
                }
                catch (IOException e)
                {
                   
                } finally{
                   if(client != null){
                      try
                      {
                         //clean up
                         client.close();
                         os.close();
                         is.close();
                      }
                      catch (IOException e)
                      {
                         
                      }
                   }
                  
                }
                 
             }
         }).start(); // And, start the thread running
         
        
      }
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
      lastHeight = -500;
      isRunning = true;
      currentScore = 21; 
        
      //Set the input listener for this screen
      Gdx.input.setInputProcessor(new UserInputListener(this));
   }
      
   public void dispose(){
      if(world != null){
    	  world.dispose();
    	  window.getMusicPlayer().dispose();
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
			  int heightToUse = (int) Math.min(lastHeight, player.getSprite().getY()-600);
			  Block b = new Block(world, heightToUse);
			  lastHeight=heightToUse;
			  blocks.add(b);
		  }
		
		  // a bullshit try at this
		  int heightDifference = (int) (player.getY() - lava.getY());
		  
		  //Lava comes after 4.5 so enough time for boxes to fall
		  if(lastDropTime >= 4500000000.0 && lastDropTime <= 25000000000.0){
			  
			  //Update the position of the lava by a few pixels
			  if(heightDifference >= -600)
			  {
				  lava.setPosition(lava.getX(), lava.getY() - (38 * delta));
			  }
			  else{
			     lava.setPosition(lava.getX(), lava.getY() - (35 * delta));
			  }
		  }
		  
		  //Increases difficulty of world through increase of velocity
		  if(lastDropTime > 25000000000.0){
			  
			  if(heightDifference >= -600)
			  {
				  lava.setPosition(lava.getX(), lava.getY() - (35 * delta));
			  }
			  else{
			     lava.setPosition(lava.getX(), lava.getY() - (33 * delta));
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
   
   
   public Rectangle getOpponent()
   {
      return opponent;
   }
   
   public boolean isMultiplayer()
   {
      return isMultiplayer;
   }
   
   public boolean isMultiplayerReady()
   {
      return multiplayerReady;
   }
   
   public boolean isMultiplayerFinished()
   {
      return multiplayerFinished;
   }
   
   public void setCurrentScore(int currentScore)
   {
      this.currentScore = currentScore;
   }
   
   
   public int getOpponentsScore()
   {
      return opponentsScore;
   }
   
   

}
