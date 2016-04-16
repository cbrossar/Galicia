package com.maxaer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.maxaer.database.User;

import com.maxaer.game.GameWindow;
import com.maxaer.gameworld.GameRenderer;
import com.maxaer.gameworld.GameWorld;

/*
 * Class: GameScreen
 * Author: Peter Kaminski
 * Purpose: GameScreen serves as the main game screen for maxaer
 *          1. The game screen contains a GameWorld and a GameRenderer
 *              a. GameWorld is responsible for creating all the bodies in the world
 *              b. GameRenderer is responsible for handling all rendering in the game for this screen
 */
public class GameScreen implements Screen
{
   
   private GameWorld world;
   private GameRenderer renderer;
   
   public GameScreen(GameWindow window, User user){
      world = new GameWorld(window, user, 0);
      renderer = new GameRenderer(world);
   }

   @Override
   public void resize(int width, int height) {
   }

   @Override
   public void show() {
   }

   @Override
   public void hide() {    
   }

   @Override
   public void pause() {       
   }

   @Override
   public void resume() {      
   }

   @Override
   public void dispose() {
      //Clean up clean up
       world.dispose();
   }

   /*
    * Render is a very important function. It is called roughly 60 times per second, and it handles the updating of the screen
    *   1. Render calls world.update() to update the bodies in the world
    *   2. Render calls the render function for the GameRenderer--passing off game rendering to this class
    *   3. This render function is called from Game's render function. So it will be called when the game GameObject begins execution
    */
   
   @Override
   public void render(float delta)
   {
      world.update(delta); 
      boolean wasPaused = false;
      if(!world.getRunningWorld()){
    	  Gdx.graphics.setContinuousRendering(false);
    	  wasPaused = true;
    	  renderer.renderPauseScreen();
      }
      else if(wasPaused)
      {
    	  wasPaused = false;
    	  Gdx.graphics.requestRendering();
    	  renderer.falseRenderPauseScreen();
      }
      else {
    	  Gdx.graphics.requestRendering();
    	  renderer.render();  
      }
   }


}
