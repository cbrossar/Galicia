package com.maxaer.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.Driver;

public class SQLDriver
{
   private Connection conn;
   private static final String GET_TOP_SCORES = "Select score, userID from HighScores order by score desc";
   private static final String GET_USER_BY_ID = "Select uname from User where userID=?";
   private static final String ADD_USER = "Insert into User (uname, passHash) values (?,?)";
   private static final String ADD_HIGH_SCORE = "Insert into HighScores (userID, score) values (?,?)";

   public SQLDriver()
   {
      try{
         new Driver();
      } catch(SQLException err){
         err.printStackTrace();
      }
   }

   public void connect(){
      try{
         conn = DriverManager.getConnection("jdbc:mysql://104.131.153.145:3306/maxaer?user=max&password=DoD2016&useSSL=false");
      } catch(SQLException err){
         err.printStackTrace();
      }
   }

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

   public void addTopScore(int userID, int score){
      try{
         PreparedStatement statement = conn.prepareStatement(ADD_HIGH_SCORE);
         statement.setInt(1, userID);
         statement.setInt(2, score);
         statement.executeUpdate();

      } catch(SQLException e){
         System.out.println("Post top scores err: " + e.getMessage() + " " + e.getSQLState());
      }

   }
   
   public void addUser(String userID, int passHash){
      try{
         PreparedStatement statement = conn.prepareStatement(ADD_USER);
         statement.setString(1, userID);
         statement.setInt(2, passHash);
         statement.executeQuery();

      } catch(SQLException e){
         System.out.println("Post top scores err: " + e.getMessage() + " " + e.getSQLState());
      }

   }
   
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
         
         return highScores;
      } catch(SQLException e){
         System.out.println("Get top scores err: " + e.getMessage() + " " + e.getSQLState());
         return null;
      }
   }
   
   public String getUserByID(int userID){
      String user = "Error";
      try{
         PreparedStatement statement = conn.prepareStatement(GET_USER_BY_ID);
         statement.setInt(1, userID);
         ResultSet results = statement.executeQuery();
         if(results.next()){
            return results.getString("uName");
         }
         
         return user;
      } catch(SQLException e){
         System.out.println("Get user id err: " + e.getMessage() + " " + e.getSQLState());
         return user;
      }
   }

  


}
