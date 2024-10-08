package dev.gfoster.game.entities.projectiles;

import dev.gfoster.game.Game;
import dev.gfoster.game.entities.Entity;
import dev.gfoster.game.entities.enemies.Enemy;
import dev.gfoster.game.entities.enemies.Meteor;
import dev.gfoster.game.sfx.SoundPlayer;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Projectile extends Entity {
    private int direction; // is it moving up or down?
    private int speed; // speed
    private BufferedImage sprite;
    protected float yMove;
    protected boolean locked = false;

    public Projectile(BufferedImage sprite, float x, float y, float width, float height, Game game, int speed, int direction) {
        super(x, y, width, height, game);
        this.speed = speed;
        this.direction = direction;
        this.sprite = sprite;
        yMove = 0;

        if (direction == 0)
            direction = 1;
        else if (direction > 1)
            direction = 1;
        else if (direction < -1)
            direction = -1;

        bounds = new Rectangle(0, 4, 12, 32);
    }

    @Override
    public void update() {
        if (!active)
            return;
        if (!locked)
            yMove = speed * direction;
        else
            yMove = 0;

        kill();
        if (y > game.getHeight() || y < -20) {
            die();
        }
    }

    private void kill() {
        if (!checkEntityCollisions(0, yMove)) {
            y += yMove;
        } else {
                // if player touches enemy laser
            if ((checkEntityTitle(0, yMove).equalsIgnoreCase("player") && (this.id.equals("eProj")))) {
                game.getPlayer().die();
                die();
                // if enemy touches player laser
            } else if (checkEntityTitle(0, yMove).equalsIgnoreCase("enemy") && this.id.equals("fProj")) {
                getEntityAt(0, yMove).harm(1);
                Enemy e = (Enemy)getEntityAt(0, yMove);
                if (e.hasRockets() && e.getHealth() <= 0)
                    game.getPlayer().giveRockets();
                die();
                // if player laser touches meteor
            } else if (checkEntityTitle(0, yMove).equalsIgnoreCase("fProj") && this.id.equals("mProj")) {
                getEntityAt(0, yMove).die();
                SoundPlayer.playSound("/res/sounds/blunt.wav");
                Meteor m = (Meteor)this;
                if (m.isReflectable())
                    this.speed -= 4;
            } else if (checkEntityTitle(0, yMove).equalsIgnoreCase("player") && this.id.equals("mProj")) {
                for (Entity e : game.getEntities()) {
                    if (!e.getId().equals("player"))
                        e.harm(4);
                }
                game.getPlayer().setBoom();
                game.getPlayer().die();
            } else if (checkEntityTitle(0, yMove).equalsIgnoreCase("enemy") && this.id.equals("mProj") && this.speed < 0) {
                getEntityAt(0, yMove).setBoom();
                getEntityAt(0, yMove).harm(4);
                Enemy e = (Enemy)getEntityAt(0, yMove);
                if (e.hasRockets() && e.getHealth() <= 0)
                    game.getPlayer().giveRockets();
                SoundPlayer.playSound("/res/sounds/death.wav");
                for (Entity n : game.getEntities()) {
                    if (!n.getId().equals("player"))
                        n.harm(2);
                }
                
                this.die();
            }
            else
                y += yMove;
        }
    }

    @Override
    public void die() {
        active = false;
        x = -1000;
        y = -1000;
    }

    @Override
    public void render(Graphics g) {
        if (!active)
            return;
        g.drawImage(sprite, (int) x, (int) y, (int) width, (int) height, null);
    }

    public float getSpeed() {
        return speed;
    }

    public BufferedImage getSprite() {
        return sprite;
    }
}
