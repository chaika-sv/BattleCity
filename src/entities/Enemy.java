package entities;

import gamestates.Playing;
import main.Game;

import java.util.Random;

import static utils.Constants.DirConstants.*;
import static utils.Constants.TankStateConstants.*;

public class Enemy extends Tank{

    private boolean active = true;
    private Random rand;

    public Enemy(TankType tankType, float x, float y, int width, int height, Playing playing) {
        super(tankType, x, y, width, height, playing);
        this.down = true;
        this.attacking = true;

        rand = new Random();
    }

    /**
     * Projectile hit the enemy
     * @param damageValue how many health to decrease
     * @return true if the enemy was destroyed by the projectile
     */
    public boolean hitByProjectile(int damageValue) {
        currentHealth -= damageValue;
        if (currentHealth <= 0) {
            active = false;
            return true;
        }

        return false;
    }

    @Override
    public void update() {
        super.update();

        if (meetObstacle)
            changeDir();

        if (curDir == UP || curDir == DOWN)
            moveInOneDir = (int)(lastCoordinate - hitbox.y);
        else if (curDir == LEFT || curDir == RIGHT)
            moveInOneDir = (int)(lastCoordinate - hitbox.x);

        if (moveInOneDir >= curChangeDirDistance)
            changeDir();

    }

    private void changeDir() {

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
