package dev.intoTheVoid.game.fonts;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {

    public static Font loadFont(String path, float size) {
        InputStream is = FontLoader.class.getResourceAsStream(path);
        try {
            Font f = Font.createFont(Font.TRUETYPE_FONT, is);
            return f.deriveFont(Font.PLAIN, size);
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
