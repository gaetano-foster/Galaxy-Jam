package dev.intoTheVoid.game.entities;

import dev.intoTheVoid.game.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Entity
{
    private float xMove;

    public Player(Game game)
    {
        super(game.getWidth() / 2.0f, game.getHeight() - 50, 32, 32, game);
        title = "player";
        xMove = 0;
    }

    @Override
    public void update()
    {
        x += xMove;

        if (game.getInput().keyDown(KeyEvent.VK_D) || game.getInput().keyDown(KeyEvent.VK_RIGHT))
        {
            xMove = 5;
        }
        else if (game.getInput().keyDown(KeyEvent.VK_A) || game.getInput().keyDown(KeyEvent.VK_LEFT))
        {
            xMove = -5;
        }
    }

    @Override
    public void render(Graphics g)
    {

    }

    @Override
    public void die()
    {

    }
}
