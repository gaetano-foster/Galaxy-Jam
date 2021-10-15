package dev.intoTheVoid.game.entities.projectiles;

import dev.intoTheVoid.game.Game;

public class FriendlyProjectile extends Projectile {
    public FriendlyProjectile(float x, float y, float width, float height, Game game) {
        super(game.getAssets().getSprite("projectile0"), x, y, width, height, game, 14, -1 /* up */);
        id = "fProj";
    }
}
