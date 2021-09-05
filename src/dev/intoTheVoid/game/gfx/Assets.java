package dev.intoTheVoid.game.gfx;

import dev.intoTheVoid.game.fonts.FontLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Assets
{
    private HashMap<String, BufferedImage> sprites = new HashMap<String, BufferedImage>(); // easy access to all sprites
    private SpriteSheet sheet;
    private final int DEF_SIZE = 32;
    public Font cs28 = FontLoader.loadFont("res/fonts/arcade-classic.ttf", 28);
    public Font cs64 = FontLoader.loadFont("res/fonts/arcade-classic.ttf", 64);

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

        // player alive
        sprites.put("player00", sheet.crop(0, 0, 1, 1, DEF_SIZE));
        sprites.put("player01", sheet.crop(1, 0, 1, 1, DEF_SIZE));
        sprites.put("player02", sheet.crop(2, 0, 1, 1, DEF_SIZE));

        // player ded
        sprites.put("player10", sheet.crop(0, 1, 1, 1, DEF_SIZE));
        sprites.put("player11", sheet.crop(1, 1, 1, 1, DEF_SIZE));
        sprites.put("player12", sheet.crop(2, 1, 1, 1, DEF_SIZE));
        sprites.put("player13", sheet.crop(0, 2, 1, 1, DEF_SIZE));
        sprites.put("player14", sheet.crop(1, 2, 1, 1, DEF_SIZE));
        sprites.put("player15", sheet.crop(2, 2, 1, 1, DEF_SIZE));

        // enemy alive
        sprites.put("enemy00", sheet.crop(0, 3, 1, 1, DEF_SIZE));
        sprites.put("enemy01", sheet.crop(1, 3, 1, 1, DEF_SIZE));
        sprites.put("enemy02", sheet.crop(2, 3, 1, 1, DEF_SIZE));

        // enemy ded
        sprites.put("enemy10", sheet.crop(0, 3 + 1, 1, 1, DEF_SIZE));
        sprites.put("enemy11", sheet.crop(1, 3 + 1, 1, 1, DEF_SIZE));
        sprites.put("enemy12", sheet.crop(2, 3 + 1, 1, 1, DEF_SIZE));
        sprites.put("enemy13", sheet.crop(0, 3 + 2, 1, 1, DEF_SIZE));
        sprites.put("enemy14", sheet.crop(1, 3 + 2, 1, 1, DEF_SIZE));
        sprites.put("enemy15", sheet.crop(2, 3 + 2, 1, 1, DEF_SIZE));

        // projectiles
        sprites.put("projectile0", sheet.crop(3, 0, 1, 1, DEF_SIZE)); // friendly
        sprites.put("projectile1", sheet.crop(3, 1, 1, 1, DEF_SIZE)); // enemy
        sprites.put("projectile2", sheet.crop(4, 2, 1, 1, DEF_SIZE)); // meteor

        // power up
        sprites.put("shield", sheet.crop(3, 4, 1, 1, DEF_SIZE)); // bubble
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
