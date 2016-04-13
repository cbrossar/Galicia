package com.maxaer.threaded;

import com.maxaer.database.SQLDriver;
import com.maxaer.database.User;
import com.maxaer.screens.UserStatsScreen;

public class SQLUserStatRetriever extends Thread
{
   
   private UserStatsScreen statScreen;
   private User user; 
   public SQLUserStatRetriever(UserStatsScreen screen)
   {
      this.statScreen = screen;
      user = statScreen.getUser(); 
      start(); 
   }
   
   @Override
   public void run()
   {
      SQLDriver driver = new SQLDriver();
      driver.connect();
      
      
      driver.stop();
   }

}
