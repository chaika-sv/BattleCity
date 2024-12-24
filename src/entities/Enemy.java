package entities;

import gamestates.Playing;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import static main.Game.*;
import static utils.Constants.DEBUG_MODE;
import static utils.Constants.DirConstants.*;
import static utils.Constants.EnemyConstants.*;
import static utils.Constants.TankColorConstants.*;

public class Enemy extends Tank{

    private Random rand;
    private Rectangle2D.Float searchBox;
    private boolean seePlayer = false;
    private boolean moveToPlayer = false;
    private boolean pDirUp = false;
    private boolean pDirDown = false;
    private boolean pDirLeft = false;
    private boolean pDirRight = false;
    private String lastCase = "";
    private long lastChangeDirTimeMS;
    private boolean withPowerUp;

    public Enemy(TankType tankType, float x, float y, boolean withPowerUp, Playing playing) {
        super(tankType, x, y, playing);

        tankColor = ENEMY_GRAY;

        this.withPowerUp = withPowerUp;

        this.down = true;
        this.attacking = true;
        this.curDir = DOWN;

        rand = new Random();

        initSearchBox();
    }

    private void initSearchBox() {
        searchBox = new Rectangle2D.Float(x - (SEARCH_BOX_WIDTH - width) / 2f, y - (SEARCH_BOX_HEIGHT - height) / 2f, SEARCH_BOX_WIDTH, SEARCH_BOX_HEIGHT);
    }


    @Override
    public void updatePosition() {
        super.updatePosition();

        long currentTime = System.currentTimeMillis();

        seePlayer = false;
        // Update the search box position and determine direction to player
        seePlayer = updateSearchBox();

        /*
          Four cases:
          1) No obstacle and doesn't see player
          2) No obstacle and see player
          3) Meet obstacle and doesn't see player
          4) Meet obstacle and see player
         */

        // Case #1 - Just keep moving and if it's time to change direction then change it randomly
        if (!meetObstacle && !seePlayer) {

            // It's time to change direction
            if (moveInOneDir >= curChangeDirDistance)
                changeDirRandomly();

            lastCase = "#1";

            return;
        }

        // Case #2 - Start moving to the player's direction
        if (!meetObstacle && seePlayer) {

            lastCase = "#2";

            // Check if enemy needs to change direction
            if (!moveToPlayer || !isCurDirActual()) {

                lastCase = "#2.1";

                if (pDirUp)
                    setDirection(UP);
                else if (pDirDown)
                    setDirection(DOWN);
                else if (pDirLeft)
                    setDirection(LEFT);
                else if (pDirRight)
                    setDirection(RIGHT);

                moveToPlayer = true;
            }
        }

        // Case #3 - Change position randomly
        if (meetObstacle && !seePlayer) {
            // todo: if it's a brick then try to hit it sometimes
            changeDirRandomly();
            lastCase = "#3";
        }

        // Case #4 - Try to go around the obstacle in the player's direction or may be hit it
        if (meetObstacle && seePlayer) {

            // Need the timer to avoid fast rotation
            if (currentTime - lastChangeDirTimeMS > CHANGE_DIR_DELAY_MS) {

                if (!setAlternativeDirection()) {
                    lastCase = "#4.1";
                    // If alternative direction hasn't been set then change direction randomly
                    // todo: if it's brick then just hit it
                    //changeDirRandomly();
                    //moveToPlayer = false;
                } else {
                    lastCase = "#4.2";
                    moveToPlayer = true;
                }

                lastChangeDirTimeMS = System.currentTimeMillis();
            }
        }

    }

    /**
     * Check if enemy still needs to move in the current direction (the player is still here)
     * @return true if current direction still actual
     */
    private boolean isCurDirActual() {

        switch (curDir) {
            case UP -> { return pDirUp; }
            case DOWN -> { return pDirDown; }
            case LEFT -> { return pDirLeft; }
            case RIGHT -> { return pDirRight; }
        }

        return false;
    }

    private void setDirection(int direction) {

        resetDir();

        curDir = direction;

        switch (direction) {
            case UP -> up = true;
            case DOWN -> down = true;
            case LEFT -> left = true;
            case RIGHT -> right = true;
        }
    }

