package entities;

import static utils.Constants.TankTypeConstants.*;

public enum TankType {
    T_BASE(BASE,                    100, 1, 1.0f, 1.0f, 1000),
    T_LONG_GUN(LONG_GUN,            120, 1, 1.0f, 1.3f, 1000),
    T_BIG_GUN(BIG_GUN,              140, 1, 1.0f, 1.3f, 500),
    T_HEAVY(HEAVY,                  150, 3, 1.0f, 1.0f, 1000),
    T_FAST(FAST,                    150, 1, 1.5f, 1.0f, 500),
    T_SUPER_FAST(SUPER_FAST,        170, 1, 1.7f, 1.0f, 500),
    T_FAST_BIG_GUN(FAST_BIG_GUN,    180, 1, 1.7f, 1.3f, 500),
    T_SUPER_HEAVY(SUPER_HEAVY,      200, 5, 1.2f, 1.2f, 800);

    private final int id;
    private final int points;
    private final int maxHealth;
    private final float driveSpeed;
    private final float projectileSpeed;
    private final int shootDelayMS;

    TankType(int id, int points, int maxHealth, float driveSpeed, float projectileSpeed, int shootDelayMS) {
        this.id = id;
        this.points = points;
        this.maxHealth = maxHealth;
        this.driveSpeed = driveSpeed;
        this.projectileSpeed = projectileSpeed;
        this.shootDelayMS = shootDelayMS;
    }

    public int getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public float getDriveSpeed() {
        return driveSpeed;
    }

    public float getProjectileSpeed() {
        return projectileSpeed;
    }

    public int getShootDelayMS() {
        return shootDelayMS;
    }
}
