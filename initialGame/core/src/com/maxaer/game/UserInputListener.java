package com.maxaer.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.Body;
import com.maxaer.gameworld.GameWorld;

/*
 * Class: UserInputListener
 * Author: Peter Kaminski
 * Purpose: This class is where any user input will be processed
 *          1. This listener needs to be passed an instance of the GameWorld so it can interact with the player
 */

public class UserInputListener implements InputProcessor
{  
   private GameWorld world;
   private Body player;
   private boolean isRunning;
   //Pass the constructor an instance of our GameWorld so we can manipulate the game objects
   public UserInputListener(GameWorld world)
   {
      this.world = world;
      player = world.getPlayerBody();
      isRunning = true;
   }

   @Override
   public boolean keyDown(int keycode)
   {
	   
	   	if(keycode == Input.Keys.UP && !world.isGameOver()){
	   		if(world.getPlayer().canJump()){
	   			player.applyForceToCenter(0f, -2.5f,true);
	   			world.getPlayer().setJumpability(false);
	   		}
	   	}
	   	
	   	if(keycode == Input.Keys.ENTER && world.isGameOver()){
	   	   world.showMenuScreen(); 
	   	}
	   	
	   	if(keycode == Input.Keys.RIGHT && !world.isGameOver()){   		
	        player.setLinearVelocity(3f, player.getLinearVelocity().y);
	    }
	      
	    if(keycode == Input.Keys.LEFT && !world.isGameOver()){
	        player.setLinearVelocity(-3f,player.getLinearVelocity().y);
	    }
	     
	    return true;
	
   }


   @Override
   public boolean keyUp(int keycode) {
      
      //If the game is over, the user can press space to create a new game session
      if(keycode == Input.Keys.SPACE && world.isGameOver()){
         world.createNewGame(); 
      }
	  
      if(keycode == Input.Keys.P && !world.isGameOver() && isRunning){
    	
    	  world.setRunningWorld(false);
    	  world.getMusicPlayer().pause();
    	  isRunning = false;
      }
      
      else if(keycode == Input.Keys.P && !world.isGameOver() && !isRunning){
    	  world.setRunningWorld(true);
    	  world.getMusicPlayer().play();
    	  isRunning = true;
      }
      
      //Allow the user to pause the music here
      if(keycode == Input.Keys.O && isRunning){
         if(world.getMusicPlayer().isPlaying()) world.getMusicPlayer().pause();
         else world.getMusicPlayer().play();
      }
      
      if(keycode == Input.Keys.I && isRunning){
         world.setGameOver(true);
      }
    
       if(keycode == Input.Keys.RIGHT && !world.isGameOver()){
    	   player.setLinearVelocity(0, player.getLinearVelocity().y * 1f);
       }
       if(keycode == Input.Keys.LEFT && !world.isGameOver())
    	  player.setLinearVelocity(0, player.getLinearVelocity().y * 1f);

       if(keycode == Input.Keys.UP && !world.isGameOver()){
    	   if(player.getLinearVelocity().y == 0)
    		   player.applyForceToCenter(0f, -2.5f,true);
       }
       
          

       return true;
   }


   @Override
   public boolean keyTyped(char character)
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean touchDown(int screenX, int screenY, int pointer, int button)
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean touchUp(int screenX, int screenY, int pointer, int button)
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean touchDragged(int screenX, int screenY, int pointer)
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean mouseMoved(int screenX, int screenY)
   {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public boolean scrolled(int amount)
   {
      // TODO Auto-generated method stub
      return false;
   }
   

}
