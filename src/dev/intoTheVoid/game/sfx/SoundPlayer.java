package dev.intoTheVoid.game.sfx;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayer
{
    // plays a sound file
    public static void playSound(String soundFile)
    {
        InputStream is = SoundPlayer.class.getResourceAsStream(soundFile);
        AudioInputStream audioIn = null;
        try {
            assert is != null;
            audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
