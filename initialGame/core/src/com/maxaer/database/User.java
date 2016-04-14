package com.maxaer.database;


public class User extends Thread
{
   
   private boolean isGuest;
   private volatile int userID;
   private String userName;
   private String passHash;
   private int deathCount;
   private int highScore;
   private int lavaDeaths;
   private int smushDeaths;
   private int totalDistanceTraveled;
   
   public User(String userName, String passHash, boolean isGuest)
   {
      this.userName = userName;
      this.passHash = passHash;
      this.isGuest = isGuest;
      userID = 1;
      deathCount = 0; 
      highScore = 0; 
      lavaDeaths = 0; 
      smushDeaths = 0; 
      totalDistanceTraveled = 0; 
      
      start();
   }
   
   //Update the stats for the user after they have died
   public void updateStats(int distanceTraveled, boolean deathByLava, boolean deathByCollision){
      this.totalDistanceTraveled += distanceTraveled;
      this.deathCount++;
      if(deathByLava) this.lavaDeaths++;
      if(deathByCollision) this.lavaDeaths++;
   }
   
   public int getLavaDeaths()
   {
      return lavaDeaths;
   }
   
   public int getHighScore()
   {
      return highScore;
   }
   
   public int getSmushDeaths()
   {
      return smushDeaths;
   }
   
   public int getDeathCount()
   {
      return deathCount;
   }
   
   public int getTotalDistanceTraveled()
   {
      return totalDistanceTraveled;
   }
   
   public void setLavaDeaths(int lavaDeaths)
   {
      this.lavaDeaths = lavaDeaths;
   }
   
   public void setHighScore(int highScore)
   {
      this.highScore = highScore;
   }
   
   public void setSmushDeaths(int smushDeaths)
   {
      this.smushDeaths = smushDeaths;
   }
   
   public void setDeathCount(int deathCount)
   {
      this.deathCount = deathCount;
   }
   
   public void setTotalDistanceTraveled(int totalDistanceTraveled)
   {
      this.totalDistanceTraveled = totalDistanceTraveled;
   }
   
   public boolean isGuest()
   {
      return isGuest;
   }
   
   public void setGuest(boolean isGuest)
   {
      this.isGuest = isGuest;
   }
   
   public String getPassHash()
   {
      return passHash;
   }
   
   public String getUserName()
   {
      return userName;
   }
   
   public int getUserID()
   {
      return userID;
   }
   
   @Override
   public void run()
   {
      SQLDriver driver = new SQLDriver();
      driver.connect();
      userID = driver.getUserByName(userName);
      driver.stop();
      
      
   }

}
