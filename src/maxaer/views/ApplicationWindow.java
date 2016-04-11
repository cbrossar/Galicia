package maxaer.views;

import javax.swing.JFrame;

public class ApplicationWindow extends JFrame
{
   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   public ApplicationWindow(){
      setSize(600, 500);
      setLocation(400, 200);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("maxÆR");
      
      //add(new RegisterPanel());
      add(new LoginPanel());
      setVisible(true);
   }

}
