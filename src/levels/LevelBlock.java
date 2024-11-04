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
    private boolean active = true;

    public LevelBlock(LevelBlockType type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = (int)(type.getWidth() * Game.SCALE);
        this.height = (int)(type.getHeight() * Game.SCALE);

        initHitbox();
    }

    /**
     * The level block was hit by some projectile
     * @return true if the projectile should be destroyed
     */
    public boolean hitByProjectile() {
        switch (type) {
            case BRICK_BIG, BRICK_HALF, BRICK_SMALL, BRICK_HALF_SMALL -> {
                // If brick then destroy the brick and the projectile
                active = false;
                return true;
            }
            case METAL_BIG, METAL_HALF, METAL_SMALL -> {
                // If metal then destroy the projectile only
                return true;
            }
            case RIVER1_BIG, RIVER1_SMALL, RIVER2_BIG, RIVER2_SMALL, RIVER3_BIG, RIVER3_SMALL,
                    GRASS_BIG, GRASS_SMALL,
                    ICE_BIG, ICE_SMALL
                    -> {
                // River, grass or ice then don't do anything with the level block and the projectile
                return false;
            }
        }

        return true;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
