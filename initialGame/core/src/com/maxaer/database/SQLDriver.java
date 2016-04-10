package com.maxaer.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Driver;

/*
 * Class: SQLDriver
 * Author: Peter Kaminski
 * Purpose: To create a connection for our game to store information in an SQL database 
 */
public class SQLDriver
{
   private Connection conn;
   private static final String GET_TOP_SCORES = "Select score, userID from HighScores order by score desc";
   private static final String GET_USER_BY_ID = "Select uname from User where userID=?";
   private static final String GET_USER_BY_NAME = "Select userID from User where uname=?";
   private static final String ADD_USER = "Insert into User (uname, passHash) values (?,?)";
   private static final String ADD_HIGH_SCORE = "Insert into HighScores (userID, score) values (?,?)";
   private static final String USER_EXISTS = "select count(uname) from User where uname=?";
   private static final String USER_AND_PASSWORD_EXIST = "select count(uname) from User where uname=? and passHash=?";
   
   public SQLDriver()
   {
      try{
         new Driver();
      } catch(SQLException err){
         err.printStackTrace();
      }
   }

   //Connect to the SQL server instance 
   public void connect(){
      try{
         conn = DriverManager.getConnection("jdbc:mysql://104.131.153.145:3306/maxaer?user=max&password=DoD2016&useSSL=false");
      } catch(SQLException err){
         err.printStackTrace();
      }
   }

   //Close the connection
   public void stop(){
      try
      {
         conn.close();
      }
      catch (SQLException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   //Return true or false, depending on whether the user name or passhash 
   public boolean loginUser(String uname, String passHash){
      
      try{
         PreparedStatement statement = conn.prepareStatement(USER_AND_PASSWORD_EXIST);
         statement.setString(1, uname);
         statement.setString(2, passHash);
         ResultSet results = statement.executeQuery();
         
         if(results.next()){
            int count = results.getInt("count(uname)");
            if(count > 0){
               return true;
            } 
         } 
         
         statement.close();
         return false;
         
      } catch(SQLException e){
         System.out.println("Login user err: " + e.getMessage() + " " + e.getSQLState());
         return false;
      } 
   }
   
   //Check to see if the user name is in SQL
   public boolean userNameExists(String uname){
      try{
         PreparedStatement statement = conn.prepareStatement(USER_EXISTS);
         statement.setString(1, uname);
         ResultSet results = statement.executeQuery();
         if(results.next()){
            int count = results.getInt("count(uname)");
            if(count > 0){
               return true;
            } 
         } 

         statement.close();
         return false;
         
      } catch(SQLException e){
         System.out.println("User exists err: " + e.getMessage() + " " + e.getSQLState());
         return false;
      } 
   }

   //Add a new score to the HighScores table
   public void addTopScore(int userID, int score){
      try{
         PreparedStatement statement = conn.prepareStatement(ADD_HIGH_SCORE);
         statement.setInt(1, userID);
         statement.setInt(2, score);
         statement.executeUpdate();
         statement.close();

      } catch(SQLException e){
         System.out.println("Post top scores err: " + e.getMessage() + " " + e.getSQLState());
      }

   }
   
   //Register a new user with SQL 
   public void addUser(String userID, String passHash){
      try{
         PreparedStatement statement = conn.prepareStatement(ADD_USER);
         statement.setString(1, userID);
         statement.setString(2, passHash);
         statement.executeUpdate();
         statement.close();

      } catch(SQLException e){
         System.out.println("Add user err: " + e.getMessage() + " " + e.getSQLState());
      }

   }
   
   //Return the top 10 scores from the DB
   public ArrayList<UserScore> getTopScores(){
      try{
         Statement statement = conn.createStatement();
         ResultSet results = statement.executeQuery(GET_TOP_SCORES);
         ArrayList<UserScore> highScores = new ArrayList<UserScore>(); 
         int count = 0; 
         while(results.next() && count < 10){
            highScores.add(new UserScore(results.getInt("userID"), results.getInt("score")));
            count++;
         }
         
         statement.close();
         results.close();
         return highScores;
      } catch(SQLException e){
         System.out.println("Get top scores err: " + e.getMessage() + " " + e.getSQLState());
         return null;
      }
   }
   
   //Return the user's name by their ID
   public String getUserByID(int userID){
      String user = "Error";
      try{
         PreparedStatement statement = conn.prepareStatement(GET_USER_BY_ID);
         statement.setInt(1, userID);
         ResultSet results = statement.executeQuery();
         if(results.next()){
            return results.getString("uName");
         }
         
         statement.close();
         results.close();
         return user;
      } catch(SQLException e){
         System.out.println("Get user id err: " + e.getMessage() + " " + e.getSQLState());
         return user;
      }
   }
   
   //Return the user id by name
   public int getUserByName(String userName){
      int user = 1;
      try{
         PreparedStatement statement = conn.prepareStatement(GET_USER_BY_NAME);
         statement.setString(1, userName);
         ResultSet results = statement.executeQuery();
         if(results.next()){
            return results.getInt("userID");
         }
         
         statement.close();
         results.close();
         return user;
      } catch(SQLException e){
         System.out.println("Get user id err: " + e.getMessage() + " " + e.getSQLState());
         return user;
      }
   }

  


}
