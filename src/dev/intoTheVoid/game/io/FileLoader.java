package dev.intoTheVoid.game.io;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLoader
{
    public static BufferedImage loadImage(String path)
    {
        try
        {
            // if the texture can be found, return it
            return ImageIO.read(FileLoader.class.getResourceAsStream(path));
        }
        catch (IOException e)
        {
            // return the "null texture" if the texture cannot be found
            e.printStackTrace();
            BufferedImage NULL_IMAGE = new BufferedImage(4, 4, BufferedImage.TYPE_3BYTE_BGR);
            NULL_IMAGE.setRGB(0, 0, Color.pink.getRGB());
            NULL_IMAGE.setRGB(1, 1, Color.pink.getRGB());
            return NULL_IMAGE;
        }
    }

    public static String loadFileAsString(String path, Charset encoding)
    {
        byte[] encoded = new byte[0];
        try
        {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return new String(encoded, encoding);
    }

    public static void writeToFile(String path, String contents)
    {
        try
        {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(contents);
            myWriter.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
