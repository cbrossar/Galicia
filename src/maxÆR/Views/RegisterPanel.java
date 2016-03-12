package maxÆR.Views;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import maxÆR.SwingExtensions.PromptPasswordField;
import maxÆR.SwingExtensions.PromptTextField;

/*
 * Class: Register Panel
 * Author: Peter Kaminski
 * Purpose: A RegisterPanel extends the functionality of a JPanel to create a user registration form. 
 *          To register a user needs three things:
 *              1. A unique username
 *              2. A unique email address
 *              3. a password with atleast 8 characters
 *          Once the user has entered valid information, the information will be stored into a SQL table
 */

public class RegisterPanel extends JPanel
{
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   //All the GUI components needed for the registration panel
   private JLabel maxAerMessage, userNameExistsError, emailAddressExistsError, passwordNotValidError;
   private PromptTextField userNameField, emailField;
   private PromptPasswordField passwordField;
   private JButton registerBtn;
   
   public RegisterPanel(){
      createGUI();
      addActions(); 
      createLayout(); 
      
      setVisible(true);
   }
   
   private void createGUI(){
      //Instantiate GUI components
      
      //Message
      maxAerMessage = new JLabel("Create your MaxÆr account!");
      /*
       * NEED TO STYLE THIS MESSAGE
       */
      
      //Errors
      userNameExistsError = new JLabel("This user name already exists!");
      emailAddressExistsError = new JLabel("This email address already exists!");
      passwordNotValidError = new JLabel("A valid password should be atleast 8 characters long!");
      styleErrors(); 
      
      //Fields
      userNameField = new PromptTextField("Username");
      emailField = new PromptTextField("yourEmail@emailHost.com");
      passwordField = new PromptPasswordField("Password (8 char)");
      
      //Buttons
      registerBtn = new JButton("Register");
   }
   
   private void addActions(){
      /*
       * We will need to implement: 1) User field validation--making sure everything they entered is fine
       *                            2) Display error messages if they mess up
       *                            3) Store user in SQL
       *                            4) Redirect user to a different screen
       *                            5) Set up email validation???
       */
      registerBtn.addActionListener(new ActionListener()
      {
        
         @Override
         public void actionPerformed(ActionEvent e)
         {
            System.out.println("Username: " + userNameField.getText() + " email: " + emailField.getText() + " password: " + passwordField.getPassword().toString());
            
         }
      });
   }
   
   private void createLayout(){
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      
      add(maxAerMessage);
      add(userNameField);
      add(emailField);
      add(passwordField);
      add(registerBtn);
   }
   
   private void styleErrors(){
      userNameExistsError.setForeground(Color.RED);
      emailAddressExistsError.setForeground(Color.RED);
      passwordNotValidError.setForeground(Color.RED);
   }

}
