package dev.gfoster.game.entities.projectiles;

import dev.gfoster.game.Game;

public class FriendlyProjectile extends Projectile {
    public FriendlyProjectile(float x, float y, float width, float height, Game game) {
        super(game.getAssets().getSprite("projectile0"), x, y, width, height, game, 14, -1 /* up */);
        id = "fProj";
    }
}
