package dev.intoTheVoid.game.entities;

import dev.intoTheVoid.game.Game;
import dev.intoTheVoid.game.entities.enemies.Meteor;
import dev.intoTheVoid.game.entities.projectiles.FriendlyProjectile;
import dev.intoTheVoid.game.gfx.Animation;
import dev.intoTheVoid.game.gfx.Assets;
import dev.intoTheVoid.game.io.FileLoader;
import dev.intoTheVoid.game.sfx.SoundPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private float xMove;
    private final Animation[] animations;
    private boolean atkAnim = false;
    private float liveX, liveY;
    private long lastAttackTimer;
    private final long attackCooldown = 400;
    private long attackTimer = attackCooldown;
    private boolean dead = false;
    private String killstreak = " ";
    private int score;

    public Player(Game game) {
        super(game.getWidth() / 2.0f, game.getHeight() - 140, defaultSize, defaultSize, game);
        Assets assets = game.getAssets();
        id = "player";
        xMove = 0;
        BufferedImage[][] anims = new BufferedImage[][]
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
                        new Animation(100, anims[1]),
                        new Animation(150, anims[2])
                };

        score = 0;
        bounds = new Rectangle(16, 6, 32, 66);
        animations[2].looping = false;
    }

    @Override
    public void update() {
        if (dead) {
            animations[2].update();
            return;
        }
        liveX = x;
        liveY = y;
        x += xMove;
        if (x > game.getWidth())
            x = 0;
        else if (x < -64)
            x = game.getWidth();
        getInput();
        fire();
        for (Animation a : animations) {
            if (a != animations[2])
                a.update();
        }
    }

    private void getInput() {
        int SPEED = 6;
        if (game.getInput().keyDown(KeyEvent.VK_D) || game.getInput().keyDown(KeyEvent.VK_RIGHT)) {
            xMove = SPEED;
        } else if (game.getInput().keyDown(KeyEvent.VK_A) || game.getInput().keyDown(KeyEvent.VK_LEFT)) {
            xMove = -SPEED;
        } else {
            xMove = 0;
        }
    }

    private void fire() {
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();

        // made gun less spammable
        atkAnim = attackTimer < attackCooldown;
        if (atkAnim)
            return;

        if (game.getInput().keyJustDown(KeyEvent.VK_SPACE) || game.getInput().keyJustDown(KeyEvent.VK_Z)) {
            SoundPlayer.playSound("res/sounds/shoot.wav");
            new FriendlyProjectile(x + width - 25, y - 25, 12, 44, game); // right side
            new FriendlyProjectile(x + 15, y - 25, 12, 44, game); // left side
            //new Meteor(x, y - height, -8, game, game.getAssets().getSprite("rocket"));
        } else {
            return;
        }

        attackTimer = 0;
    }

    @Override
    public void render(Graphics g) {
        if (dead) {
            if (!isDeathAnimOver())
                g.drawImage(animations[2].getCurrentFrame(), (int) liveX, (int) liveY, (int) width, (int) height, null);
        } else
            g.drawImage(getCurrentAnimationFrame(), (int) x, (int) y, (int) width, (int) height, null);
    }

    @Override
    public void die() {
        if (dead)
            return;

        dead = true;
        bounds = new Rectangle(-1000, -1000, 1, 1);
        if (Integer.parseInt(game.getHighestScore()) < score)
            FileLoader.writeToFile("res/killstreak/highestkillstreak.txt", Integer.toString(score)); // write highest ks
        SoundPlayer.playSound("res/sounds/death.wav");
        setScore(0);
        setKillstreak(" "); // reset killstreak
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (atkAnim)
            return animations[1].getCurrentFrame();
        else
            return animations[0].getCurrentFrame();
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isDeathAnimOver() {
        return animations[2].isOver();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getKillstreak() {
        return killstreak;
    }

    public void setKillstreak(String killstreak) {
        this.killstreak = killstreak;
    }
}
