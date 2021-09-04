package dev.intoTheVoid.game.entities.projectiles;

import dev.intoTheVoid.game.Game;

import java.awt.image.BufferedImage;

public class EnemyProjectile extends Projectile
{
    public EnemyProjectile(float x, float y, float width, float height, Game game)
    {
        super(game.getAssets().getSprite("projectile1"), x, y, width, height, game, 10, 1 /* down */);
        title = "eProj";
    }
}
