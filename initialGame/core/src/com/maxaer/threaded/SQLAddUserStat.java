package com.maxaer.threaded;


import com.maxaer.database.SQLDriver;

public class SQLAddUserStat extends Thread
{
   
   /*
    * Class SQLStoreUser 
    * Author: Peter Kaminski
    * Purpose: Thread to connect and store a new user in SQL
    */
      private String userName;
      public SQLAddUserStat(String userName)
      {
         this.userName = userName;
      }
      
      
      @Override
      public void run()
      {
         //On a separate thread add the user to SQL Driver
         SQLDriver driver = new SQLDriver();
         driver.connect();
         int userID = driver.getUserByName(userName);
         driver.createUserStats(userID);
         driver.stop();
      }


}
