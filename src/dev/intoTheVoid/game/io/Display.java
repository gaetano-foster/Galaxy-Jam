package dev.intoTheVoid.game.io;

import javax.swing.*;
import java.awt.*;

public class Display
{
    private int width, height;
    private String title;
    private JFrame frame;
    private Canvas canvas;

    public Display(int width, int height, String title)
    {
        this.title = title;
        this.width = width;
        this.height = height;

        constructWindow();
    }

    private void constructWindow()
    {
        frame = new JFrame(title);

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        canvas = new Canvas();

        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
        canvas.createBufferStrategy(3);
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
