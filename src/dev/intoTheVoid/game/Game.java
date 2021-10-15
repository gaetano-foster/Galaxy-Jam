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

public class Game {
    // window
    private int width, height;
    private String title;
    private Display display;
    private Input input;
    private boolean running = false;
    private BufferStrategy bs;
    private Graphics g;
    private Assets assets;
    private BufferedImage scrollingSky, scrollingSky2; // for the sky
    private int skyY, skyY1;
    private int gameOverY;
    private boolean gameOver;
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    public ArrayList<Entity> toAdd = new ArrayList<Entity>();
    private ListIterator<Entity> it;
    private Player player;
    private final Random RANDOM = new Random();
    private String highestScore;

    public Game(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private void init() {
        SoundPlayer.playSound("res/sounds/hit.wav");
        assets = new Assets();
        assets.loadAssets();
        input = new Input();
        display = new Display(width, height, title);
        display.addInput(input);
        display.getFrame().setIconImage(assets.getSprite("enemy00"));
        it = entities.listIterator();
        scrollingSky = FileLoader.loadImage("/textures/sky.png");
        scrollingSky2 = scrollingSky;
        skyY = 0;
        skyY1 = -800;
        initGameStuff();
    }

    private void initGameStuff() {
        player = new Player(this);
        gameOverY = -height / 3;
        gameOver = false;
        highestScore = FileLoader.loadFileAsString("res/killstreak/highestkillstreak.txt", StandardCharsets.UTF_8);
    }

    double delta = 0;
    int desiredFPS = 60;
    double timePerTick = 1000000000 / desiredFPS;
    long now;
    long lastTime = System.nanoTime();
    long timer = 0;
    float ticks = 0;
    private int i = 0;

    private void run() {
        init();

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            // lock the game at 60 fps
            if (delta >= 1) {
                update();
                render();
                ticks++;
                delta--;
            }

            // reset ticks and timer, and show fps on window
            if (timer >= 1000000000) {
                display.setTitle(display.getTitle() + " FPS: " + ticks);
                if (!player.isDeathAnimOver()) {
                    if (i == 1)
                        new Meteor(RANDOM.nextInt(width - 64), -10, this);
                    new Enemy(RANDOM.nextInt(width - 64), -10, this);
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
    private void update() {
        input.update();
        sort();
        gameOver();
        scrollSky();

        if ((input.keyJustDown(KeyEvent.VK_ENTER) || (input.keyJustDown(KeyEvent.VK_SPACE) || (input.keyJustDown(KeyEvent.VK_Z)))) && gameOver) {
            initGameStuff();
        }
    }

    // draw updates
    private void render() {
        bs = display.getCanvas().getBufferStrategy();

        g = bs.getDrawGraphics(); // so we can draw stuff to the buffers
        // clear frame
        g.clearRect(0, 0, width, height);
        // draw stuff
        g.drawImage(scrollingSky, 0, skyY, null);
        g.drawImage(scrollingSky2, 0, skyY1, null);

        for (it = entities.listIterator(); it.hasNext(); ) {
            Entity e = it.next();
            e.render(g);
        }

        // gui

        Text.drawString(g, player.getKillstreak(), 0, height - 28 - 32, false, Color.WHITE, assets.cs28);
        if (gameOverY <= -height / 3) {
            Text.drawString(g, "FRAGS ! " + player.getScore(), 0, height - 28, false, Color.WHITE, assets.cs28);
            Text.drawString(g, "HIGHEST KILLSTREAK ! " + highestScore, width - 400, height - 28, false, Color.WHITE, assets.cs28);
        }
        Text.drawString(g, "GAME OVER", width / 2, gameOverY, true, Color.WHITE, assets.cs64);

        // stop drawing
        bs.show();
        g.dispose();
    }

    // start the game
    public void start() {
        if (running)
            return;
        running = true;
        run();
    }

    // close the game
    public void stop(int code) {
        if (!running)
            return;
        running = false;
        System.exit(code);
    }

    private void sort() {
        ListIterator<Entity> itToAdd;
        for (itToAdd = toAdd.listIterator(); itToAdd.hasNext(); ) {
            it.add(itToAdd.next());
            itToAdd.remove();
        }
        for (it = entities.listIterator(); it.hasNext(); ) {
            Entity e = it.next();
            if (gameOver) {
                it.remove();
            } else
                e.update();
        }
    }

    private void scrollSky() {
        skyY++;
        skyY1++;
        if (skyY >= 800)
            skyY = -800;
        if (skyY1 >= 800)
            skyY1 = -800;
    }

    private void gameOver() {
        if (player.isDead())
            gameOverY += 5;
        else
            gameOverY = -height / 3;
        if (gameOverY >= height / 2) {
            gameOver = true;
            gameOverY = height / 2;
        } else
            gameOver = false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public Display getDisplay() {
        return display;
    }

    public Input getInput() {
        return input;
    }

    public boolean isRunning() {
        return running;
    }

    public Assets getAssets() {
        return assets;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ListIterator<Entity> getIt() {
        return it;
    }

    public Player getPlayer() {
        return player;
    }

    public String getHighestScore() {
        return highestScore;
    }
}
