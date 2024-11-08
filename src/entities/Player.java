package entities;

import gamestates.Playing;
import main.Game;

import static utils.Constants.ANI_SPEED;
import static utils.Constants.DirConstants.*;
import static utils.Constants.ProjectileConstants.*;
import static utils.Constants.TankStateConstants.*;
import static utils.HelpMethods.*;

public class Player extends Tank{

    private boolean left, right, up, down;
    private boolean moving = false, attacking = false;

    public Player(TankType tankType, float x, float y, int width, int height, Playing playing) {
        super(tankType, x, y, width, height, playing);
        this.state = IDLE;
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

        applyHitboxOffset();
        syncHitboxWithSprite();

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

        objectManager.shootProjectile((int)(hitbox.x + xOffset), (int)hitbox.y + yOffset, curDir, projectileSpeed, this);
        lastShootTimeMS = System.currentTimeMillis();
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
