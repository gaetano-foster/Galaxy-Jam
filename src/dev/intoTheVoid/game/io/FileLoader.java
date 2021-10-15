package dev.intoTheVoid.game.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLoader {
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(FileLoader.class.getResourceAsStream(path));
        } catch (IOException e) {
            System.err.println("ERROR: could not find specified file at " + path + "!");
        }
        return null;
    }

    // gets contents of a file
    public static String loadFileAsString(String path, Charset encoding) {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, encoding);
    }

    // writes to file, replacing existing characters in the process
    public static void writeToFile(String path, String contents) {
        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(contents);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
