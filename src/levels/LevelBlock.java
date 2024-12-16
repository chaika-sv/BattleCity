package levels;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import static levels.LevelBlockType.*;
import static utils.Constants.DEBUG_MODE;
import static utils.LoadSaveImages.BLOCK_IMAGES;

public class LevelBlock implements Serializable {

    private LevelBlockType drawType;
    private LevelBlockType type;
    private float x, y;
    private int width, height;
    private Rectangle2D.Float hitbox;
    private boolean active = true;

    /**
     * Creates level block
     * The constructor sets type based on drawType
     * @param drawType is type like BRICK_BIG, BRICK_HALF_SMALL or RIVER1_SMALL
     */
    public LevelBlock(LevelBlockType drawType, float x, float y) {
        this.drawType = drawType;
        this.x = x;
        this.y = y;
        this.width = (int)(drawType.getWidth() * Game.SCALE);
        this.height = (int)(drawType.getHeight() * Game.SCALE);

        switch (drawType) {
            case BRICK_BIG, BRICK_HALF, BRICK_SMALL, BRICK_HALF_SMALL, BRICK_LITTLE1, BRICK_LITTLE2 ->
                    this.type = BRICK;
            case METAL_BIG, METAL_HALF, METAL_SMALL ->
                    this.type = METAL;
            case RIVER1_BIG, RIVER1_SMALL, RIVER2_BIG, RIVER2_SMALL, RIVER3_BIG, RIVER3_SMALL ->
                    this.type = RIVER;
            case GRASS_BIG, GRASS_SMALL ->
                    this.type = GRASS;
            case ICE_BIG, ICE_SMALL ->
                    this.type = ICE;
            case BASE_UP, BASE_DOWN ->
                    this.type = BASE;
            case ERASE, SAVE, OPEN ->
                    this.type = drawType;
        }

        initHitbox();
    }

    public LevelBlock(LevelBlock blockToCopy) {
        this.drawType = blockToCopy.drawType;
        this.type = blockToCopy.type;
        this.x = blockToCopy.x;
        this.y = blockToCopy.y;
        this.width = blockToCopy.width;
        this.height = blockToCopy.height;
        this.hitbox = blockToCopy.hitbox;
        this.active = blockToCopy.active;
    }

    /**
     * The level block was hit by some projectile
     * @return true if the projectile should be destroyed
     */
    public boolean hitByProjectile() {
        switch (type) {
            case BRICK -> {
                // If brick then destroy the brick and the projectile
                active = false;
                return true;
            }
            case METAL -> {
                // If metal then destroy the projectile only
                return true;
            }
            case RIVER, GRASS, ICE -> {
                // If river, grass or ice then don't do anything with the level block and let the projectile move through it
                return false;
            }
            case BASE -> {
                drawType = BASE_DOWN;
                return true;
            }
        }

        return true;
    }

    private void initHitbox() {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.drawImage(BLOCK_IMAGES.get(drawType), (int)x, (int)y, width, height,null);
//        if (type == LevelBlockType.ERASE) {
//            g.setColor(Color.GRAY);
//            g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
//        }

        if (DEBUG_MODE)
            drawHitbox(g);
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

    public LevelBlockType getDrawType() {
        return drawType;
    }
}
