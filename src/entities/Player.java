package entities;

import gamestates.Playing;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.ANI_SPEED;
import static utils.Constants.DEBUG_MODE;
import static utils.Constants.DirConstants.*;
import static utils.Constants.ProjectileConstants.*;
import static utils.Constants.TankStateConstants.*;
import static utils.Constants.TankTypeConstants.*;
import static utils.HelpMethods.*;

public class Player extends Tank{

    private Playing playing;
    private LevelManager levelManager;
    private ObjectManager objectManager;

    private BufferedImage[][] animationsV;
    private BufferedImage[][] animationsH;

    private boolean left, right, up, down;
    private boolean moving = false, attacking = false;

    private float yFlipOffset = 13 * Game.SCALE;



    private int flipX = 0;
    private int flipW = 1;
    private int flipY = 0;
    private int flipH = 1;

    private int curDir = UP;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);

        this.playing = playing;
        this.state = IDLE;
        this.type = BASE;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.driveSpeed = 1.0f * Game.SCALE;
        this.shootDelayMS = (long)(1000 * Game.SCALE);

        levelManager = playing.getLevelManager();
        objectManager = playing.getObjectManager();

        loadAnimations();
        initHitbox(48, 48);
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


    public void update() {
        long currentTime = System.currentTimeMillis();

        updatePosition();
        updateAnimationTick();
        setAnimation();

        if (attacking && currentTime - lastShootTimeMS > shootDelayMS)
            shoot();
    }

    private void updatePosition() {

        moving = false;

        float xSpeed = 0;
        float ySpeed = 0;

        if (left) {
            xSpeed -= driveSpeed;
            flipX = 0;
            flipW = 1;
            curDir = LEFT;
            moving = true;
        } else if (right) {
            xSpeed += driveSpeed;
            flipX = width;
            flipW = -1;
            curDir = RIGHT;
            moving = true;
        } else if (up) {
            ySpeed -= driveSpeed;
            flipY = 0;
            flipH = 1;
            curDir = UP;
            moving = true;
        } else if (down) {
            ySpeed += driveSpeed;
            flipY = height;
            flipH = -1;
            curDir = DOWN;
            moving = true;
        }

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, levelManager.getLevelBlocks())) {
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
        }

    }

    private void shoot() {
        int xOffset = 0;
        int yOffset = 0;

        // Spawn the projectile if the middle of tank's hitbox
        if (curDir == UP || curDir == DOWN)
            xOffset += (hitbox.width - PROJECTILE_WIDTH) / 2;

        if (curDir == LEFT || curDir == RIGHT)
            yOffset += (hitbox.height - PROJECTILE_HEIGHT) / 2;

        // Just give some space if front of tank to spawn the projectile
        int spaceInFront = 10;
        switch (curDir) {
            case UP -> yOffset -= spaceInFront * Game.SCALE;
            case DOWN -> yOffset += hitbox.height + spaceInFront * Game.SCALE;
            case RIGHT -> xOffset += hitbox.width + spaceInFront * Game.SCALE;
            case LEFT -> xOffset -= spaceInFront * Game.SCALE;
        }

        objectManager.shootProjectile((int)(hitbox.x + xOffset), (int)hitbox.y + yOffset, curDir);
        lastShootTimeMS = System.currentTimeMillis();
    }



    public void draw(Graphics g) {

        if (curDir == UP || curDir == DOWN) {
            int correctionY = (int)(((flipH == 1) ? -12 : 0) * Game.SCALE);

            g.drawImage(animationsV[type][state == IDLE ? 0 : aniIndex],
                    (int) (hitbox.x + flipX),
                    (int) (hitbox.y + correctionY + flipY),
                    width * flipW,
                    height * flipH,
                    null
            );
        } else if (curDir == LEFT || curDir == RIGHT) {
            int correctionX = (int)(((flipW == 1) ? -13 : 8) * Game.SCALE);
            int correctionY = (int)(((flipH == 1) ? -13 : -2) * Game.SCALE);

            g.drawImage(animationsH[type][state == IDLE ? 0 : aniIndex],
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
     * Increment animTick and when we reach animSpeed then increment animIndex
     * We use animIndex to display next animation sprite
     */
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= 2) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }


    /**
     * Depending on booleans (moving, attacking, ...) set playerAction (RUNNING, IDLE, ATTACK_1)
     */
    private void setAnimation() {

        int startAni = state;

        if (moving)
            state = MOVING;
        else
            state = IDLE;

        if (attacking) {
            state = ATTACK;
            // If we just starting the attack animation (first tick)
            if (startAni != ATTACK) {
                return;     // return to don't go to resetAnyTick() below since we already reset index and tick
            }
        }

        // In case we have new animation (i.e. another button was pressed) we need to reset previous animation
        if (startAni != state)
            resetAnyTick();

    }

    /**
     * Reset everything for the player to be ready to start the game again
     */
    public void resetAll() {
        up = false;
        down = false;
        left = false;
        right = false;
        attacking = false;
        moving = false;
        state = IDLE;
        currentHealth = maxHealth;
        hitbox.x = x;
        hitbox.y = y;
    }

    /**
     * Reset current animation
     */
    private void resetAnyTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }
}
