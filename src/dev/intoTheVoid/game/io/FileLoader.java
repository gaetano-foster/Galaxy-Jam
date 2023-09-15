package dev.intoTheVoid.game.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

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
        return new Scanner(FileLoader.class.getResourceAsStream(path), "UTF-8").useDelimiter("\\A").next();
    }

    // writes to file, replacing existing characters in the process
    public static void writeToFile(String path, String contents) {
        try {
            PrintWriter myWriter =
                    new PrintWriter(
                            new File(FileLoader.class.getResource(path).getPath()));
            myWriter.write(contents);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
