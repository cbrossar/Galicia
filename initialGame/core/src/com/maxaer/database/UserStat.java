package com.maxaer.database;

public class UserStat
{
   
   private int totalDeaths;
   private int blockDeaths;
   private int lavaDeaths;
   private int distanceTraveled;
   private int highScore;
   
   
   public UserStat(int totalDeaths, int blockDeaths, int lavaDeaths, int distanceTraveled, int highScore)
   {
      this.totalDeaths = totalDeaths;
      this.blockDeaths = blockDeaths;
      this.lavaDeaths = lavaDeaths;
      this.distanceTraveled = distanceTraveled;
      this.highScore = highScore;
   }


   public int getTotalDeaths()
   {
      return totalDeaths;
   }


   public int getBlockDeaths()
   {
      return blockDeaths;
   }


   public int getLavaDeaths()
   {
      return lavaDeaths;
   }


   public int getDistanceTraveled()
   {
      return distanceTraveled;
   }


   public int getHighScore()
   {
      return highScore;
   }


}
