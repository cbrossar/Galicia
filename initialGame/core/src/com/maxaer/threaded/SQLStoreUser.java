package com.maxaer.threaded;


import com.maxaer.database.SQLDriver;

public class SQLStoreUser extends Thread
{
   
   /*
    * Class SQLStoreUser 
    * Author: Peter Kaminski
    * Purpose: Thread to connect and store a new user in SQL
    */

      private String passHash;
      private String userName;
      public SQLStoreUser(String userName, String password)
      {
         this.userName = userName;
         this.passHash = password;
         
      }
      
      
      @Override
      public void run()
      {
         //On a separate thread add the user to SQL Driver
         SQLDriver driver = new SQLDriver();
         driver.connect();
         driver.addUser(userName, passHash);
         driver.stop();
      }


}
