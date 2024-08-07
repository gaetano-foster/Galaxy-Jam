package dev.gfoster.game;

public class Launcher {
    public static void main(String args[]) {
        Game game = new Game(600, 800, "Galaxy Jam");
        game.start();
    }
}
