package dev.intoTheVoid.game.gfx;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Assets
{
    private HashMap<String, BufferedImage> sprites = new HashMap<String, BufferedImage>(); // easy access to all sprites
    private SpriteSheet sheet;
    private final int DEF_SIZE = 32;

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
        sheet = new SpriteSheet("/textures/spriteSheet.png");

        // sprites for while player is alive
        sprites.put("player00", sheet.crop(0, 0, 1, 1, DEF_SIZE));
        sprites.put("player01", sheet.crop(1, 0, 1, 1, DEF_SIZE));
        sprites.put("player02", sheet.crop(2, 0, 1, 1, DEF_SIZE));

        // sprites for dying player
        sprites.put("player10", sheet.crop(0, 1, 1, 1, DEF_SIZE));
        sprites.put("player11", sheet.crop(1, 1, 1, 1, DEF_SIZE));
        sprites.put("player12", sheet.crop(2, 1, 1, 1, DEF_SIZE));
        sprites.put("player13", sheet.crop(0, 2, 1, 1, DEF_SIZE));
        sprites.put("player14", sheet.crop(1, 2, 1, 1, DEF_SIZE));
        sprites.put("player15", sheet.crop(2, 2, 1, 1, DEF_SIZE));
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
