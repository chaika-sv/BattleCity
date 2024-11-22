package entities;

import gamestates.Playing;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import static main.Game.TILES_DEFAULT_SIZE;
import static utils.Constants.DirConstants.*;
import static utils.Constants.EnemyConstants.*;

public class Enemy extends Tank{

    private Random rand;
    private Rectangle2D.Float searchBox;
    private boolean seePlayer = false;

    public Enemy(TankType tankType, float x, float y, Playing playing) {
        super(tankType, x, y, playing);
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

        updateSearchBox();

        if (meetObstacle)
            if (!seePlayer)
                changeDirRandomly();

        if (curDir == UP || curDir == DOWN)
            moveInOneDir = (int)(lastCoordinate - hitbox.y);
        else if (curDir == LEFT || curDir == RIGHT)
            moveInOneDir = (int)(lastCoordinate - hitbox.x);

        if (moveInOneDir >= curChangeDirDistance)
            changeDirRandomly();

    }

    /**
     * When move hitbox we need to move sprite that we are drawing
     */
    protected void updateSearchBox() {

        searchBox.x = x - (SEARCH_BOX_WIDTH - width) / 2f;
        searchBox.y = y - (SEARCH_BOX_HEIGHT - height) / 2f;

        Rectangle2D.Float playerHitbox = playing.getPlayer().getHitbox();

        if (searchBox.intersects(playerHitbox)) {
            seePlayer = true;
            resetDir();

            if (Math.abs(playerHitbox.x - hitbox.x) > TILES_DEFAULT_SIZE) {

                if (playerHitbox.x < hitbox.x)
                    left = true;
                else if (playerHitbox.x > hitbox.x)
                    right = true;

            } else if (Math.abs(playerHitbox.y - hitbox.y) > TILES_DEFAULT_SIZE) {

                if (playerHitbox.y < hitbox.y)
                    up = true;
                else if (playerHitbox.y > hitbox.y)
                    down = true;

            }
        } else {
            seePlayer = false;
        }

    }

    private void changeDirRandomly() {

        // Reset current direction
        resetDir();

        // Select next direction randomly
        int nextDir = rand.nextInt(4);

        // Make sure that it's not equal current direction
        while (nextDir == curDir)
            nextDir = rand.nextInt(4);

        curDir = nextDir;

        switch(curDir) {
            case UP -> up = true;
            case DOWN -> down = true;
            case LEFT -> left = true;
            case RIGHT -> right = true;
        }

        if (curDir == UP || curDir == DOWN)
            lastCoordinate = (int) hitbox.y;
        else if (curDir == LEFT || curDir == RIGHT)
            lastCoordinate = (int)hitbox.x;

        curChangeDirDistance = rand.nextInt(5) * 100;
    }

    private void resetDir() {
        up = false;
        down = false;
        left = false;
        right = false;
    }



    @Override
    public void draw(Graphics g) {
        super.draw(g);
        //if (DEBUG_MODE)
            drawSearchBox(g);
    }

    /**
     * Draw it just for debugging
     */
    protected void drawSearchBox(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawRect((int)searchBox.x, (int)searchBox.y, (int)searchBox.width, (int)searchBox.height);
    }
}
