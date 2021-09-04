package dev.intoTheVoid.game.entities;
import dev.intoTheVoid.game.Game;

import java.awt.*;

/*
   When handling entities in games, it mostly boils down to two options:
   - Have an abstract entity class and make different types of entities in different classes, a more object oriented approach
   - Have one single entity class with no functionality, and an abstract component class, which will account for all functionality,
     a more functional approach

     I have chosen the former, as I believe it leads to cleaner code
 */
public abstract class Entity
{
    // rotations are not necessary, you will face 1 direction the whole game
    public static float defaultSize = 64;
    protected float x, y, width, height;
    protected Rectangle bounds;
    protected boolean active = true;
    protected Game game;
    protected String title;

    public Entity(float x, float y, float width, float height, Game game)
    {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(0, 0, (int)width, (int)height);
        game.toAdd.add(this);
    }

    public Rectangle getCollisionBounds(float xOffset, float yOffset)
    {
        return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
    }

    public abstract void update();
    public abstract void render(Graphics g);
    public abstract void die();

    // I hate writing collision detection code
    public boolean checkEntityCollisions(float xOffset, float yOffset)
    {
        for (Entity e : game.getEntities())
        {
            if (e.equals(this))
                continue;
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
            {
                return true;
            }
        }
        return false;
    }

    public String checkEntityTitle(float xOffset, float yOffset)
    {
        for (Entity e : game.getEntities())
        {
            if (e.equals(this))
                continue;
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
            {
                return e.getTitle();
            }
        }
        return "null";
    }

    public Entity getEntityAt(float xOffset, float yOffset)
    {
        for (Entity e : game.getEntities())
        {
            if (e.equals(this))
                continue;
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
            {
                return e;
            }
        }
        return null;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    // java-code.jpg

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public void setWidth(float width)
    {
        this.width = width;
    }

    public void setHeight(float height)
    {
        this.height = height;
    }

    public void setBounds(Rectangle bounds)
    {
        this.bounds = bounds;
    }

    public String getTitle()
    {
        return title;
    }
}
