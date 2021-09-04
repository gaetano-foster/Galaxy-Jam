package dev.intoTheVoid.game.entities.projectiles;

import dev.intoTheVoid.game.Game;
import dev.intoTheVoid.game.entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Projectile extends Entity
{
    private int direction; // is it moving up or down?
    private int speed; // speed
    private BufferedImage sprite;
    protected float yMove;

    public Projectile(BufferedImage sprite, float x, float y, float width, float height, Game game, int speed, int direction)
    {
        super(x, y, width, height, game);
        this.speed = speed;
        this.direction = direction;
        this.sprite = sprite;
        yMove = 0;
        // make sure that direction is not:
        // - greater than 1
        // - equal to 0
        // - less than -1
        // any one of these will result in headaches!
        if (direction == 0)
            direction = 1;
        if (direction > 1)
            direction = 1;
        if (direction < -1)
            direction = -1;
    }

    @Override
    public void update()
    {
        if (!active)
            return;
        yMove = speed * direction;

        if (!checkEntityCollisions(0, yMove))
            y += yMove;
        else
            die();
    }

    @Override
    public void die()
    {
        active = false;
    }

    @Override
    public void render(Graphics g)
    {
        if (!active)
            return;
        g.drawImage(sprite, (int)x, (int)y, (int)width, (int)height, null);
    }
}
