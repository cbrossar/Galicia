package maxaer.constants;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import maxaer.swingExtensions.RegisteredFonts;

//Class where we should add all our GUI Constants
public class GUIConstants
{
    // custom fonts
    public static final HashMap<String, Font> customFonts = new RegisteredFonts().customFonts;

    // icons
    public static final ImageIcon arrowIcon =
            new ImageIcon("/Users/carleubanks/Desktop/maxaer/galicia/resources/images/icons/arrowIcon.png");
//    public static final ImageIcon maxÆRLogo =
//            new ImageIcon("/Users/carleubanks/Desktop/maxaer/galicia/resources/images/icons/maxAER_LogoRender.jpg");

    // custom colors
    public static final Color thinMintGreen = Color.decode("#039D69");
    public static final Color dosiDoOrange = Color.decode("#F38C00");
    public static final Color trefoilBlue = Color.decode("#0365C0");
    public static final Color samoaPurple = Color.decode("#7E24BA");


}
