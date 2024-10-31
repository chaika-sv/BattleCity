package entities;

import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.ANI_SPEED;
import static utils.Constants.DEBUG_MODE;
import static utils.Constants.TankDirConstants.*;
import static utils.Constants.TankStateConstants.*;
import static utils.Constants.TankTypeConstants.*;
import static utils.HelpMethods.*;

public class Player extends Tank{

    Playing playing;

    BufferedImage[][] animationsV;
    BufferedImage[][] animationsH;

    private boolean left, right, up, down;
    private boolean moving = false, attacking = false;
    private boolean attackChecked;

    private float xDrawOffset = 0 * Game.SCALE;        // 21px - offset from tile border to actual player position
    private float yDrawOffset = 0 * Game.SCALE;         // 4px - offset from tile border to actual player position

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

        loadAnimations();
        initHitbox(52, 63);
    }

    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.MAIN_SPRITE);

        animationsV = new BufferedImage[8][2];

        // i - tank types (8 types)
        // j - animation (2 ani indexes)
        for (int i = 0; i < animationsV.length; i++)
            for (int j = 0; j < animationsV[i].length; j++)
                animationsV[i][j] = img.getSubimage(j * 64, i * 65, 64, 65);

        animationsH = new BufferedImage[8][2];

        for (int i = 0; i < animationsH.length; i++)
            for (int j = 0; j < animationsH[i].length; j++)
                animationsH[i][j] = img.getSubimage(127 + j * 67,  i * 65, 67, 65);

    }


    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
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
        } else if (right) {
            xSpeed += driveSpeed;
            flipX = width;
            flipW = -1;
            curDir = RIGHT;
        } else if (up) {
            ySpeed -= driveSpeed;
            flipY = 0;
            flipH = 1;
            curDir = UP;
        } else if (down) {
            ySpeed += driveSpeed;
            flipY = height;
            flipH = -1;
            curDir = DOWN;
        }

        updateXPos(xSpeed);
        updateYPos(ySpeed);

        moving = true;
    }

    /**
     * Update x position from updatePosition() method
     */
    private void updateXPos(float xSpeed) {
        int[][] lvlData = null;     // todo: get real lvlData

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            // Can't move but still some little space between the player and wall so we want to move right to the wall
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    /**
     * Update y position from updatePosition() method
     */
    private void updateYPos(float ySpeed) {
        int[][] lvlData = null;     // todo: get real lvlData

        if (CanMoveHere(hitbox.x, hitbox.y + ySpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += ySpeed;
        } else {
            // Can't move but still some little space between the player and wall so we want to move right to the wall
            hitbox.x = GetEntityXPosNextToWall(hitbox, ySpeed);
        }
    }


    public void draw(Graphics g) {

        if (curDir == UP || curDir == DOWN) {
            g.drawImage(animationsV[type][aniIndex],
                    (int) (hitbox.x - xDrawOffset + flipX),
                    (int) (hitbox.y - yDrawOffset + flipY),
                    width * flipW,
                    height * flipH,
                    null
            );
        } else if (curDir == LEFT || curDir == RIGHT) {
            g.drawImage(animationsH[type][aniIndex],
                    (int) (hitbox.x - xDrawOffset + flipX),
                    (int) (hitbox.y - yDrawOffset + flipY),
                    height * flipW,
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
                attackChecked = false;
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
}
