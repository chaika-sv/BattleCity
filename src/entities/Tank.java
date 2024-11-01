package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Tank {

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int hitboxYOffset = (int)(10 * Game.SCALE);
    protected int aniTick, aniIndex;
    protected int state;
    protected int type;
    protected int maxHealth;
    protected int currentHealth = maxHealth;
    protected float driveSpeed = 1.2f * Game.SCALE;

    public Tank(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    /**
     * Draw it just for debugging
     */
    protected void drawHitbox(Graphics g) {
        g.setColor(Color.PINK);
        g.drawRect((int)hitbox.x, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public int getState() {
        return state;
    }

    public int getAniIndex() {
        return aniIndex;
    }
}
