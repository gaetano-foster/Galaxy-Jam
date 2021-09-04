package dev.intoTheVoid.game;

import dev.intoTheVoid.game.entities.Entity;
import dev.intoTheVoid.game.entities.Player;
import dev.intoTheVoid.game.entities.enemies.Enemy;
import dev.intoTheVoid.game.gfx.Assets;
import dev.intoTheVoid.game.io.Display;
import dev.intoTheVoid.game.io.Input;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.ListIterator;

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
                                    double delta = 0;
    int desiredFPS = 60;
    double timePerTick = 1000000000 / desiredFPS;
    long now;
    long lastTime = System.nanoTime();
    long timer = 0;
    float ticks = 0;

    // gfx
    private BufferStrategy bs;
    private Graphics g;
    private Assets assets;

    // entities
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    public ArrayList<Entity> toAdd = new ArrayList<Entity>();
    private ListIterator<Entity> it;
    private ListIterator<Entity> itToAdd;
    private Player player;
    private Enemy enemy;

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
        assets = new Assets();
        input = new Input();
        display = new Display(width, height, title);
        display.addInput(input); // the marriage (can you tell I just woke up)
        assets.loadAssets();
        it = entities.listIterator();
        player = new Player(this);
        enemy = new Enemy(100, 100, this);
    }

    private void run()
    {
        // initiate variables and blah blah blah you already read this
        init();

        // the fun part
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

        stop(0);
    }
    // update game
    private void update()
    {
        if (player.isFullDed())
            return;
        input.update();

        for (itToAdd = toAdd.listIterator(); itToAdd.hasNext();) // if we don't use an iterator and a separate list, we will get the
                                                                // "ConcurrentModificationException". Bad!
        {
            it.add(itToAdd.next());
            itToAdd.remove();
        }
        for (it = entities.listIterator(); it.hasNext();)
        {
            Entity e = it.next();
            e.update();
        }
    }

    // draw updates
    private void render()
    {
        bs = display.getCanvas().getBufferStrategy(); // buffer strategy, so our game doesn't "flicker"

        g = bs.getDrawGraphics(); // so we can draw stuff to the buffers
        // clear frame
        g.setColor(Color.black);
        g.fillRect( 0, 0, width, height);
        // draw stuff

        for (it = entities.listIterator(); it.hasNext();)
        {
            Entity e = it.next();
            e.render(g);
        }

        // stop drawing
        bs.show();
        g.dispose();
    }

    // start the game
    public void start()
    {
        if (running)
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
        System.exit(code);
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

    public Assets getAssets()
    {
        return assets;
    }

    public ArrayList<Entity> getEntities()
    {
        return entities;
    }

    public ListIterator<Entity> getIt()
    {
        return it;
    }

    public Player getPlayer()
    {
        return player;
    }
}
