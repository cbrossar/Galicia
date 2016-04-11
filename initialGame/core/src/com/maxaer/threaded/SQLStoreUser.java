package com.maxaer.threaded;

<<<<<<< HEAD
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

=======
>>>>>>> master
import com.maxaer.database.SQLDriver;

public class SQLStoreUser extends Thread
{
   
   /*
    * Class SQLStoreUser 
    * Author: Peter Kaminski
    * Purpose: Thread to connect and store a new user in SQL
    */

      private String passHash;
      private String userName;
      public SQLStoreUser(String userName, String password)
      {
         this.userName = userName;
<<<<<<< HEAD
         this.passHash = hashPassword(password);
         
      }
      
      private String hashPassword(String password){

         MessageDigest messageDigest;
         try
         {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            return new String(messageDigest.digest());
         }
         catch (NoSuchAlgorithmException e)
         {
            return "";
         }
      
      }
      
=======
         this.passHash = password;
         
      }
      
>>>>>>> master
      
      @Override
      public void run()
      {
         //On a separate thread add the user to SQL Driver
         SQLDriver driver = new SQLDriver();
         driver.connect();
         driver.addUser(userName, passHash);
         driver.stop();
      }


}
