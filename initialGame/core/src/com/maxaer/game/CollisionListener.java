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
public class CollisionListener implements ContactListener {
	GameWorld world;

	public CollisionListener(GameWorld world) {
		this.world = world;
	}

   @Override
   public void beginContact(Contact contact)
   {
      //Get the two objects that are colliding
      Fixture obj1 = contact.getFixtureA();
      Fixture obj2 = contact.getFixtureB();
      
      if(obj2.getBody().equals(world.getPlatform().getBody())) {
    	  if(obj1.getBody().equals(world.getPlayerBody())) {
    		  world.getPlayer().setOnTopOfSurface(true);
    	  }
      }
      
      //Death by blocks attempt #2      
      Vector<Block> v = world.getBlocks();
      
      //check all bottom blocks for death conditions
      for(int i = 0; i < v.size(); i++) {
    	  if(obj2.getBody().equals(v.get(i).getBody())) {  
    		  if(obj1.getBody().equals(world.getPlayerBody())) {
    			  Vector2 vfalling = v.get(i).getBody().getPosition();
    			  Vector2 vplayer = world.getPlayerBody().getPosition();
    			  double yDiff = vplayer.y - vfalling.y;
    			  double xDiff = vplayer.x - vfalling.x;
//    			  System.out.println("\nvbottom = " + vfalling.x + ", " + vfalling.y);
//    			  System.out.println("vplayer = " + vplayer.x + ", " + vplayer.y);
//    			  System.out.println("vplayer.y - vbottom.y = " + yDiff);
//    			  System.out.println("vplayer.x - vbottom.x = " + xDiff);
    			  
    			  //check if on top of a block and for deaths
    			  if((!v.get(i).isSmall() && yDiff < -.7) || 
    					  (v.get(i).isSmall() && yDiff < -.4)) {
    				  world.getPlayer().setOnTopOfSurface(true);
    			  } else if(world.getPlayer().isOnTopOfSurface()) {
    				  if((!v.get(i).isSmall() && xDiff >= -.75 && xDiff <= .75) ||
    						  (v.get(i).isSmall() && xDiff >= -.45 && xDiff <= .45)) {
    					  world.setGameOver(true);
    				  }	  
    			  }
    		  }
    		  break;
    	  }
      }
      
      //The player is here--do something with it
      if(obj1.getBody().equals(world.getPlayerBody())){
    	  for(int i = 0; i < v.size(); i++) {
    		  world.getPlayer().setJumpability(true); 
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
   public void preSolve(Contact contact, Manifold oldManifold) {

   }

	@Override
	public void endContact(Contact contact) {
		//Get the two objects that are colliding
		 Fixture obj1 = contact.getFixtureA();
		 Fixture obj2 = contact.getFixtureB();
		 		 
		 //Resetting gravity
		 if (obj1.getBody().equals(world.getPlayerBody())) {
			 obj1.getBody().setGravityScale(7);
			 world.getPlayer().setOnTopOfSurface(false);
		 }
			 
		 else if(obj2.getBody().equals(world.getPlayerBody())){
			 obj2.getBody().setGravityScale(7);
			 world.getPlayer().setOnTopOfSurface(false);
		 }
			 
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}

}
