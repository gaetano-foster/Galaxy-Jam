package dev.gfoster.game.entities.enemies;

import dev.gfoster.game.Game;
import dev.gfoster.game.entities.projectiles.EnemyProjectile;
import dev.gfoster.game.gfx.Animation;
import dev.gfoster.game.gfx.Assets;
import dev.gfoster.game.sfx.SoundPlayer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BoomShip extends Enemy {

    public BoomShip(float x, float y, Game game) {
        super(x, y, game);
        Assets assets = game.getAssets();
        health = 4;
        speed = 3;
        attackCooldown = 1500;

        BufferedImage anims[][] = new BufferedImage[][]
                {
                        {assets.getSprite("enemy20"), assets.getSprite("enemy21")}, // idle animation
                        {assets.getSprite("enemy22"), assets.getSprite("enemy22")}, // firing animation
                        {assets.getSprite("enemy23"), assets.getSprite("enemy24"),
                                assets.getSprite("enemy25"), assets.getSprite("enemy13"),
                                assets.getSprite("enemy14"), assets.getSprite("enemy15")},// dead
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

    protected void fire() {
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

            SoundPlayer.playSound("/res/sounds/blunt.wav");
            new Meteor(x + 20, y + 25, 16, game, game.getAssets().getSprite("projectile6"));
            new Meteor(x - 20, y + 25, 16, game, game.getAssets().getSprite("projectile6"));
        }
        else {
            lastDistToPlayer = distToPlayer;
            return;
        }

        lastDistToPlayer = distToPlayer;
        attackTimer = 0;
    }

    public boolean hasRockets() {
        return true;
    }
}
