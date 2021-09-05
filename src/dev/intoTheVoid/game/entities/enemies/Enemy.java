package dev.intoTheVoid.game.entities.enemies;

import dev.intoTheVoid.game.Game;
import dev.intoTheVoid.game.entities.Entity;
import dev.intoTheVoid.game.entities.projectiles.EnemyProjectile;
import dev.intoTheVoid.game.entities.projectiles.FriendlyProjectile;
import dev.intoTheVoid.game.gfx.Animation;
import dev.intoTheVoid.game.gfx.Assets;
import dev.intoTheVoid.game.sfx.SoundPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

// what is a game if not for obstacles?
public class Enemy extends Entity
{
    private float yMove;
    private float speed = 5;
    private BufferedImage[][] anims;
    private Animation[] animations;
    private Assets assets;
    private boolean attacking = false, atkAnim = false;
    private boolean ded = false;
    private float liveX = 0, liveY = 0; // so we can move the collision hull, but still play animation
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
        animations[2].looping = false;
    }

    @Override
    public void update()
    {
        if (ded)
        {
            animations[2].update();
            return;
        }
        liveX = x;
        liveY = y;
        yMove = speed;
        y += yMove;
        fire();
        for (Animation a : animations)
        {
            if (a != animations[2])
                a.update();
        }
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
            SoundPlayer.playSound("res/sounds/eshoot.wav");
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
        if (ded)
        {
            if (!isFullDed())
                g.drawImage(animations[2].getCurrentFrame(), (int)liveX, (int)liveY, (int)width, (int)height, null);
        }
        else
            g.drawImage(getCurrentAnimationFrame(), (int)x, (int)y, (int)width, (int)height, null);
    }

    @Override
    public void die()
    {
        if (ded)
            return;
        SoundPlayer.playSound("res/sounds/hit.wav");
        x = 42069; // the funny
        y = 42069;
        ded = true;
        addScore();
    }

    private void addScore()
    {
        int score = game.getPlayer().getScore();
        game.getPlayer().setScore(score + 1);
        score = game.getPlayer().getScore();

        // handle killstreaks
        if (score >= 10 && score < 20)
        {
            game.getPlayer().setKillstreak("KILLING SPREE!");
        }
        else if (score >= 20 && score < 30)
        {
            game.getPlayer().setKillstreak("UNSTOPPABLE!");
        }
        else if (score >= 30 && score < 40)
        {
            game.getPlayer().setKillstreak("TOO LEGIT TO QUIT!");
        }
        else if (score >= 40 && score < 50)
        {
            game.getPlayer().setKillstreak("ON A RAMPAGE!");
        }
        else if (score >= 50)
        {
            game.getPlayer().setKillstreak("GODLIKE!");
        }

        if (score % 10 == 0)
        {
            SoundPlayer.playSound("res/sounds/domination.wav");
        }
    }

    private BufferedImage getCurrentAnimationFrame()
    {
        if (atkAnim)
            return animations[1].getCurrentFrame();
        else
            return animations[0].getCurrentFrame();
    }

    public boolean isFullDed()
    {
        return animations[2].isOver();
    }
}
