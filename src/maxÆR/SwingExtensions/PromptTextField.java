package maxÆR.SwingExtensions;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/*
 * Class: PromptTextField
 * Author: Peter Kaminski
 * Purpose: PromptTextField is an extension of the JTextField Swing component. 
 *          PTF has the added ability of displaying a placeholder for a text field whenever the textfield is currently empty.
 *          This prevents programmers from having to implement the two component JLabel + JTextArea combo and can make GUIs sexier
 */
public class PromptTextField extends JTextField implements FocusListener
{

   private static final long serialVersionUID = 1L;
   private String promptText;
   private Color textColor, promptTextColor;
   
   
   //Default constructor for the textField accepts a string for the prompt message
   public PromptTextField(String promptText){
      this.promptText = promptText;
      textColor = Color.BLACK;
      promptTextColor = Color.LIGHT_GRAY;
      
      setPromptMessage();
      addFocusListener(this);
   }
   
   //Constructor to take prompt message and the color of the actual text component
   public PromptTextField(String promptText, Color textColor){
      this.promptText = promptText;
      this.textColor = textColor;
      promptTextColor = Color.LIGHT_GRAY;
      
      setPromptMessage();
      addFocusListener(this);
   }
   
   //Constructor to take the prompt message, the color of the text, and the color of the prompt text
   public PromptTextField(String promptText, Color textColor, Color promptTextColor){
      this.promptText = promptText;
      this.textColor = textColor;
      this.promptTextColor = promptTextColor;
      
      setPromptMessage();
      addFocusListener(this);
   }

   
   private void setPromptMessage(){
      setForeground(promptTextColor);
      setText(promptText);
   }
   
   //Focus Listeners are used to add the prompt message when our field is blank, and remove it once the user enters text
   @Override
   public void focusGained(FocusEvent e)
   {
      if(getText().equals(promptText)){
         setText("");
         setForeground(textColor);
      }
      
   }

   @Override
   public void focusLost(FocusEvent e)
   {
      if(getText().equals("")){
         setPromptMessage(); 
      }
      
   }
   

}
