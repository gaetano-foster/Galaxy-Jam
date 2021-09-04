package dev.intoTheVoid.game.gfx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
}
