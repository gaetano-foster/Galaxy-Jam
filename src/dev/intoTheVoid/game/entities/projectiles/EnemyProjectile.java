package dev.intoTheVoid.game.entities.projectiles;

import dev.intoTheVoid.game.Game;

import java.awt.image.BufferedImage;

public class EnemyProjectile extends Projectile
{
    public EnemyProjectile(float x, float y, float width, float height, Game game, int speed, int direction)
    {
        super(game.getAssets().getSprite("projectile1"), x, y, width, height, game, 18, 1 /* down */);
        title = "eProj";
    }
}
