package dev.intoTheVoid.game.entities.enemies;

import dev.intoTheVoid.game.Game;
import dev.intoTheVoid.game.entities.Entity;
import dev.intoTheVoid.game.entities.projectiles.Projectile;

import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

// falling projectile
public class Meteor extends Projectile
{
    public Meteor(float x, float y, Game game)
    {
        super(game.getAssets().getSprite("projectile2"), x, y, defaultSize, defaultSize, game, 5, 1);
        title = "mProj";
        bounds.x = 20;
        bounds.y = 15;
        bounds.width *= 2;
    }

    @Override
    public void update()
    {
        super.update();
    }

    @Override
    public void render(Graphics g)
    {
        super.render(g);
        //g.setColor(Color.YELLOW);
        //g.drawRect((int)x + bounds.x, (int)y + bounds.y, bounds.width, bounds.height);
    }

    @Override
    public void die()
    {

    }
}
