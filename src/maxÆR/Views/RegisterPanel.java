package maxÆR.Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import maxÆR.Constants.GUIConstants;
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
     
      setBackground(GUIConstants.thinMintGreen);
   }
   
   private void createGUI(){
      //Instantiate GUI components
      
      //Message
      maxAerMessage = new JLabel("Welcome to MaxÆR!");
      maxAerMessage.setFont(new Font("Arial", Font.BOLD, 16));
      //This makes the first focusable object our message, allowing the userNameField to have it's prompt show up initially
      maxAerMessage.setFocusable(true);
      /*
       * NEED TO STYLE THIS MESSAGE
       */
      
      //Errors
      userNameExistsError = new JLabel("This user name already exists!");
      emailAddressExistsError = new JLabel("This email address already exists!");
      passwordNotValidError = new JLabel("A valid password should be atleast 8 characters long!");
      styleErrors(); 
      
      //Fields
      userNameField = new PromptTextField("Username", Color.WHITE, Color.LIGHT_GRAY, Color.WHITE);
      emailField = new PromptTextField("yourEmail@emailHost.com", Color.WHITE, Color.LIGHT_GRAY, Color.WHITE);
      passwordField = new PromptPasswordField("Password (8 char or more)", Color.LIGHT_GRAY, Color.WHITE);
      
      //Buttons
      registerBtn = new JButton("Register");
      
      styleComponents();
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
      setLayout(new GridLayout(3, 1));
      
      JPanel messagePanel = new JPanel(new GridLayout(3,1));
      messagePanel.setBackground(GUIConstants.thinMintGreen);
      messagePanel.add(maxAerMessage, BorderLayout.NORTH);
      JLabel accountLabel = new JLabel("Please create a free user account");
      accountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
      accountLabel.setBackground(GUIConstants.thinMintGreen);
      accountLabel.setForeground(Color.WHITE);
      messagePanel.add(accountLabel);
      messagePanel.add(getThinMintPanel());
    
      add(messagePanel);
      
      JPanel fieldsPanel = new JPanel(new GridLayout(3, 1));
      fieldsPanel.add(userNameField);
      fieldsPanel.add(emailField);
      fieldsPanel.add(passwordField);
      
      add(fieldsPanel);
      
      JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
      buttonPanel.setBackground(GUIConstants.thinMintGreen);
      buttonPanel.add(getThinMintPanel());
      buttonPanel.add(getThinMintPanel());
      buttonPanel.add(getThinMintPanel());
      JPanel innerButtonPanel = new JPanel(new GridLayout(3, 1));
      innerButtonPanel.add(getThinMintPanel());
      innerButtonPanel.add(registerBtn);
      innerButtonPanel.add(getThinMintPanel());
      buttonPanel.add(innerButtonPanel);
      
      add(buttonPanel);
   }
   
   private JPanel getThinMintPanel(){
      return new JPanel(){
         /**
          * 
          */
         private static final long serialVersionUID = 1L;

         @Override
         public void setBackground(Color bg)
         {
            // TODO Auto-generated method stub
            super.setBackground(GUIConstants.thinMintGreen);
         }
      };
   }
   
   private void styleComponents(){
      userNameField.setBackground(GUIConstants.thinMintGreen);
      emailField.setBackground(GUIConstants.thinMintGreen);
      passwordField.setBackground(GUIConstants.thinMintGreen);
      maxAerMessage.setBackground(GUIConstants.thinMintGreen);
      maxAerMessage.setForeground(Color.WHITE);
      registerBtn.setBackground(GUIConstants.thinMintGreen);
      registerBtn.setOpaque(true);
   }
   
   private void styleErrors(){
      userNameExistsError.setForeground(Color.RED);
      emailAddressExistsError.setForeground(Color.RED);
      passwordNotValidError.setForeground(Color.RED);
   }

}
