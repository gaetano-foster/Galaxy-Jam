package dev.intoTheVoid.game.entities.enemies;

import dev.intoTheVoid.game.Game;
import dev.intoTheVoid.game.entities.projectiles.Projectile;

import java.awt.*;

// falling projectile
public class Meteor extends Projectile {
    public Meteor(float x, float y, Game game) {
        super(game.getAssets().getSprite("projectile2"), x, y, defaultSize, defaultSize, game, 8, 1);
        id = "mProj";
        bounds.x = 20;
        bounds.y = 15;
        bounds.width *= 2;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }

    @Override
    public void die() {

    }
}
