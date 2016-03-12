package maxÆR.SwingExtensions;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JPasswordField;

public class PromptPasswordField extends JPasswordField implements FocusListener
{

   private static final long serialVersionUID = 1L;
   private String promptText;
   private Color promptTextColor;
   
   
   //Default constructor for the textField accepts a string for the prompt message
   public PromptPasswordField(String promptText){
      this.promptText = promptText;
      promptTextColor = Color.LIGHT_GRAY;
      
      setPromptMessage();
      addFocusListener(this);
   }
   
   //Constructor to take the prompt message and the color of the prompt text
   public PromptPasswordField(String promptText, Color promptTextColor){
      this.promptText = promptText;
      this.promptTextColor = promptTextColor;
      
      setPromptMessage();
      addFocusListener(this);
   }
   
  
   private void setPromptMessage(){
      setForeground(promptTextColor);
      setText(promptText);
      setEchoChar((char)0);
   }
   
   //Focus Listeners are used to add the prompt message when our field is blank, and remove it once the user enters text
   @Override
   public void focusGained(FocusEvent e)
   {
      System.out.println(getPassword().length);
      if(getPassword().length != 0){
         setText("");
         setEchoChar('*');
         setForeground(Color.BLACK);
      }
      
   }

   @Override
   public void focusLost(FocusEvent e)
   {
      if(getPassword().length == 0){
         setPromptMessage(); 
      }
      
   }
}
