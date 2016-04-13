package com.maxaer.threaded;

import com.maxaer.database.SQLDriver;
import com.maxaer.database.User;
import com.maxaer.database.UserStat;
import com.maxaer.screens.UserStatsScreen;

public class SQLUserStatRetriever extends Thread
{
   
   private UserStatsScreen statScreen;
   private User user; 
   public SQLUserStatRetriever(UserStatsScreen screen)
   {
      this.statScreen = screen;
      user = statScreen.getUser(); 
   }
   
   @Override
   public void run()
   {
      SQLDriver driver = new SQLDriver();
      driver.connect();
      
      UserStat stats = driver.getUserStats(user.getUserID());
      user.setDeathCount(stats.getTotalDeaths());
      user.setTotalDistanceTraveled(stats.getDistanceTraveled());
      user.setLavaDeaths(stats.getLavaDeaths());
      user.setSmushDeaths(stats.getBlockDeaths());
      user.setHighScore(stats.getHighScore());
      
      driver.stop();
   }

}
