package dev.intoTheVoid.game.sfx;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer
{
    // plays a sound file
    public static void playSound(String soundFile)
    {
        File f = new File("./" + soundFile);
        AudioInputStream audioIn = null;
        try
        {
            audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        }
        catch (UnsupportedAudioFileException | IOException e)
        {
            e.printStackTrace();
        }
        Clip clip = null;
        try
        {
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        }
        catch (LineUnavailableException | IOException e)
        {
            e.printStackTrace();
        }
    }
}
