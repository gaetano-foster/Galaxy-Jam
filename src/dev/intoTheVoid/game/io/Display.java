package dev.intoTheVoid.game.io;

import javax.swing.*;
import java.awt.*;

public class Display
{
    private int width, height;
    private String title;
    private JFrame frame;
    private Canvas canvas;

    // creates window, AND displays it. Will have to be careful when and where I call this, lest I cause compiler errors!
    public Display(int width, int height, String title)
    {
        this.title = title;
        this.width = width;
        this.height = height;

        constructWindow();
    }

    // creates the window
    private void constructWindow()
    {
        frame = new JFrame(title);

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false); // if the window is resizable, i will have to work harder to make sure the user doesn't see things they
                                    // aren't supposed to see. It's easier to take away a user's ability than work around it. Sorry!
        frame.setVisible(true);

        canvas = new Canvas();

        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height)); // not sure if using all of these calls is necessary anymore,
                                                                // but I'm a creature of habit! What can I say?
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
        canvas.createBufferStrategy(3); // create buffer strategy NOW to save headaches later
    }

    // just to make the code look cleaner, don't want the init method flooded with .addMouseListener() calls!
    public void addInput(Input input)
    {
        frame.addKeyListener(input);
        frame.addMouseListener(input);
        frame.addMouseMotionListener(input);
        canvas.addMouseListener(input); // must add mouse listener for canvas too, I believe
        canvas.addMouseMotionListener(input);
    }

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

    public void setTitle(String title)
    {
        frame.setTitle(title);
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public Canvas getCanvas()
    {
        return canvas;
    }
}
