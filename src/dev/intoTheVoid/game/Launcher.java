package dev.intoTheVoid.game;

import dev.intoTheVoid.game.io.Display;

public class Launcher {
    public static void main(String args[]) {
        Game game = new Game(600, 700, "Galaxy Jam"); // I like to think the low resolution will give the "1980's arcade" vibe
        game.start();
    }
}
