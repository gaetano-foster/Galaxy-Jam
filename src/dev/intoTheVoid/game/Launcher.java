package dev.intoTheVoid.game;

import dev.intoTheVoid.game.io.Display;

public class Launcher {
    public static void main(String args[]) {
        Game game = new Game(600, 800, "Galaxy Jam");
        game.start();
    }
}
