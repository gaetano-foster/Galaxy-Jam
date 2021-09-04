package dev.intoTheVoid.game;

import dev.intoTheVoid.game.io.Display;
import dev.intoTheVoid.game.io.Input;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game
{
    // window
    private int width, height;
    private String title;
    private Display display;

    // input
    private Input input;

    // game loop
    private boolean running = false; // by default this is false, but as soon as we call the .start() method, the game starts
                                    // not sure why I felt the need to explain this, but I think commented code is better or something

    // rendering stuff
    private BufferStrategy bs;
    private Graphics g;

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

    private void run()
    {
        // initiate variables and blah blah blah you already read this
        init();

        // the fun part
        double delta = 0;
        int desiredFPS = 60;
        double timePerTick = 1000000000 / desiredFPS;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        float ticks = 0;

        while (running)
        {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            // lock the game at 60 fps
            if (delta >= 1)
            {
                update();
                render();
                ticks++;
                delta--;
            }

            // reset ticks and timer, and show fps on window
            if (timer >= 1000000000)
            {
                display.setTitle(display.getTitle() + " FPS: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }
    }

    // update game
    private void update()
    {

    }

    // draw updates
    private void render()
    {
        bs = display.getCanvas().getBufferStrategy(); // buffer strategy, so our game doesn't "flicker"

        g = bs.getDrawGraphics(); // so we can draw stuff to the buffers
        // clear frame
        g.clearRect(0, 0, 800, 600); // I like to think the low resolution will give the "1980's arcade" vibe
        // draw stuff

        // stop drawing
        bs.show();
        g.dispose();
    }

    // start the game
    public void start()
    {
        if (running) // in retrospect, I'm not sure if this is necessary when not using threads,
                    // but I'm so used to doing it with no explanation I will leave it alone
            return;
        running = true;
        run();
    }

    // close the game
    private void stop(int code)
    {
        if (!running)
            return;
        running = false;
        System.exit(code); // so we will know if there was an error (unlikely, but it's good to be prepared)
    }

    // java-code.jpg

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public String getTitle()
    {
        return title;
    }

    public Display getDisplay()
    {
        return display;
    }

    public Input getInput()
    {
        return input;
    }

    public boolean isRunning()
    {
        return running;
    }
}
