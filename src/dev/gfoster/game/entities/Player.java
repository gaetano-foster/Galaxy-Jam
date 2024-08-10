package dev.gfoster.game.entities;

import dev.gfoster.game.Game;
import dev.gfoster.game.entities.enemies.Meteor;
import dev.gfoster.game.entities.projectiles.FriendlyProjectile;
import dev.gfoster.game.gfx.Animation;
import dev.gfoster.game.gfx.Assets;
import dev.gfoster.game.io.FileLoader;
import dev.gfoster.game.sfx.SoundPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private float xMove;
    public static final int SPEED = 6;
    private final Animation[] animations;
    private boolean atkAnim = false;
    private float liveX, liveY;
    private long lastAttackTimer;
    private final long attackCooldown = 400;
    private long attackTimer = attackCooldown;
    private boolean dead = false;
    private String killstreak = " ";
    private int score;
    private int rockets;

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
                                assets.getSprite("player14"), assets.getSprite("player15")},  // ded
                        {assets.getSprite("boom"), assets.getSprite("boom")}
                };
        animations = new Animation[]
                {
                        new Animation(100, anims[0]),
                        new Animation(100, anims[1]),
                        new Animation(150, anims[2]),
                        new Animation(600, anims[3])
                };

        score = 0;
        rockets = 0;
        bounds = new Rectangle(16, 6, 32, 66);
        animations[2].looping = false;
        animations[3].looping = false;
    }

    @Override
    public void update() {
        if (dead) {
            animations[2].update();
            if (boom) {
                animations[3].update();
            }
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
            if (a != animations[2] && a != animations[3])
                a.update();
        }
    }

    private void getInput() {

        if (game.getInput().keyDown(KeyEvent.VK_D) || game.getInput().keyDown(KeyEvent.VK_RIGHT)) {
            xMove = SPEED;
        } else if (game.getInput().keyDown(KeyEvent.VK_A) || game.getInput().keyDown(KeyEvent.VK_LEFT)) {
            xMove = -SPEED;
        } else {
            xMove = 0;
        }
    }

    public void addScore() {
        score += 1;

        // handle killstreaks
        if (score >= 10 && score < 20) {
            game.getPlayer().setKillstreak("PRETTY GOOD!");
        } else if (score >= 20 && score < 30) {
            game.getPlayer().setKillstreak("RAMPAGE!");
        } else if (score >= 30 && score < 40) {
            game.getPlayer().setKillstreak("D1 COMMIT!");
        } else if (score >= 40 && score < 50) {
            game.getPlayer().setKillstreak("UNSTOPPABLE!");
        } else if (score >= 50 && score < 60) {
            game.getPlayer().setKillstreak("LEGENDARY!");
        } else if (score >= 60 && score < 70) {
            game.getPlayer().setKillstreak("HOLY GUACAMOLE!");
        } else if (score >= 70 && score < 80) {
            game.getPlayer().setKillstreak("TOO LEGIT TO QUIT!");
        } else if (score >= 80 && score < 90) {
            game.getPlayer().setKillstreak("WOOP WOOP!");
        } else if (score >= 90 && score < 100) {
            game.getPlayer().setKillstreak("YIPEE!");
        } else if (score >= 100) {
            game.getPlayer().setKillstreak("congrats ig.");
        }

        if (score % 10 == 0) {
            SoundPlayer.playSound("/res/sounds/domination.wav");
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
            if (rockets <= 0) {
                SoundPlayer.playSound("/res/sounds/shoot.wav");
                new FriendlyProjectile(x + width - 25, y - 25, 12, 44, game); // right side
                new FriendlyProjectile(x + 15, y - 25, 12, 44, game); // left side
            }
            else {
                SoundPlayer.playSound("/res/sounds/blunt.wav");
                new Meteor(x, y - height, -8, game, game.getAssets().getSprite("projectile5"));
                rockets--;
            }

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
            if (boom && !animations[3].isOver()) {
                g.drawImage(animations[3].getCurrentFrame(), (int)liveX - (int)(game.getWidth() - width / 2), (int)liveY - (int)(game.getHeight() - height / 2), animations[3].getCurrentFrame().getWidth(), animations[3].getCurrentFrame().getHeight(), null);
            }
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
            FileLoader.writeToFile("/res/killstreak/highestkillstreak.txt", Integer.toString(score)); // write highest ks
        SoundPlayer.playSound("/res/sounds/death.wav");
        setScore(0);
        setKillstreak(" "); // reset killstreak
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (atkAnim)
            return animations[1].getCurrentFrame();
        else
            return animations[0].getCurrentFrame();
    }
    public void giveRockets() {
        rockets = 3;
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

    public float getXMove() {
        return xMove;
    }

    public int getRockets() {
        return rockets;
    }
}
