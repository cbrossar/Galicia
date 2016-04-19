package com.maxaer.gameworld;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
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
import com.maxaer.server.ServerConstants;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;


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
   private volatile boolean gameOver;

   private boolean justDied;
   private GameWindow window;
   private boolean lavaDeath, blockDeath;
   private Music musicPlayer;
   private volatile Random rand;
   
   private User user;
   private volatile String opponentID;
   private String userID;
   
   private boolean createdNewGame;

   private boolean isRunning, isMultiplayer;
   private volatile boolean multiplayerReady, multiplayerFinished;
   private volatile int currentScore;
   private volatile int opponentsScore;
   
   private int lastHeight = 0;

   final float PIXELS_TO_METERS = GameConstants.PIXEL_TO_METERS;
      
   public GameWorld(GameWindow window, User user, int seed, boolean isMultiplayer)
   {
      this.user = user;
      this.window = window;
      this.isMultiplayer = isMultiplayer;
      multiplayerReady = false;
      multiplayerFinished = false;
      this.rand = new Random(seed);
      this.musicPlayer = window.getMusicPlayer();
      this.userID = user.getUserName();
      this.opponentID = "";

      createNewGame(); 
      createdNewGame = false;

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
                   client = new Socket("104.131.153.145", 6789);
                   is = new DataInputStream(client.getInputStream());
                   os = new DataOutputStream(client.getOutputStream());
                   
                   //Wait for the signal to start the game here
                   multiplayerReady = is.readBoolean();
                   rand = new Random(is.readInt());
                   for(Block b: blocks){
                      world.destroyBody(b.getBody());
                   }
                   blocks.clear();
                   //Write out our user name
                   os.writeUTF(userID);
                   os.flush();
                   
                   opponentID = is.readUTF();
                   
                   //Play the music once we are ready to go
                   getMusicPlayer().play();
                   //Set the initial game status
                   multiplayerFinished = false;
                   float oppX = 0; 
                   float oppY = 0; 
                   
                   /*
                    * While the opponent's score is greater than 0, they are not dead, so get their positioning
                    */
                   while(!multiplayerFinished){
                      
                      //Read from the server if the multiplayer code is finished or not
                      multiplayerFinished = is.readBoolean();
                      
                      if(multiplayerFinished) break; // Then end the server code here
                      
                      /*
                       * Write the information necessary to the server
                       *    1. If we are dead or alive
                       *    2. currentScore
                       *    3. X and Y coordinates of our player
                       */
                      if(gameOver){
                         os.writeInt(ServerConstants.IS_DEAD);
                      } else{
                         os.writeInt(ServerConstants.IS_ALIVE);
                      }
                      
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
         
        
      } else if(user.getMusic()){ 
         //start the music for singleplayer at the time of playing
         window.getMusicPlayer().play();
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
      lava = new Rectangle(0, Gdx.graphics.getHeight() + 400,Gdx.graphics.getWidth(), Gdx.graphics.getHeight() + 1000);
      blocks = new Vector<Block>();
      gameOver = false;
      justDied = true;
      lavaDeath = false;
      blockDeath = false;
      isRunning = true;
      createdNewGame = true;
      currentScore = 21; 
      
      //Set the input listener for this screen
      Gdx.input.setInputProcessor(new UserInputListener(this));
   }
      
   public void dispose(){
      if(world != null){
    	  world.dispose();

    	  //window.getMusicPlayer().dispose();

      }
      if(player != null) player.dispose();
      if(platform != null) platform.dispose();
   }

   public void setCreatedGame(boolean created) {
	   createdNewGame = created;
   }
   
   public boolean getCreatedGame() {
	   return createdNewGame;
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
			  int heightToUse = (int) Math.min(lastHeight, Math.min(player.getSprite().getY()-800, lava.getY()-1600));
			  lastHeight = heightToUse;
			  Block b = new Block(world, heightToUse, rand);
			  if(user.getDifficulty() == 1) {
				  b.getBody().setLinearVelocity(0, 4f);
				  float speed = rand.nextInt(2);
				  b.getBody().setLinearVelocity(0, 4f - speed);
			  } else if (user.getDifficulty() == 2) {
				  float speed = rand.nextInt(3);
				  b.getBody().setLinearVelocity(0, 6f - speed);
			  } else {
				  float speed = rand.nextInt(4);
				  b.getBody().setLinearVelocity(0, 9f - speed);
			  }
			  
			  
			  blocks.add(b);
		  }		

		  float heightDifference = (player.getY() - lava.getY()/PIXELS_TO_METERS);
 		  
		  
		  if(lastDropTime > 25000000000.0){

			      if(heightDifference <= -10)
	              {
	                  if(user.getDifficulty() == 1) {
	                      lava.setPosition(lava.getX(), lava.getY() - (60 * delta));
	                  }
	                  else  if(user.getDifficulty() == 2) {
	                      lava.setPosition(lava.getX(), lava.getY() - (63 * delta));
	                  }
	                  else {
	                      lava.setPosition(lava.getX(), lava.getY() - (66 * delta));
	                  }
	                  
	              }
	              else{
	                  if(user.getDifficulty() == 1) {

	                      lava.setPosition(lava.getX(), lava.getY()- (38 * delta));
	                  }
	                  else if(user.getDifficulty() == 2) {
	                      lava.setPosition(lava.getX(), lava.getY() - (40 * delta));
	                  }
	                  else {

	                      lava.setPosition(lava.getX(), lava.getY() - (42 * delta));
	                  }
	                 
	              }
			  			 
		  }
	   }

   }
   
   //Method to start the menu screen from game
   public void showMenuScreen(){

      window.setScreen(new MenuScreen(window, user));
      this.dispose();
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
   
   public String getOpponentID()
   {
      return opponentID;
   }

   public void setLastHeight(int i) {
		lastHeight = i;
   }

   
   

}
