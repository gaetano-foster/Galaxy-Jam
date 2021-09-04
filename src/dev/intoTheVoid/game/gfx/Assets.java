package dev.intoTheVoid.game.gfx;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Assets
{
    private HashMap<String, BufferedImage> sprites = new HashMap<String, BufferedImage>(); // easy access to all sprites

    /*
      * Loads all sprites into memory.
      * maybe there is a more efficient way of doing this,
      * like dynamically loading sprites into memory
      * as needed, but this is easier,
      * and the performance increase would surely be
      * miniscule for such a small game.
     */
    public void loadAssets()
    {

    }

    public BufferedImage getSprite(String key)
    {
        return sprites.get(key);
    }

    public void setSprite(String key, BufferedImage bufferedImage)
    {
        if (sprites.get(key) != null)
            sprites.replace(key, bufferedImage);
        else
            sprites.put(key, bufferedImage);
    }
}
