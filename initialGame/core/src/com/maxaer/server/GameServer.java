package com.maxaer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class GameServer
{
   
   private Vector<MultiplayerThread> clients;
   
   public GameServer(int port)
   {
      //Create the game server 
      ServerSocket socket = null;
      clients = new Vector<MultiplayerThread>(); 

      try
      {
         socket = new ServerSocket(port);
         //Now accept clients
         while(true){
            //Accept two players
            System.out.println("Waiting for player 1...");
            Socket client1 = socket.accept();
            System.out.println("Player 1 connected.");
            
            System.out.println("Waiting for player 2...");
            Socket client2 = socket.accept();
            System.out.println("Player 2 connected.");
            //We won't start the multiplayer thread until the second user accepts our connection. 
            MultiplayerThread st = new MultiplayerThread(client1, client2, this);
            clients.add(st);
         }
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally{
         if(socket != null){

            try
            {
               socket.close();
            }
            catch (IOException e)
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }


   
   }
   
   //Function to delete threads
   public void removeMultiplayerThread(MultiplayerThread thread){
      clients.remove(thread);
   }
   
   
   public static void main(String[] args)
   {
      new GameServer(6789);
   }

}
