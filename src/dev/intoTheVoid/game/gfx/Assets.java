package dev.intoTheVoid.game.gfx;

import dev.intoTheVoid.game.fonts.FontLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Assets {
    private final HashMap<String, BufferedImage> sprites = new HashMap<>(); // easy access to all sprites
    public Font cs28 = FontLoader.loadFont("/res/fonts/arcade-classic.ttf", 28);
    public Font cs64 = FontLoader.loadFont("/res/fonts/arcade-classic.ttf", 64);

    public void loadAssets() {
        SpriteSheet sheet = new SpriteSheet("/res/textures/spriteSheet.png");
        BufferedImage boom = new SpriteSheet("/res/textures/boom.png").cropSpecific(0, 0, 1200, 1600);

        // player alive
        int DEF_SIZE = 32;
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
        sprites.put("projectile3", sheet.crop(4, 1, 1, 1, DEF_SIZE)); // dead meteor
        sprites.put("projectile4", sheet.crop(4, 0, 1, 1, DEF_SIZE)); // flipped meteor

        // ammo types
        sprites.put("rocket", sheet.crop(4, 3, 1, 1, DEF_SIZE)); // rocket
        sprites.put("cell", sheet.crop(4, 4, 1, 1, DEF_SIZE)); // cell

        // power up
        sprites.put("shield", sheet.crop(3, 4, 1, 1, DEF_SIZE)); // bubble

        // boom
        sprites.put("boom", boom);
    }

    public BufferedImage getSprite(String key) {
        return sprites.get(key);
    }
}