    /**
     * If there is any other alternative direction to reach the player then set it
     * @return true if alternative direction was set
     */
    private boolean setAlternativeDirection() {

        int oldDir = curDir;

        switch (curDir) {
            case UP -> {
                if (pDirRight)
                    setDirection(RIGHT);
                else if (pDirLeft)
                    setDirection(LEFT);
                else if (pDirDown)
                    setDirection(DOWN);
            }
            case DOWN -> {
                if (pDirRight)
                    setDirection(RIGHT);
                else if (pDirLeft)
                    setDirection(LEFT);
                else if (pDirUp)
                    setDirection(UP);
            }
            case LEFT -> {
                if (pDirRight)
                    setDirection(RIGHT);
                else if (pDirDown)
                    setDirection(DOWN);
                else if (pDirUp)
                    setDirection(UP);
            }
            case RIGHT -> {
                if (pDirLeft)
                    setDirection(LEFT);
                else if (pDirDown)
                    setDirection(DOWN);
                else if (pDirUp)
                    setDirection(UP);
            }
        }

        return oldDir != curDir;
    }


    /**
     * When move enemy hitbox we need to move his search box with him
     * If the search box intersects player's search box (found the player) then we set player direction booleans
     * @return true if found the player
     */
    protected boolean updateSearchBox() {

        // Move the search box along with the tank
        searchBox.x = x - (SEARCH_BOX_WIDTH - width) / 2f;
        searchBox.y = y - (SEARCH_BOX_HEIGHT - height) / 2f;

        Rectangle2D.Float playerHitbox = playing.getPlayer1().getHitbox();

        resetPlayerDirectionBooleans();

        // If the search box intersects player's search box (found the player)
        if (searchBox.intersects(playerHitbox)) {

            // Set booleans that point where is the player

            if (Math.abs(playerHitbox.x - hitbox.x) > TILES_DEFAULT_SIZE / 4f) {

                if (playerHitbox.x < hitbox.x)
                    pDirLeft = true;
                else if (playerHitbox.x > hitbox.x)
                    pDirRight = true;

            }

            if (Math.abs(playerHitbox.y - hitbox.y) > TILES_DEFAULT_SIZE / 4f) {

                if (playerHitbox.y < hitbox.y)
                    pDirUp = true;
                else if (playerHitbox.y > hitbox.y)
                    pDirDown = true;

            }

            return true;

        } else {
            moveToPlayer = false;
            return false;
        }

    }

    private void resetPlayerDirectionBooleans() {
        pDirUp = false;
        pDirDown = false;
        pDirLeft = false;
        pDirRight = false;
    }


    /**
     * Change tank direction randomly (not equal current dir)
     * Also randomly set distance that the tank will be moving in the direction
     */
    private void changeDirRandomly() {

        // Select next direction randomly
        int nextDir = rand.nextInt(4);

        // Make sure that it's not equal current direction
        while (nextDir == curDir)
            nextDir = rand.nextInt(4);

        setDirection(nextDir);

        // Save last coordinate in this direction
        if (curDir == UP || curDir == DOWN)
            lastCoordinate = (int) hitbox.y;
        else if (curDir == LEFT || curDir == RIGHT)
            lastCoordinate = (int) hitbox.x;

        // Just random distance to move in the current direction
        curChangeDirDistance = rand.nextInt(5) * 100;
    }

    private void resetDir() {
        up = false;
        down = false;
        left = false;
        right = false;
    }

    @Override
    public void killTheTank(Tank killerTank) {
        super.killTheTank(killerTank);

        // Decrease number of enemies to kill
        playing.getEnemyManager().decreasedEnemiesToKillCount();

        // If the tank was killed by player then give points to player
        if (killerTank instanceof Player)
            ((Player)killerTank).addPoints(points);

        if (withPowerUp)
            playing.getObjectManager().generateNewPowerUp();

    }

    @Override
    public void draw(Graphics g) {

        if (withPowerUp)
            tankColor = (aniIndex % 2) == 0 ? ENEMY_GRAY : ENEMY_RED;

        super.draw(g);
        if (DEBUG_MODE)
            drawSearchBox(g);
    }

    /**
     * Draw it just for debugging
     */
    protected void drawSearchBox(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawRect((int)searchBox.x, (int)searchBox.y, (int)searchBox.width, (int)searchBox.height);

        String searchMsg;
        if (!seePlayer)
            searchMsg = "Can't see; " + lastCase;
        else
            searchMsg = (pDirUp ? "U" : "") + (pDirDown ? "D" : "") + (pDirLeft ? "L" : "") + (pDirRight ? "R" : "") + "; " + lastCase;

        g.setColor(Color.WHITE);
        g.drawString(searchMsg, (int)(hitbox.x - 5), (hitbox.y - 5 > 0) ? (int)(hitbox.y - 5) : (int)(hitbox.y + hitbox.height + 10));

    }

    public boolean isWithPowerUp() {
        return withPowerUp;
    }
}
