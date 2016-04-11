package com.maxaer.game;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.maxaer.gameobjects.Block;
import com.maxaer.gameworld.GameWorld;

/*
 * Class: CollisionListener
 * Author: Peter Kaminski
 * Purpose: CollisionListener is what will do most of the heavy lifting for our collision detection
 */
public class CollisionListener implements ContactListener
{
   GameWorld world;
   public CollisionListener(GameWorld world)
   {
      this.world = world;
   }

   @Override
   public void beginContact(Contact contact)
   {
      //Get the two objects that are colliding
      Fixture obj1 = contact.getFixtureA();
      Fixture obj2 = contact.getFixtureB();
      
    //Death by blocks attempt #2      
      Vector<Block> v = world.getBlocks();
      
      //check all bottom blocks for death conditions
      for(int i = 0; i < v.size(); i++) {
    	  if(obj2.getBody().equals(v.get(i).getBottomBlock())) {  
    		  if(obj1.getBody().equals(world.getPlayerBody())) {
    			  Vector2 vbottom = v.get(i).getBottomBlock().getPosition();
    			  Vector2 vplayer = world.getPlayerBody().getPosition();
    			  if(world.getPlayer().canJump() && ((vplayer.y - vbottom.y) > .1)) {
    				  world.setGameOver(true);
    				  System.out.println("Death by block crush! Block #" + i);
				  }
    		  }
    		  else {
    			  for(int j = 0; j < v.size(); j++) {
    				  if(obj1.getBody().equals(v.get(i).getBody())) {
    					  world.addToBottomBlocksInactive(v.get(i).getBottomBlock());
    				  }
    			  }
    			  
    		  }
    	  } else if (obj2.getBody().equals(v.get(i).getBody())) {
    		  if(!obj1.getBody().equals(world.getPlayerBody())) {
    			  v.get(i).getBottomBlock().setLinearVelocity(0f, 0f);
    			  world.addToBottomBlocksInactive(v.get(i).getBottomBlock());
    			  
    		  }
    		  	
    	  }
      }
      
      
      //The player is here--do something with it
      if(obj1.getBody().equals(world.getPlayerBody())){
    	  for(int i = 0; i < v.size(); i++) {
        	  if(!obj2.getBody().equals(v.get(i).getBottomBlock())) {  
        		  world.getPlayer().setJumpability(true);
        	  }
    	  }
          //Gdx.app.error("Collision", "Collision with player in 2");        
      }
      
      //It's here--do something with it
      else if(obj2.getBody().equals(world.getPlayerBody())){
    	 world.getPlayer().setJumpability(true);
    	  
         Gdx.app.log("Player", "We have the player in obj2");
         obj1.getBody().setLinearVelocity(0, 0);
      } 
      
      
      //Both objects are blocks (may have to adjust this if we add more classes with multiple objects)
      else if(obj1.getClass().equals(obj2.getClass())) {
    	  obj1.getBody().setAwake(false);
    	  obj2.getBody().setAwake(false);
      }
      
   }

   @Override
   public void endContact(Contact contact)
   {
//    //Get the two objects that are colliding
//      Fixture obj1 = contact.getFixtureA();
//      Fixture obj2 = contact.getFixtureB();
      
   }

   @Override
   public void preSolve(Contact contact, Manifold oldManifold)
   {
      // TODO Auto-generated method stub
	 //Get the two objects that are colliding
	      Fixture obj1 = contact.getFixtureA();
	      Fixture obj2 = contact.getFixtureB();
      
	      
   }

   @Override
   public void postSolve(Contact contact, ContactImpulse impulse)
   {
      // TODO Auto-generated method stub
      
   }
   

}
