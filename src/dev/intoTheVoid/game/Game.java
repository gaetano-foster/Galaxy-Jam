package dev.intoTheVoid.game;

import dev.intoTheVoid.game.io.Display;

public class Game
{
    // window
    private int width, height;
    private String title;
    private Display display;

    // variables will be used to create the display
    public Game(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    // initiates variables and assets
    private void init()
    {
        display = new Display(width, height, title);
    }
}
