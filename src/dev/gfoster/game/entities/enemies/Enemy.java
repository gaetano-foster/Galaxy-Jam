package dev.gfoster.game.entities.enemies;

import dev.gfoster.game.Game;
import dev.gfoster.game.entities.Entity;
import dev.gfoster.game.entities.Player;
import dev.gfoster.game.entities.projectiles.EnemyProjectile;
import dev.gfoster.game.gfx.Animation;
import dev.gfoster.game.gfx.Assets;
import dev.gfoster.game.sfx.SoundPlayer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {
    private final Animation animations[];
    private boolean atkAnim = false;
    private boolean dead = false;
    private float liveX = 0, liveY = 0;
    private final long attackCooldown = 500;
    private long lastAttackTimer = attackCooldown;
    private long attackTimer = 0;
    private final int SPEED = 5;

    public Enemy(float x, float y, Game game) {
        super(x, y, defaultSize, defaultSize, game);
        Assets assets = game.getAssets();

        // idle animation
        // firing animation
        // ded
        BufferedImage anims[][] = new BufferedImage[][]
                {
                        {assets.getSprite("enemy00"), assets.getSprite("enemy01")}, // idle animation
                        {assets.getSprite("enemy02"), assets.getSprite("enemy02")}, // firing animation
                        {assets.getSprite("enemy10"), assets.getSprite("enemy11"),
                                assets.getSprite("enemy12"), assets.getSprite("enemy13"),
                                assets.getSprite("enemy14"), assets.getSprite("enemy15")},// ded
                        {assets.getSprite("boom"), assets.getSprite("boom")}
                };

        animations = new Animation[]
                {
                        new Animation(100, anims[0]),
                        new Animation(200, anims[1]),
                        new Animation(150, anims[2]),
                        new Animation(300, anims[3])
                };
        id = "enemy";

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
        if (y > game.getHeight()) {
            die();
        }
        liveX = x;
        liveY = y;
        y += (float) SPEED;
        fire();
        for (Animation a : animations) {
            if (a != animations[2] && a != animations[3])
                a.update();
        }
    }

    int projPPS = 60 * EnemyProjectile.SPEED;
    int playerPPS = 60 * Player.SPEED;
    float distToPlayer = 0, lastDistToPlayer = 0;
    private void fire() {
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();

        atkAnim = attackTimer < attackCooldown;

        distToPlayer = Math.abs(game.getPlayer().getX() - x);
        Rectangle playerHitbox = new Rectangle(game.getPlayer().getBounds().x + (int)game.getPlayer().getX(), game.getPlayer().getBounds().y + (int)game.getPlayer().getY(), game.getPlayer().getBounds().width, game.getPlayer().getBounds().height);

        float projTravelTime = ((game.getHeight()) - y) / projPPS;
        float playerTravelTime = distToPlayer / playerPPS;

        if (!atkAnim
                && (((float)(Math.round(projTravelTime*2))/2 == (float)(Math.round(playerTravelTime*2))/2 && distToPlayer < lastDistToPlayer)
                    || (Math.round(projTravelTime) == Math.round(playerTravelTime) && distToPlayer < lastDistToPlayer)
                    || playerHitbox.intersects((int) this.x - 9, (int) this.y, (int) width + 15, game.getHeight()))) {

            SoundPlayer.playSound("/res/sounds/eshoot.wav");
            new EnemyProjectile(x + width - 25, y + 25, 12, 44, game);
            new EnemyProjectile(x + 15, y + 25, 12, 44, game);
        }
        else {
            lastDistToPlayer = distToPlayer;
            return;
        }

        lastDistToPlayer = distToPlayer;
        attackTimer = 0;
    }

    @Override
    public void render(Graphics g) {
        if (dead) {
            if (!isDeathAnimOver()) {
                g.drawImage(animations[2].getCurrentFrame(), (int)liveX, (int) liveY, (int) width, (int) height, null);
            }
            if (boom && !animations[3].isOver()) {
                g.drawImage(animations[3].getCurrentFrame(), (int)liveX - (int)(game.getWidth() - width / 2), (int)liveY - (int)(game.getHeight() - height / 2), animations[3].getCurrentFrame().getWidth(), animations[3].getCurrentFrame().getHeight(), null);
            }
        } else
            g.drawImage(getCurrentAnimationFrame(), (int)x, (int)y, (int)width, (int)height, null);
    }

    @Override
    public void die() {
        if (dead)
            return;

        if (checkEntityTitle(0, SPEED).equalsIgnoreCase("mProj")) {
            boom = true;
        }

        if (y < game.getHeight()) {
            SoundPlayer.playSound("/res/sounds/hit.wav");
            addScore();
        }

        x = 42069;
        y = 42069;
        dead = true;
    }

    private void addScore() {
        int score = game.getPlayer().getScore();
        game.getPlayer().setScore(score + 1);
        score = game.getPlayer().getScore();

        // handle killstreaks
        if (score >= 10 && score < 20) {
            game.getPlayer().setKillstreak("KILLING SPREE!");
        } else if (score >= 20 && score < 30) {
            game.getPlayer().setKillstreak("UNSTOPPABLE!");
        } else if (score >= 30 && score < 40) {
            game.getPlayer().setKillstreak("TOO LEGIT TO QUIT!");
        } else if (score >= 40 && score < 50) {
            game.getPlayer().setKillstreak("RAMPAGE!");
        } else if (score >= 50 && score < 100) {
            game.getPlayer().setKillstreak("GODLIKE!");
        } else if (score >= 100) {
            game.getPlayer().setKillstreak("TOP G!");
        }

        if (score % 10 == 0) {
            if (score % 100 == 0) {
                SoundPlayer.playSound("/res/sounds/the-slayer.wav");
                return;
            }
            SoundPlayer.playSound("/res/sounds/domination.wav");
        }
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (atkAnim)
            return animations[1].getCurrentFrame();
        else
            return animations[0].getCurrentFrame();
    }

    public boolean isDeathAnimOver() {
        return animations[2].isOver();
    }
}
