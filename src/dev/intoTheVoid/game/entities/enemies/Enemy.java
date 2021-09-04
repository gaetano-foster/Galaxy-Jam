package dev.intoTheVoid.game.entities.enemies;

import dev.intoTheVoid.game.Game;
import dev.intoTheVoid.game.entities.Entity;

// what is a game if not for obstacles?
public abstract class Enemy extends Entity
{
    public Enemy(float x, float y, float width, float height, Game game)
    {
        super(x, y, width, height, game);
    }
}
