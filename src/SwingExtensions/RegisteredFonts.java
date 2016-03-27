package maxÆR.SwingExtensions;

import java.awt.*;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by carleubanks on 3/15/16.
 */
public class RegisteredFonts {
    public static HashMap<String, Font> customFonts;
    public RegisteredFonts()
    {
        customFonts = new HashMap<>();
        setUbuntuFonts();
    }

    public void setUbuntuFonts()
    {

        try {

            // gets path for ubuntu fonts
            //Path bebasPath = Paths.get("/Users/carleubanks/Desktop/maxaer/galicia/resources/fonts/bebasFonts/BebasNeue Bold.ttf");

            // ubuntu Directory to get files
            File[] bebasDirectory = new File("resources/fonts/bebasFonts/").listFiles();

            // inputs all files into hash map
            assert bebasDirectory != null;
            for(int i = 0; i < bebasDirectory.length; i++)
            {
                // dismisses extension (ttf)
                String canonPath = bebasDirectory[i].getName();
                int pos = canonPath.lastIndexOf(".");
                System.out.println(pos);
                if(pos > 0) { canonPath = canonPath.substring(0, pos); }
                System.out.println(canonPath);
                if(!canonPath.equals(".DS_Store")) {
                    // inputs name and font into hashmap
                    customFonts.put(canonPath,
                            Font.createFont(Font.TRUETYPE_FONT,
                                    new File(bebasDirectory[i].getAbsolutePath())).deriveFont(12f));
                }
            }

            // registers each font
            for(int i = 0; i < bebasDirectory.length; i++)
            {

                // registers each font
                GraphicsEnvironment ge =
                        GraphicsEnvironment.getLocalGraphicsEnvironment();


                if(!bebasDirectory[i].getName().equals(".DS_Store")) {
                    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
                            new File(bebasDirectory[i].getAbsolutePath())));
                }
            }

        } catch (IOException |FontFormatException e) {
            e.printStackTrace();
        }
    }
}
