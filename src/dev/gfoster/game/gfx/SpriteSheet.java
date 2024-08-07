package dev.gfoster.game.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    private BufferedImage img; // the spritesheet itself

    public SpriteSheet(String path) {
        try {
            if ((img = ImageIO.read((SpriteSheet.class.getResource(path)))) == null) {
                System.exit(1);
                throw new IOException();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage crop(int x, int y, int width, int height, int def) {
        return img.getSubimage(x * def, y * def, width * def, height * def);
    }

    public BufferedImage cropSpecific(int x, int y, int width, int height) {
        return this.crop(x, y, width, height, 1); // ezpz
    }
}
