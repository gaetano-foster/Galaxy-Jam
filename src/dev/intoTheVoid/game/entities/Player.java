package dev.intoTheVoid.game.entities;

import dev.intoTheVoid.game.Game;
import dev.intoTheVoid.game.entities.projectiles.FriendlyProjectile;
import dev.intoTheVoid.game.gfx.Animation;
import dev.intoTheVoid.game.gfx.Assets;
import dev.intoTheVoid.game.io.FileLoader;
import dev.intoTheVoid.game.sfx.SoundPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Handler;

public class Player extends Entity
{
    private float xMove;
    private BufferedImage[][] anims;
    private Animation[] animations;
    private Assets assets;
    private boolean attacking = false, atkAnim = false;
    private float liveX, liveY;
    private long lastAttackTimer, attackCooldown = 400, attackTimer = attackCooldown;
    private boolean ded = false;
    private String killstreak = " ";
    private int score;

    public Player(Game game)
    {
        super(game.getWidth() / 2.0f, game.getHeight() - 100, defaultSize, defaultSize, game);
        assets = game.getAssets();
        title = "player";
        xMove = 0;
        anims = new BufferedImage[][]
                {
                        {assets.getSprite("player00"), assets.getSprite("player01")}, // idle animation
                        {assets.getSprite("player02"), assets.getSprite("player02")}, // firing animation
                        {assets.getSprite("player10"), assets.getSprite("player11"),
                                assets.getSprite("player12"), assets.getSprite("player13"),
                                assets.getSprite("player14"), assets.getSprite("player15")}  // ded
                };
        animations = new Animation[]
                {
                        new Animation(100, anims[0]),
                        new Animation(200, anims[1]),
                        new Animation(150, anims[2])
                };

        score = 0;
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
        x += xMove;
        if (x > game.getWidth())
            x = 0;
        else if (x < -64)
            x = game.getWidth() - 64;
        getInput(); // gets input
        fire(); // test if you gotta shoot, and fire
        for (Animation a : animations)
        {
            if (a != animations[2])
                a.update();
        }
    }

    private void getInput()
    {
        if (game.getInput().keyDown(KeyEvent.VK_D) || game.getInput().keyDown(KeyEvent.VK_RIGHT))
        {
            xMove = 5;
        }
        else if (game.getInput().keyDown(KeyEvent.VK_A) || game.getInput().keyDown(KeyEvent.VK_LEFT))
        {
            xMove = -5;
        }
        else
        {
            xMove = 0;
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
        if (game.getInput().keyJustDown(KeyEvent.VK_SPACE) || game.getInput().keyJustDown(KeyEvent.VK_Z))
        {
            SoundPlayer.playSound("res/sounds/shoot.wav");
            new FriendlyProjectile(x + width - 25, y - 25, 12, 44, game); // right side
            new FriendlyProjectile(x + 15, y - 25, 12, 44, game); // left side
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

        //g.setColor(Color.YELLOW);
        //g.drawRect((int)x + bounds.x, (int)y + bounds.y, bounds.width, bounds.height);
    }

    @Override
    public void die()
    {
        if (ded)
            return;
        SoundPlayer.playSound("res/sounds/die.wav");
        ded = true; // self explanatory
        if (Integer.parseInt(game.getHighestScore()) < score)
        {
            FileLoader.writeToFile("res/killstreak/highestkillstreak.txt", Integer.toString(score)); // write highest ks
        }
        setScore(0);
        setKillstreak(" "); // reset killstreak
    }

    private BufferedImage getCurrentAnimationFrame()
    {
        if (atkAnim)
            return animations[1].getCurrentFrame();
        else
            return animations[0].getCurrentFrame();
    }

    // useless
    public boolean isDed()
    {
        return ded;
    }

    // useful
    public boolean isFullDed()
    {
        return animations[2].isOver();
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public String getKillstreak()
    {
        return killstreak;
    }

    public void setKillstreak(String killstreak)
    {
        this.killstreak = killstreak;
    }
}
