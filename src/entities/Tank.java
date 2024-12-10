package entities;

import gamestates.Gamestate;
import gamestates.Playing;
import levels.LevelBlock;
import levels.LevelBlockType;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import objects.TemporaryObject;
import objects.TemporaryObjectType;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static levels.LevelBlockType.*;
import static main.Game.TILES_DEFAULT_SIZE;
import static utils.Constants.ANI_SPEED;
import static utils.Constants.DEBUG_MODE;
import static utils.Constants.DirConstants.*;
import static utils.Constants.DirConstants.RIGHT;
import static utils.Constants.LevelConstants.PLAYER_SPAWN_X;
import static utils.Constants.LevelConstants.PLAYER_SPAWN_Y;
import static utils.Constants.MovementConstants.TANK_FRONT_AREA;
import static utils.Constants.ProjectileConstants.PROJECTILE_HEIGHT;
import static utils.Constants.ProjectileConstants.PROJECTILE_WIDTH;
import static utils.Constants.TankColorConstants.*;
import static utils.Constants.TankStateConstants.*;
import static utils.Constants.TankTypeConstants.*;
import static utils.Constants.TempObjectsConstants.SHIELD_OFFSET_X;
import static utils.Constants.TempObjectsConstants.SHIELD_OFFSET_Y;
import static utils.LoadSave.TANK_HITBOX_OFFSETS;
import static utils.LoadSave.TANK_IMAGES;

public abstract class Tank {

    protected Playing playing;
    protected LevelManager levelManager;
    protected ObjectManager objectManager;
    protected TemporaryObject shield;

    protected boolean active = true;

    protected boolean left, right, up, down;
    protected boolean moving = false, attacking = false;
    protected boolean hasActiveProjectile = false;


    protected float x, y;
    protected float tankScale;
    protected int width, height;
    protected int lastCoordinate = 0;
    protected int moveInOneDir = 0;
    protected int curChangeDirDistance = 900;
    protected Rectangle2D.Float hitbox;
    protected int aniTick, aniIndex;
    protected int state;
    protected int tankColor;

    // Tank characteristics
    protected TankType tankType;
    protected int maxHealth;
    protected float driveSpeed;
    protected float projectileSpeed;
    protected long shootDelayMS;
    protected int points;

    // Current indicators
    protected long lastShootTimeMS;
    protected int currentHealth;
    protected int curDir;
    protected boolean meetObstacle = false;
    protected LevelBlockType obstacleType;

    protected int hitboxXOffset;
    protected int hitboxYOffset;


    protected BufferedImage[][] animationsV;
    protected BufferedImage[][] animationsH;


