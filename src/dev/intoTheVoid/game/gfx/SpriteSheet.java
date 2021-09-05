package dev.intoTheVoid.game.gfx;

import dev.intoTheVoid.game.io.FileLoader;

import java.awt.image.BufferedImage;

public class SpriteSheet
{
    private BufferedImage img; // the spritesheet itself

    public SpriteSheet(String path)
    {
        img = FileLoader.loadImage(path); // simple
    }

    public BufferedImage crop(int x, int y, int width, int height, int def) // for getting sprites that are default size
    {
        return img.getSubimage(x * def, y * def, width * def, height * def);
    }

    public BufferedImage cropSpecific(int x, int y, int width, int height) // for getting sprites with more specific dimensions
    {
        return this.crop(x, y, width, height, 1); // ezpz
    }
}
