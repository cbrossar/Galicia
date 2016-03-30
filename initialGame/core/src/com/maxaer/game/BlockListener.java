package com.maxaer.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/*
 * Class: BlockListener
 * Author: Peter Kaminski
 * Purpose: Provides a contact listener interface for block objects
 */
public class BlockListener implements ContactListener
{

   @Override
   public void beginContact(Contact contact)
   {
	  
      
   }

   @Override
   public void endContact(Contact contact)
   {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void preSolve(Contact contact, Manifold oldManifold)
   {
      // TODO Auto-generated method stub
      
   }

   @Override
   public void postSolve(Contact contact, ContactImpulse impulse)
   {
      // TODO Auto-generated method stub
      
   }

}
