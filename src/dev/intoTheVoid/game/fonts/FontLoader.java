package dev.intoTheVoid.game.fonts;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontLoader {

    public static Font loadFont(String path, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("didn't work");
            System.exit(1);
        }
        return null;
    }
}
