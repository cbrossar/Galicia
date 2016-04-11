package com.maxaer.threaded;

import com.maxaer.database.SQLDriver;

//Separate thread used to update user high scores
public class SQLScoreUpdater extends Thread
{
   int scoreToAdd;
   int userID;
   public SQLScoreUpdater(int userID, int score){
      scoreToAdd = score;
      this.userID = userID;
   }
   
   //Run the SQL driver on a separate thread
   @Override
   public void run()
   {
      SQLDriver driver = new SQLDriver();
      driver.connect();
      
      driver.addTopScore(userID, scoreToAdd);
      
      driver.stop();
   }

}
