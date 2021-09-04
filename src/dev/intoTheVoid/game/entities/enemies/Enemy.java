package dev.intoTheVoid.game.entities.enemies;

import dev.intoTheVoid.game.Game;
import dev.intoTheVoid.game.entities.Entity;
import dev.intoTheVoid.game.entities.projectiles.EnemyProjectile;
import dev.intoTheVoid.game.entities.projectiles.FriendlyProjectile;
import dev.intoTheVoid.game.gfx.Animation;
import dev.intoTheVoid.game.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

// what is a game if not for obstacles?
public class Enemy extends Entity
{
    private float yMove;
    private BufferedImage[][] anims;
    private Animation[] animations;
    private Assets assets;
    private boolean attacking = false, atkAnim = false;
    private long lastAttackTimer, attackCooldown = 800, attackTimer = 0;

    public Enemy(float x, float y, Game game)
    {
        super(x, y, defaultSize, defaultSize, game);
        assets = game.getAssets();

        anims = new BufferedImage[][]
                {
                        {assets.getSprite("enemy00"), assets.getSprite("enemy01")}, // idle animation
                        {assets.getSprite("enemy02"), assets.getSprite("enemy02")}, // firing animation
                        {assets.getSprite("enemy10"), assets.getSprite("enemy11"),
                                assets.getSprite("enemy12"), assets.getSprite("enemy13"),
                                assets.getSprite("enemy14"), assets.getSprite("enemy15")}  // ded
                };

        animations = new Animation[]
                {
                        new Animation(100, anims[0]),
                        new Animation(200, anims[1]),
                        new Animation(150, anims[2])
                };
        title = "enemy";

        bounds = new Rectangle(16, 6, 32, 66);
    }

    @Override
    public void update()
    {
        yMove = 3;
        y += yMove;
        fire();
    }

    private void fire()
    {
        // fun timer stuff
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();

        if (attackTimer < 150)
        {
            atkAnim = true;
        }
        else
        {
            atkAnim = false;
        }
        if (attackTimer < attackCooldown)
        {
            attacking = true;
            return;
        }
        else
        {
            attacking = false;
        }

        // shoot boom boom haha
        if ( new Rectangle((int)game.getPlayer().getBounds().x + (int)game.getPlayer().getX(), (int)game.getPlayer().getBounds().y + (int)game.getPlayer().getY(), game.getPlayer().getBounds().width, game.getPlayer().getBounds().height).intersects((int)this.x - 9, (int)this.y, (int)width + 15, game.getHeight()))
        {
            new EnemyProjectile(x + width - 25, y + 25, 12, 44, game); // right side
            new EnemyProjectile(x + 15, y + 25, 12, 44, game); // left side
        }
        else
        {
            return;
        }

        attackTimer = 0;
    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(getCurrentAnimationFrame(), (int)x, (int)y, (int)width, (int)height, null);
    }

    @Override
    public void die()
    {

    }

    private BufferedImage getCurrentAnimationFrame()
    {
        if (atkAnim)
            return animations[1].getCurrentFrame();
        else
            return animations[0].getCurrentFrame();
    }
}
