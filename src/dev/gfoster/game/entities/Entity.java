package dev.gfoster.game.entities;

import dev.gfoster.game.Game;

import java.awt.*;

public abstract class Entity {
    public static float defaultSize = 64;
    protected float x, y, width, height;
    protected Rectangle bounds;
    protected boolean active = true;
    protected Game game;
    protected String id;
    protected boolean boom = false;

    protected int health = 3;

    public Entity(float x, float y, float width, float height, Game game) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(0, 0, (int) width, (int) height);
        game.toAdd.add(this);
    }

    public Rectangle getCollisionBounds(float xOffset, float yOffset) {
        return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
    }

    public abstract void update();

    public abstract void render(Graphics g);

    public abstract void die();

    public void harm(int amount) {
        health -= amount;
    }

    public boolean checkEntityCollisions(float xOffset, float yOffset) {
        for (Entity e : game.getEntities()) {
            if (e.equals(this))
                continue;
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        return false;
    }

    public String checkEntityTitle(float xOffset, float yOffset) {
        for (Entity e : game.getEntities()) {
            if (e.equals(this))
                continue;
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return e.getId();
            }
        }
        return "null";
    }

    public Entity getEntityAt(float xOffset, float yOffset) {
        for (Entity e : game.getEntities()) {
            if (e.equals(this))
                continue;
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return e;
            }
        }
        return null;
    }

    protected void drawHitBoxes(Color color, Graphics g) {
        g.setColor(color);
        g.drawRect((int)x + bounds.x, (int)y + bounds.y, bounds.width, bounds.height);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setBoom() {
        boom = true;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
