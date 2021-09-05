package dev.intoTheVoid.game;

import dev.intoTheVoid.game.entities.Entity;
import dev.intoTheVoid.game.entities.Player;
import dev.intoTheVoid.game.entities.enemies.Enemy;
import dev.intoTheVoid.game.entities.enemies.Meteor;
import dev.intoTheVoid.game.gfx.Assets;
import dev.intoTheVoid.game.io.FileLoader;
import dev.intoTheVoid.game.gfx.Text;
import dev.intoTheVoid.game.io.Display;
import dev.intoTheVoid.game.io.Input;
import dev.intoTheVoid.game.sfx.SoundPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

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
    private BufferedImage scrollingSky, scrollingSky2; // for the sky
    private int skyY, skyY1;
    private int gameOverY;
    private boolean gameOver;

    // entities
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    public ArrayList<Entity> toAdd = new ArrayList<Entity>();
    private ListIterator<Entity> it;
    private ListIterator<Entity> itToAdd;
    private Player player;
    private Random random = new Random();
    private String highestScore;

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
        scrollingSky = FileLoader.loadImage("/textures/sky.png");
        scrollingSky2 = scrollingSky;
        skyY = 0;
        skyY1 = -height;
        initGameStuff();
        display.getFrame().setIconImage(assets.getSprite("enemy00"));
        SoundPlayer.playSound("res/sounds/shoot.wav");
    }

    private void initGameStuff()
    {
        player = new Player(this);
        gameOverY = -height / 3;
        gameOver = false;
        highestScore = FileLoader.loadFileAsString("res/killstreak/highestkillstreak.txt", StandardCharsets.UTF_8);
    }

    private int i = 0;
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
                if (!player.isFullDed())
                {
                    if (i == 1)
                        new Meteor(random.nextInt(width - 64), -10, this);
                    new Enemy(random.nextInt(width - 64), -10, this);
                }
                i++;
                if (i == 2)
                    i = 0;
                ticks = 0;
                timer = 0;
            }
        }

        stop(0);
    }
    // update game
    private void update()
    {
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
            if (gameOver)
            {
                it.remove();
            }
            else
                e.update();
        }

        // game over handling code
        if ((input.keyJustDown(KeyEvent.VK_ENTER) || (input.keyJustDown(KeyEvent.VK_SPACE) || (input.keyJustDown(KeyEvent.VK_Z)))) && gameOver)
        {
            initGameStuff();
        }

        if (player.isDed())
            gameOverY += 5;
        else
            gameOverY = -height / 3;
        if (gameOverY >= height / 2)
        {
            gameOver = true;
            gameOverY = height / 2;
        }
        else
            gameOver = false;

        skyY++;
        skyY1++;
        if (skyY > height)
            skyY = -height;
        if (skyY1 > height)
            skyY1 = -height;
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
        g.drawImage(scrollingSky, 0, skyY, null);
        g.drawImage(scrollingSky2, 0, skyY1, null);

        for (it = entities.listIterator(); it.hasNext();)
        {
            Entity e = it.next();
            e.render(g);
        }

        // gui

        Text.drawString(g, player.getKillstreak(), 0, height - 28 - 32, false, Color.WHITE, assets.cs28);
        Text.drawString(g, "FRAGS ! " + player.getScore(), 0, height - 28, false, Color.WHITE, assets.cs28);
        Text.drawString(g, "HIGHEST KILLSTREAK ! " + highestScore, width - 400, height - 28, false, Color.WHITE, assets.cs28);
        Text.drawString(g, "GAME OVER", width / 2, gameOverY, true, Color.WHITE, assets.cs64);

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

    public String getHighestScore()
    {
        return highestScore;
    }
}
