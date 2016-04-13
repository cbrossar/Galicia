package com.maxaer.threaded;

import com.maxaer.database.SQLDriver;
import com.maxaer.database.User;

//Separate thread used to update user high scores
public class SQLStatUpdater extends Thread
{
   private int scoreToAdd;
   private int userID;
   private User user;
   public SQLStatUpdater(User user, int score){
      scoreToAdd = score;
      this.userID = user.getUserID();
      this.user = user;
   }
   
   //Run the SQL driver on a separate thread
   @Override
   public void run()
   {
      SQLDriver driver = new SQLDriver();
      driver.connect();
      System.out.println("Adding score from " + userID + " " + scoreToAdd);
      driver.addTopScore(userID, scoreToAdd);
      
      driver.updateUserStats(user);
      
      driver.stop();
   }

}
