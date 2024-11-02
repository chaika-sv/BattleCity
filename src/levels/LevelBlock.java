package levels;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.LoadSave.BLOCK_IMAGES;

public class LevelBlock {

    private LevelBlockType type;
    private float x, y;
    private int width, height;
    private Rectangle2D.Float hitbox;

    public LevelBlock(LevelBlockType type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = (int)(type.getWidth() * Game.SCALE);
        this.height = (int)(type.getHeight() * Game.SCALE);

        initHitbox();
    }

    private void initHitbox() {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.drawImage(BLOCK_IMAGES.get(type), (int)x, (int)y, width, height,null);
    }

    public void drawHitbox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int)hitbox.x, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
    }

    public LevelBlockType getType() {
        return type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
}
