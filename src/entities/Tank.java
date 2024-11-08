package entities;

import gamestates.Playing;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static main.Game.TILES_DEFAULT_SIZE;
import static utils.Constants.DEBUG_MODE;
import static utils.Constants.DirConstants.*;
import static utils.Constants.DirConstants.RIGHT;
import static utils.Constants.TankStateConstants.ATTACK;
import static utils.Constants.TankStateConstants.IDLE;
import static utils.Constants.TankTypeConstants.*;
import static utils.LoadSave.TANK_HITBOX_OFFSETS;
import static utils.LoadSave.TANK_IMAGES;

public abstract class Tank {

    protected Playing playing;
    protected LevelManager levelManager;
    protected ObjectManager objectManager;

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int aniTick, aniIndex;
    protected int state;

    // Tank characteristics
    protected TankType tankType;
    protected int maxHealth;
    protected float driveSpeed;
    protected float projectileSpeed;
    protected long shootDelayMS;

    // Current indicators
    protected long lastShootTimeMS;
    protected int currentHealth;
    protected int curDir = UP;

    // Draw values
    protected float yFlipOffset = 13 * Game.SCALE;
    protected int flipX = 0;
    protected int flipW = 1;
    protected int flipY = 0;
    protected int flipH = 1;

    protected int hitboxXOffset;
    protected int hitboxYOffset;


    protected BufferedImage[][] animationsV;
    protected BufferedImage[][] animationsH;


    public Tank(TankType tankType, float x, float y, int width, int height, Playing playing) {

        this.playing = playing;
        this.levelManager = playing.getLevelManager();
        this.objectManager = playing.getObjectManager();

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // Like health, speed, points for selected tank type
        applyTankCharacteristics(tankType);
        this.currentHealth = maxHealth;

        // Number of pixels (x and y) from top-left corner of sprite image to actual tank picture
        applyHitboxOffset();
        // It's always square (same in width and height)
        initHitbox(hitboxXOffset, hitboxYOffset, tankType.getHitboxSize(), tankType.getHitboxSize());
    }

    protected void applyHitboxOffset() {
        this.hitboxXOffset = TANK_HITBOX_OFFSETS[tankType.getId()] [curDir] [0];
        this.hitboxYOffset = TANK_HITBOX_OFFSETS[tankType.getId()] [curDir] [1];
    }

    /**
     * When move hitbox we need to move sprite that we are drawing
     */
    protected void syncHitboxWithSprite() {
        x = hitbox.x - hitboxXOffset;
        y = hitbox.y - hitboxYOffset;
    }

    protected void applyTankCharacteristics(TankType tankType) {
        this.tankType = tankType;
        this.maxHealth = tankType.getMaxHealth();
        this.driveSpeed = tankType.getDriveSpeed() * Game.SCALE;
        this.projectileSpeed = tankType.getProjectileSpeed();
        this.shootDelayMS = (long)(tankType.getShootDelayMS() * Game.SCALE);
    }


    protected void initHitbox(int offsetX, int offsetY, int width, int height) {
        hitbox = new Rectangle2D.Float(x + offsetX, y + offsetY, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    public void draw(Graphics g) {

        g.drawImage(TANK_IMAGES[tankType.getId()] [curDir] [state == IDLE || state == ATTACK ? 0 : aniIndex],
                (int) (x),
                (int) (y),
                width,
                height,
                null
        );

        if (DEBUG_MODE)
            drawHitbox(g);

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
