package maxÆR.SwingExtensions;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;


/*
 * Class: PromptPasswordField
 * Author: Peter Kaminski
 * Purpose: PromptPasswordField is an extension of the JPasswordField Swing component. 
 *          PPF has the added ability of displaying a placeholder for a password field whenever it is currently empty.
 *          This prevents programmers from having to implement the two component JLabel + JPasswordField combo and can make GUIs sexier
 */
public class PromptPasswordField extends JPasswordField implements FocusListener
{

   private static final long serialVersionUID = 1L;
   private String promptText;
   private Color promptTextColor, borderColor, textColor;
   
   
   //Default constructor for the pwfield accepts a string for the prompt message
   public PromptPasswordField(String promptText){
      this.promptText = promptText;
      this.promptTextColor = Color.LIGHT_GRAY;
      this.borderColor = Color.GREEN;
      this.textColor = Color.BLACK;
      
      setPromptMessage();
      addFocusListener(this);

      setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
   }
   
   //Constructor to take the prompt message and the color of the prompt text
   public PromptPasswordField(String promptText, Color promptTextColor, Color borderColor){
       this.promptText = promptText;
       this.promptTextColor = promptTextColor;
       this.borderColor = borderColor;
       this.textColor = Color.BLACK;
      
      setPromptMessage();
      addFocusListener(this);
      
      setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
   }
   
   
   private void setPromptMessage(){
      setForeground(promptTextColor);
      setText(promptText);
      //This makes our prompt message readable to the user--no echo char set
      setEchoChar((char)0);
   }
   
   //Focus Listeners are used to add the prompt message when our field is blank, and remove it once the user enters text
   @Override
   public void focusGained(FocusEvent e)
   {

      setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, borderColor));
      if(getPassword().length != 0){
         setText("");
         //This is the character we will shadow the password with
         setEchoChar('•');
         setForeground(textColor);
      }
      
   }

   @Override
   public void focusLost(FocusEvent e)
   {

      setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
      if(getPassword().length == 0){
         setPromptMessage(); 
      }
      
   }
}
