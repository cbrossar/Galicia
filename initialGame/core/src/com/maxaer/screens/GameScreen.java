package com.maxaer.screens;

import com.badlogic.gdx.Screen;
<<<<<<< HEAD
=======
import com.maxaer.database.User;
>>>>>>> master
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
   
<<<<<<< HEAD
   public GameScreen(GameWindow window){
      world = new GameWorld(window);
=======
   public GameScreen(GameWindow window, User user){
      world = new GameWorld(window, user);
>>>>>>> master
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
      renderer.render();   
   }


}
