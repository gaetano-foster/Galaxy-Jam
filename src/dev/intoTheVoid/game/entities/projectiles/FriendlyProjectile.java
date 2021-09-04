package dev.intoTheVoid.game.entities.projectiles;

import dev.intoTheVoid.game.Game;

import java.awt.image.BufferedImage;

public class FriendlyProjectile extends Projectile
{
    public FriendlyProjectile(float x, float y, float width, float height, Game game)
    {
        super(game.getAssets().getSprite("projectile0"), x, y, width, height, game, 5, -1 /* up */);
        title = "fProj";
    }
}
