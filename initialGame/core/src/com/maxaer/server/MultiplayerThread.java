package com.maxaer.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MultiplayerThread extends Thread
{
   
   private DataOutputStream p1OutStream, p2OutStream;
   private DataInputStream p1InStream, p2InStream;
   
   /*
    * Class: MultiplayerThread
    * Author: Peter Kaminski
    * Purpose: A middle man between two game clients that receieves their X and Y coordinates, sends them to the other user, 
    *   and keeps track of multiplayer games
    */
   public MultiplayerThread(Socket player1, Socket player2)
   {
      
      try
      {
         /*
          * Set up our server thread here. Our server thread will only need to do three things
          *     1. Send user's location
          *     2. Receive enemies location
          *     3. Send game over true/false
          */
         //Get connections to the clients
         p1OutStream = new DataOutputStream(player1.getOutputStream());
         p1InStream = new DataInputStream(player1.getInputStream());
         
         p2OutStream = new DataOutputStream(player2.getOutputStream());
         p2InStream = new DataInputStream(player2.getInputStream());
         this.start();
         
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         System.out.println("Error creating the streams in the multiplayer thread");
      }
   }
   
   
   
   @Override
   public void run()
   {
      //The first time we run, let the user's know the game should begin
      try
      {
         p1OutStream.writeBoolean(true);
         p2OutStream.writeBoolean(true);
         
         p1OutStream.flush();
         p2OutStream.flush();
      }
      catch (IOException e1)
      {
         // TODO Auto-generated catch block
         System.out.println("Error starting the game ");
         
      }
      
      boolean gameRunning = true;
      //Store user positions
      float p1X = 0; float p1Y = 0;
      float p2X = 0; float p2Y = 0; 
      int p1Score = 0; int p2Score = 0;
      
      //The server thread will just receieve information, send information, and then 
      while(gameRunning){
         try
         {
            
            //Only read from the streams if they are available to use
            if(p1InStream.available() > 0 && p2InStream.available() > 0){
               
               /*
                * Read the necessary information
                */
               p1Score = p1InStream.readInt();
               p1X = p1InStream.readFloat();
               p1Y = p1InStream.readFloat();
               
               p2Score = p2InStream.readInt();
               p2X = p2InStream.readFloat();
               p2Y = p2InStream.readFloat();

               /*
                * Write the information 
                */
               p2OutStream.writeInt(p1Score);
               p2OutStream.writeFloat(p1X);
               p2OutStream.writeFloat(p1Y);
               p2OutStream.flush();


               p1OutStream.writeInt(p2Score);
               p1OutStream.writeFloat(p2X);
               p1OutStream.writeFloat(p2Y);
               p1OutStream.flush();

               
            }

           
          

         }
         catch (IOException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } finally{
            try
            {
               p1InStream.close();
               p1OutStream.close();
               p2InStream.close();
               p2OutStream.close();
            }
            catch (IOException e)
            {
               // TODO Auto-generated catch block
               System.out.println("Error closing streams");
            }
            
         }
      }
   }

}
