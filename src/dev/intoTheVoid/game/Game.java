package dev.intoTheVoid.game;

import dev.intoTheVoid.game.io.Display;
import dev.intoTheVoid.game.io.Input;

public class Game
{
    // window
    private int width, height;
    private String title;
    private Display display;

    // input
    private Input input;

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
        input = new Input();
        display = new Display(width, height, title);
        display.addInput(input); // the marriage (can you tell I just woke up)

    }
}
