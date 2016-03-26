package com.maxaer.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/*
 * Class: Game
 * Author: Peter Kaminski
 * Purpose: Game is the top level class in our game application
 *          1. Game implements the ApplicationListener to interact with the lifecycle methods (dispose, pause, resume, render, etc)
 *          2. Game is abstract, as it does not implement the create method. 
 *          3. This class will serve as the base for any Game created
 *          4. setScreen(Screen screen) is the most important method. To change the screen for a game, do that here
 */
public abstract class Game implements ApplicationListener
{
   
   //Each game will have one screen at a time
   private Screen screen;
   
   /*
    * These methods just perform basic lifecycle tasks
    */
   
   
   @Override
   public void dispose () {
       if (screen != null) screen.hide();
   }

   @Override
   public void pause () {
       if (screen != null) screen.pause();
   }

   @Override
   public void resume () {
       if (screen != null) screen.resume();
   }

   @Override
   public void render () {
       if (screen != null) screen.render(Gdx.graphics.getDeltaTime());
   }

   @Override
   public void resize (int width, int height) {
       if (screen != null) screen.resize(width, height);
   }

   
   /*
    * Method available the change the screen
    */
   public void setScreen(Screen screen)
   {
      //Hide the current screen
      if(this.screen != null){
         this.screen.hide();
      } 
      
      //Now update the screen 
      this.screen = screen;
      if(this.screen != null){
         this.screen.show();
         this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      }
      
      
   }
   
   public Screen getScreen()
   {
      return screen;
   }

}
