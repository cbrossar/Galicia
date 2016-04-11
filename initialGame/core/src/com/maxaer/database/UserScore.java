package com.maxaer.database;

public class UserScore extends Thread
{
   
   private int score;
   private int userID;
   private String uName;
   
   /*
    * Class UserScore
    * Author: Peter Kaminski
    * Purpose: Wrapper for user scores from database
    */
   public UserScore(int id, int score)
   {
      // TODO Auto-generated constructor stub
      this.score = score;
      userID = id;
      uName = "";
      start();
   }
   
   public int getUserID()
   {
      return userID;
   }
   
   public int getScore()
   {
      return score;
   }
   
   @Override
   public String toString()
   {
      // TODO Auto-generated method stub
      return uName + ": " + score;
   }
   
   @Override
   public void run()
   {
      SQLDriver driver = new SQLDriver();
      driver.connect();
      
      uName = driver.getUserByID(userID);
      
      driver.stop();
   }

}
