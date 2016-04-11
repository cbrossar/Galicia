package com.maxaer.database;

public class User extends Thread
{
   
   private boolean isGuest;
   private volatile int userID;
   private String userName;
   private String passHash;
   
   public User(String userName, String passHash, boolean isGuest)
   {
      this.userName = userName;
      this.passHash = passHash;
      this.isGuest = isGuest;
      userID = 1;
      start();
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
