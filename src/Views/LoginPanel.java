package maxÆR.Views;

import javax.swing.*;
import maxÆR.SwingExtensions.PromptPasswordField;
import maxÆR.SwingExtensions.PromptTextField;
import maxÆR.SwingExtensions.RegisteredFonts;

import maxÆR.Constants.GUIConstants;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import java.awt.*;
import java.io.IOException;


/**
 * Class: Login Panel
 * Author: Carl Eubanks
 * Purpose: A LoginPanel extends the functionality of a JPanel to create a user login form.
 *          To login a user needs two things:
 *              1. A valid username
 *              2. A valid password (with at least 8 characters)
 *           Once the user has entered valid information, they will be put to the ModeSelectPanel
 */

public class LoginPanel extends JPanel {

    // serial version UID
    private static final long serialVersionUID = 94963016067263084L;

    // private member variables
    private Image img;
    private JLabel forgotPWLbl, maxÆRMessage;
    private JLabel userOrPWNotValidError;
    private JButton signInBtn, cancelBtn, backBtn;
    private JButton registerBtn, facebookBtn, twitterBtn;
    private PromptTextField userNameField;
    private PromptPasswordField passwordField;
    private RegisterPanel rp;
    private RegisteredFonts rf;

    // constructor
    public LoginPanel()
    {

        // calls each needed method
        createGUI();
        addActions();
        createLayout();

        setVisible(true);
        setBackground(GUIConstants.thinMintGreen);
    }

    public void createGUI()
    {
        /*
         * Font of Bordered Buttons: Ubuntu-BOLD
         * Font of Borderless Buttons: Ubuntu-MEDIUM
         */

        /* -------- Borderless Buttons -------- */


        // back button with gray ubuntu font -- no border && has icon
        backBtn = new JButton("Back");

        Image img = GUIConstants.arrowIcon.getImage() ;
        Image newimg = img.getScaledInstance(10 , 12,  java.awt.Image.SCALE_SMOOTH );
        Icon newIcon = new ImageIcon(newimg);
        backBtn.setIcon(newIcon);
        backBtn.setBorder(null);
        backBtn.setFont(GUIConstants.customFonts.get("BebasNeue Regular"));
        backBtn.setForeground(Color.BLACK);


        /* -------- Bordered Buttons -------- */

        // cancel button with white BOLD ubuntu font
        cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(GUIConstants.customFonts.get("BebasNeue Bold"));
        //cancelBtn.setForeground(Color.WHITE);

        // login button with white BOLD ubuntu font
        signInBtn = new JButton("Login");
        signInBtn.setFont(GUIConstants.customFonts.get("BebasNeue Bold"));
        //signInBtn.setForeground(Color.LIGHT_GRAY);

        // override facebook button -- no border
        facebookBtn = new JButton()
        {
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(new ImageIcon
                        ("/Users/carleubanks/Desktop/maxaer/galicia/resources/images/icons/findUsFBLogo.gif")
                        .getImage().getScaledInstance
                                (getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
            }
        };
        facebookBtn.setBorder(null);

        // override twitter button -- no border
        twitterBtn = new JButton()
        {
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(new ImageIcon
                        ("/Users/carleubanks/Desktop/maxaer/galicia/resources/images/icons/findUsTwitterLogo.png")
                        .getImage(), 0, 0, null);
            }
        };
        twitterBtn.setBorder(null);


        /* -------- Labels-------- */

        // forgot password
        forgotPWLbl = new JLabel("Forgot Password?");
        forgotPWLbl.setBorder(null);
        forgotPWLbl.setFont(GUIConstants.customFonts.get("BebasNeue Regular"));
        forgotPWLbl.getFont().deriveFont(16f);
        forgotPWLbl.setForeground(Color.BLACK);

        // replace with logo later***
        maxÆRMessage = new JLabel("Welcome to maxÆR!");
        maxÆRMessage.setBorder(null);
        maxÆRMessage.setFont(GUIConstants.customFonts.get("BebasNeue Bold"));
        maxÆRMessage.setForeground(Color.WHITE);

        // prompt fields
        userNameField = new PromptTextField("Username", Color.BLACK, Color.LIGHT_GRAY, Color.WHITE);
        passwordField = new PromptPasswordField("Password (8 char or more)", Color.LIGHT_GRAY, Color.WHITE);

        // errors
        userOrPWNotValidError = new JLabel("Username/Password combination not valid!");
        styleErrors();
    }

    public void addActions()
    {

      // mouse listeners (with hover effects)
        setMouseListeners();
        setActionListeners();
    }

    public void setMouseListeners()
    {


    }

    public void setActionListeners()
    {

    }

    public void createLayout()
    {
        setLayout(new GridLayout(4,1));

        // literally a panel just for the back button LOL (cause I feel like it)
        JPanel backPanel = new JPanel(new BorderLayout());
        JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanel.setBackground(GUIConstants.thinMintGreen);
        tempPanel.add(backBtn);
        backPanel.add(tempPanel, BorderLayout.NORTH);
        backPanel.setBackground(GUIConstants.thinMintGreen);

        add(backPanel);

        // literally just a panel for the logo
        JPanel logoPanel = new JPanel()
        {
            protected void paintComponent(Graphics g)
            {
//                g.drawImage
//                        (GUIConstants.maxÆRLogo.getImage().getScaledInstance
//                                (getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, null);

            }
        };

        add(logoPanel);

        // username, password, and sign in/cancel buttons
        JPanel promptPanel = new JPanel(new GridLayout(3,1));
        promptPanel.add(userNameField);
        promptPanel.add(passwordField);
        promptPanel.setBackground(GUIConstants.thinMintGreen);

        // goes into prompt panel
        JPanel loginOrCancelPanel = new JPanel(new GridLayout(0,2));
        loginOrCancelPanel.add(signInBtn);
        loginOrCancelPanel.add(cancelBtn);
        loginOrCancelPanel.add(forgotPWLbl);
        loginOrCancelPanel.setBackground(GUIConstants.thinMintGreen);

        promptPanel.add(loginOrCancelPanel);

        add(promptPanel);



    }

    private void styleErrors(){
        userOrPWNotValidError.setForeground(Color.RED);
    }
}
