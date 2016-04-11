package com.maxaer.threaded;


import com.maxaer.database.SQLDriver;
import com.maxaer.screens.HighScoreScreen;

/*
 * Class SQLHighScoreRetriever 
 * Author: Peter Kaminski
 * Purpose: Thread to connect and retrieve top scores from SQL
 */
public class SQLHighScoreRetriever extends Thread
{
   
   private HighScoreScreen screen;
   public SQLHighScoreRetriever(HighScoreScreen screen)
   {
      this.screen = screen;
   }
   
   @Override
   public void run()
   {
      SQLDriver driver = new SQLDriver();
      driver.connect();
      screen.setTopScores(driver.getTopScores());
      driver.stop();
   }

}
