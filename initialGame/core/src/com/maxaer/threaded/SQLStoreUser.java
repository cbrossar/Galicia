package com.maxaer.threaded;

import com.maxaer.database.SQLDriver;

public class SQLStoreUser extends Thread
{
   
   private String userName;
   private String passHash;
   public SQLStoreUser(String userName, String passHash)
   {
      this.userName = userName;
      this.passHash = passHash;
   }
   
   @Override
   public void run()
   {
      SQLDriver driver = new SQLDriver();
      driver.connect();
      driver.addUser(userName, passHash);
      driver.stop();
      
   }

}
