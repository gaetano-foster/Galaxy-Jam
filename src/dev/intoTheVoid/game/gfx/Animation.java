package dev.intoTheVoid.game.gfx;

import java.awt.image.BufferedImage;

public class Animation {
    private int speed, index;
    private long lastTime, timer;
    private BufferedImage[] frames;
    public boolean looping = true;
    private boolean over = false;

    public Animation(int speed, BufferedImage[] frames) {
        this.speed = speed;
        this.frames = frames;
        index = 0;
        lastTime = System.currentTimeMillis();
    }

    public void update() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer > speed) {
            index++;
            timer = 0;
            if (index >= frames.length) {
                if (looping)
                    index = 0;
                else {
                    index = 0;
                    over = true;
                }
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        if (!over)
            return frames[index];
        else
            return new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR);
    }

    public boolean isOver() {
        return over;
    }
}
