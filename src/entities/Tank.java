package entities;

import gamestates.Playing;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.DEBUG_MODE;
import static utils.Constants.DirConstants.*;
import static utils.Constants.DirConstants.RIGHT;
import static utils.Constants.TankStateConstants.ATTACK;
import static utils.Constants.TankStateConstants.IDLE;

public abstract class Tank {

    protected Playing playing;
    protected LevelManager levelManager;
    protected ObjectManager objectManager;

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int hitboxYOffset = (int)(10 * Game.SCALE);
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

        applyTankCharacteristics(tankType);
        this.currentHealth = maxHealth;

        loadAnimations();

        initHitbox(48, 48);
    }

    protected void applyTankCharacteristics(TankType tankType) {
        this.tankType = tankType;
        this.maxHealth = tankType.getMaxHealth();
        this.driveSpeed = tankType.getDriveSpeed() * Game.SCALE;
        this.projectileSpeed = tankType.getProjectileSpeed();
        this.shootDelayMS = (long)(tankType.getShootDelayMS() * Game.SCALE);
    }


    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.MAIN_SPRITE);

        animationsV = new BufferedImage[8][2];

        // i - tank types (8 types)
        // j - animation (2 ani indexes)

        // Player's tank up
        for (int i = 0; i < animationsV.length; i++)
            for (int j = 0; j < animationsV[i].length; j++) {
                int yCorrection = 0;
                if (i == 7)
                    yCorrection = (int)(1 * Game.SCALE);      // for SUPER_HEAVY
                animationsV[i][j] = img.getSubimage(j * 64, i * 65 + yCorrection, 64, 64);
            }

        animationsH = new BufferedImage[8][2];

        // Player's tank left
        // The sprite sheet is bit uneven so I grab the sprites this way
        for (int i = 0; i < animationsH.length; i++) {
            int yCorrection = 0;
            if (i >= 4)
                yCorrection = (int)(1 * Game.SCALE);
            animationsH[i][0] = img.getSubimage(127, i * 65 + yCorrection, 66, 65);
            animationsH[i][1] = img.getSubimage(195, i * 65 + yCorrection, 66, 65);
        }

    }



    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    public void draw(Graphics g) {

        if (curDir == UP || curDir == DOWN) {
            int correctionY = (int)(((flipH == 1) ? -12 : 0) * Game.SCALE);

            g.drawImage(animationsV[tankType.getId()][state == IDLE || state == ATTACK ? 0 : aniIndex],
                    (int) (hitbox.x + flipX),
                    (int) (hitbox.y + correctionY + flipY),
                    width * flipW,
                    height * flipH,
                    null
            );
        } else if (curDir == LEFT || curDir == RIGHT) {
            int correctionX = (int)(((flipW == 1) ? -13 : 8) * Game.SCALE);
            int correctionY = (int)(((flipH == 1) ? -13 : -2) * Game.SCALE);

            g.drawImage(animationsH[tankType.getId()][state == IDLE || state == ATTACK ? 0 : aniIndex],
                    (int) (hitbox.x + correctionX + flipX),
                    (int) (hitbox.y + correctionY + flipY + yFlipOffset * flipH),
                    height * flipW,     // Switch width and height
                    width * flipH,
                    null
            );
        }

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
