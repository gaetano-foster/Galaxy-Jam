package dev.intoTheVoid.game.entities.projectiles;

import dev.intoTheVoid.game.Game;

public class EnemyProjectile extends Projectile {
    public static final int SPEED = 12;

    public EnemyProjectile(float x, float y, float width, float height, Game game) {
        super(game.getAssets().getSprite("projectile1"), x, y, width, height, game, SPEED, 1 /* down */);
        id = "eProj";
    }
}
