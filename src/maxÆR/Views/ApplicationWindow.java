package maxÆR.Views;

import javax.swing.JFrame;

public class ApplicationWindow extends JFrame
{
   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   public ApplicationWindow(){
      setSize(200, 200);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("MaxÆR");
      
      add(new RegisterPanel());
      setVisible(true);
   }

}
