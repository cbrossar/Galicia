package com.maxaer.threaded;

import com.maxaer.database.SQLDriver;
import com.maxaer.database.User;
import com.maxaer.database.UserStat;

//Separate thread used to update user high scores
public class SQLStatUpdater extends Thread
{
   private int scoreToAdd;
   private int userID;
   private User user;
   private boolean lavaDeath;
   public SQLStatUpdater(User user, int score, boolean lavaDeath){
      scoreToAdd = score;
      this.userID = user.getUserID();
      this.user = user;
      this.lavaDeath = lavaDeath;
   }
   
   //Run the SQL driver on a separate thread
   @Override
   public void run()
   {
      //Connect to SQL and add a top score
      SQLDriver driver = new SQLDriver();
      driver.connect();
      driver.addTopScore(userID, scoreToAdd);
      
      //Then update the user's overall stats
      UserStat stats = driver.getUserStats(user.getUserID());    
      user.setTotalDistanceTraveled(scoreToAdd + stats.getDistanceTraveled());
      user.setLavaDeaths(stats.getLavaDeaths());
      user.setSmushDeaths(stats.getBlockDeaths());
      user.setHighScore(Math.max(user.getHighScore(), stats.getHighScore()));
      user.setDeathCount(stats.getTotalDeaths() + 1);
      
      //If it was a lava death, update that stat here
      if(lavaDeath){
         user.setLavaDeaths(user.getLavaDeaths()+1);
      } else{
         user.setSmushDeaths(user.getSmushDeaths()+1);
      }
     
      driver.updateUserStats(user);
      
      driver.stop();
   }

}
