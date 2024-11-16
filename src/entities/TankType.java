package entities;

import static utils.Constants.TankTypeConstants.*;

public enum TankType {
    T_BASE(BASE,                    100, 1, 1.0f, 1.2f, 1000, 52),
    T_FAST(FAST,                    200, 1, 3.0f, 3.2f, 500, 60),
    T_POWER(POWER,                  300, 1, 2.0f, 3.0f, 500, 60),
    T_HEAVY(HEAVY,                  400, 4, 2.0f, 2.2f, 500, 60),

    T_LONG_GUN(LONG_GUN,            120, 1, 1.0f, 1.3f, 1000, 52),
    T_BIG_GUN(BIG_GUN,              140, 1, 1.0f, 1.3f, 500, 52),
    T_SUPER_FAST(SUPER_FAST,        170, 1, 1.7f, 1.0f, 500, 52),
    T_FAST_BIG_GUN(FAST_BIG_GUN,    180, 1, 1.7f, 1.3f, 500, 52),
    T_SUPER_HEAVY(SUPER_HEAVY,      200, 5, 1.2f, 1.2f, 800, 52);

    private final int id;
    private final int points;
    private final int maxHealth;
    private final float driveSpeed;
    private final float projectileSpeed;
    private final int shootDelayMS;

    private final int hitboxSize;       // assuming it's the same in width and height

    TankType(int id, int points, int maxHealth, float driveSpeed, float projectileSpeed, int shootDelayMS, int hitboxSize) {
        this.id = id;
        this.points = points;
        this.maxHealth = maxHealth;
        this.driveSpeed = driveSpeed;
        this.projectileSpeed = projectileSpeed;
        this.shootDelayMS = shootDelayMS;
        this.hitboxSize = hitboxSize;
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

    public int getHitboxSize() {
        return hitboxSize;
    }
}
