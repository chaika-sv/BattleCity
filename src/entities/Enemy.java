package entities;

import gamestates.Playing;
import main.Game;

import static utils.Constants.TankStateConstants.IDLE;

public class Enemy extends Tank{

    private boolean active = true;

    public Enemy(TankType tankType, float x, float y, int width, int height, Playing playing) {
        super(tankType, x, y, width, height, playing);
        this.state = IDLE;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
