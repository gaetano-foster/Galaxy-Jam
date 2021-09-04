package dev.intoTheVoid.game.io;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener
{
    private boolean[] keys, justPressed, cantPress;

    private boolean leftPressed, rightPressed, leftJustPressed, rightJustPressed, leftCantPress, rightCantPress;
    private int mouseX, mouseY;

    public Input()
    {
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    public void update()
    {
        for (int i = 0; i < keys.length; i++)
        {
            if (cantPress[i] && !keys[i])
            {
                cantPress[i] = false;
            } else if (justPressed[i])
            {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if (!cantPress[i] && keys[i])
            {
                justPressed[i] = true;
            }
        }

        if (leftCantPress && !leftPressed)
        {
            leftCantPress = false;
        } else if (leftJustPressed)
        {
            leftCantPress = true;
            leftJustPressed = false;
        }
        if (!leftCantPress && leftPressed)
        {
            leftJustPressed = true;
        }

        if (rightCantPress && !rightPressed)
        {
            rightCantPress = false;
        } else if (rightJustPressed)
        {
            rightCantPress = true;
            rightJustPressed = false;
        }
        if (!rightCantPress && rightPressed)
        {
            rightJustPressed = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() < 0 || e.getKeyCode() > keys.length)
        {
            System.err.println("Key out of bounds!");
            return;
        }

        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() < 0 || e.getKeyCode() > keys.length)
        {
            System.err.println("Key out of bounds!");
            return;
        }

        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    public boolean keyJustDown(int keyCode)
    {
        if (keyCode < 0 || keyCode > keys.length)
        {
            System.err.println("Key out of bounds!");
            return false;
        }

        return justPressed[keyCode];
    }

    public boolean keyDown(int keyCode)
    {
        if (keyCode < 0 || keyCode > keys.length)
        {
            System.err.println("Key out of bounds!");
            return false;
        }

        return keys[keyCode];
    }

    public boolean[] getKeys()
    {
        return keys;
    }

    public boolean isLeftJustPressed()
    {
        return leftJustPressed;
    }

    public boolean isRightJustPressed()
    {
        return rightJustPressed;
    }

    public boolean isLeftPressed()
    {
        return leftPressed;
    }

    public boolean isRightPressed()
    {
        return rightPressed;
    }

    public int getMouseX()
    {
        return mouseX;
    }

    public int getMouseY()
    {
        return mouseY;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1)
            leftPressed = true;
        else if (e.getButton() == MouseEvent.BUTTON3)
            rightPressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1)
            leftPressed = false;
        else if (e.getButton() == MouseEvent.BUTTON3)
            rightPressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
