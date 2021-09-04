package dev.intoTheVoid.game.gfx;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Assets
{
    private HashMap<String, BufferedImage> sprites = new HashMap<String, BufferedImage>(); // easy access to all sprites
    private SpriteSheet sheet;
    private final int DEF_SIZE = 32;
    private String[] keys;

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
        keys = new String[9];

        // sprites for while player is alive
        sprites.put("player00", sheet.crop(0, 0, 1, 1, DEF_SIZE)); keys[0] = "player00";
        sprites.put("player01", sheet.crop(1, 0, 1, 1, DEF_SIZE)); keys[1] = "player01";
        sprites.put("player02", sheet.crop(2, 0, 1, 1, DEF_SIZE)); keys[2] = "player02";

        // sprites for dying player
        sprites.put("player10", sheet.crop(0, 1, 1, 1, DEF_SIZE)); keys[3] = "player10";
        sprites.put("player11", sheet.crop(1, 1, 1, 1, DEF_SIZE)); keys[4] = "player11";
        sprites.put("player12", sheet.crop(2, 1, 1, 1, DEF_SIZE)); keys[5] = "player12";
        sprites.put("player13", sheet.crop(0, 2, 1, 1, DEF_SIZE)); keys[6] = "player13";
        sprites.put("player14", sheet.crop(1, 2, 1, 1, DEF_SIZE)); keys[7] = "player14";
        sprites.put("player15", sheet.crop(2, 2, 1, 1, DEF_SIZE)); keys[8] = "player15";
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

    public String[] getKeys()
    {
        return keys;
    }
}