    public Tank(TankType tankType, float x, float y, Playing playing) {

        this.playing = playing;
        this.levelManager = playing.getLevelManager();
        this.objectManager = playing.getObjectManager();

        // Like health, speed, points for selected tank type
        applyTankCharacteristics(tankType);

        this.x = x;
        this.y = y;

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

    public void update() {
        long currentTime = System.currentTimeMillis();

        updatePosition();

        updateAnimationTick();
        setAnimation();

        // Enemy is attacking all the time
        // Player is attacking only when attacking bool is true (button pressed)
        if (
                ((this instanceof Player && attacking) || (this instanceof Enemy)) &&
                        currentTime - lastShootTimeMS > shootDelayMS &&
                        !hasActiveProjectile
        )
            shoot();
    }

    protected void updatePosition() {

        moving = false;

        float xSpeed = 0;
        float ySpeed = 0;

        float xFrontArea = 0;
        float yFrontArea = 0;

        if (left) {
            xSpeed -= driveSpeed;
            xFrontArea = -TANK_FRONT_AREA;
            curDir = LEFT;
            moving = true;
        } else if (right) {
            xSpeed += driveSpeed;
            xFrontArea = TANK_FRONT_AREA;
            curDir = RIGHT;
            moving = true;
        } else if (up) {
            ySpeed -= driveSpeed;
            yFrontArea = -TANK_FRONT_AREA;
            curDir = UP;
            moving = true;
        } else if (down) {
            ySpeed += driveSpeed;
            yFrontArea = TANK_FRONT_AREA;
            curDir = DOWN;
            moving = true;
        }

        // Check if tank still can move in the direction then move
        if (canMoveHere(hitbox.x + xSpeed + xFrontArea, hitbox.y + ySpeed + yFrontArea)) {
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;

            applyHitboxOffset();
            syncHitboxWithSprite();
            syncShieldWithSprite();
        }

    }

    /**
     * Return true if tank can move here. The method also set meetObstacle and obstacleType
     * @param x possible new x tank's coordinate
     * @param y possible new y tank's coordinate
     * @return true if tank can move here
     */
    private boolean canMoveHere(float x, float y) {

        if (x < 0 || y < 0 || x > Game.GAME_WIDTH - hitbox.width || y > Game.GAME_HEIGHT - hitbox.height) {
            obstacleType = SCREEN_EDGE;
            meetObstacle = true;
            return false;
        }

        // New possible hitbox to be checked
        Rectangle2D.Float newHitbox = new Rectangle2D.Float(x, y, hitbox.width, hitbox.height);

        for (LevelBlock block : levelManager.getCurrentLevel().getLevelBlocks())
            if (block.isActive())
                if (newHitbox.intersects(block.getHitbox())) {
                    obstacleType = block.getType();

                    switch (block.getType()) {
                        case BRICK, METAL, RIVER -> {
                            // If brick, metal or river then cannot move
                            meetObstacle = true;
                            return false;
                        }
                        case GRASS, ICE -> {
                            // Grass or ice then can move
                            // todo: set something for ice
                            meetObstacle = false;
                        }
                    }
                }

        // Any tank (player or enemy) shouldn't intersects with enemies
        for (Enemy enemy : playing.getEnemyManager().getEnemies())
            if (enemy.isActive() && this != enemy)
                if (newHitbox.intersects(enemy.getHitbox())) {
                    obstacleType = TANK;
                    return false;
                }

        // If it's an enemy then it also shouldn't intersects with player
        if (this instanceof Enemy) {
            if (newHitbox.intersects(playing.getPlayer().getHitbox())) {
                obstacleType = TANK;
                return false;
            }
        }

        meetObstacle = false;
        obstacleType = null;
        return true;
    }



    /**
     * When move hitbox we need to move sprite that we are drawing
     */
    protected void syncHitboxWithSprite() {
        x = hitbox.x - hitboxXOffset;
        y = hitbox.y - hitboxYOffset;
    }

    /**
     * When move player we need to move shield
     */
    protected void syncShieldWithSprite() {
        if (shield != null) {
            shield.setX((int) x + SHIELD_OFFSET_X);
            shield.setY((int) y + SHIELD_OFFSET_Y);
        }
    }

    protected void applyTankCharacteristics(TankType tankType) {
        this.tankType = tankType;
        this.maxHealth = tankType.getMaxHealth();
        this.driveSpeed = tankType.getDriveSpeed() * Game.SCALE;
        this.projectileSpeed = tankType.getProjectileSpeed();
        this.shootDelayMS = tankType.getShootDelayMS();
        this.tankScale = tankType.getTankScale() * Game.SCALE;
        this.points = tankType.getPoints();

        width = (int) (TILES_DEFAULT_SIZE * Game.SCALE * tankScale);
        height = (int) (TILES_DEFAULT_SIZE * Game.SCALE * tankScale);

    }


    public void hitByProjectile(int damageValue) {
        currentHealth -= damageValue;
    }

    /**
     * Kill the tank
     * @param killerTank tank who killed the tank
     */
    public void killTheTank(Tank killerTank) {
        // Deactivate the tank
        active = false;

        TemporaryObjectType bigExplosionType = TemporaryObjectType.TO_BIG_EXPLOSION;

        // Set big explosion
        playing.getObjectManager().createExplosion(
                (int)(hitbox.x + hitbox.width / 2 - bigExplosionType.getWidth() / 2),
                (int)(hitbox.y + hitbox.height / 2 - bigExplosionType.getHeight() / 2),
                bigExplosionType);
    }

    public void injureTheTank() {

    }

    public void addHealth(int value) {
        currentHealth += value;
    }


    protected void initHitbox(int offsetX, int offsetY, int width, int height) {
        hitbox = new Rectangle2D.Float(x + offsetX, y + offsetY, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    public void draw(Graphics g) {

        g.drawImage(TANK_IMAGES[tankColor][tankType.getId()] [curDir] [state == IDLE || state == ATTACK ? 0 : aniIndex],
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
        hasActiveProjectile = true;
    }

    public boolean hasShield() {
        if (shield != null)
            return shield.isActive();

        return false;
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
        curDir = UP;
        currentHealth = maxHealth;
        //tankType = TankType.T_FAST;
        hitbox.x = PLAYER_SPAWN_X;
        hitbox.y = PLAYER_SPAWN_Y;
        syncHitboxWithSprite();
    }

    /**
     * Reset current animation
     */
    private void resetAnyTick() {
        aniTick = 0;
        aniIndex = 0;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public TemporaryObject getShield() {
        return shield;
    }

    public void setShield(TemporaryObject shield) {
        this.shield = shield;
        syncShieldWithSprite();
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getPoints() {
        return points;
    }

    public boolean isHasActiveProjectile() {
        return hasActiveProjectile;
    }

    public void setHasActiveProjectile(boolean hasActiveProjectile) {
        this.hasActiveProjectile = hasActiveProjectile;
    }
}
