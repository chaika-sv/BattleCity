package entities;

import static utils.Constants.TankTypeConstants.*;

public enum TankType {
    T_BASE_PLAYER(BASE,             100, 5, 1.0f, 2.5f, 500, 52, 1.0f),
    T_BASE(BASE,                    100, 1, 1.0f, 2.5f, 1000, 52, 1.0f),
    T_FAST(FAST,                    200, 1, 1.3f, 2.5f, 500, 52, 0.9f),
    T_POWER(POWER,                  300, 1, 1.2f, 2.5f, 500, 52, 0.9f),
    T_HEAVY(HEAVY,                  400, 4, 1.2f, 2.5f, 500, 52, 0.9f),

    T_LONG_GUN(LONG_GUN,            120, 1, 1.0f, 1.3f, 1000, 52, 0.9f),
    T_BIG_GUN(BIG_GUN,              140, 1, 1.0f, 1.3f, 500, 52, 0.9f),
    T_SUPER_FAST(SUPER_FAST,        170, 1, 1.7f, 1.0f, 500, 52, 0.9f),
    T_FAST_BIG_GUN(FAST_BIG_GUN,    180, 1, 1.7f, 1.3f, 500, 52, 0.9f),
    T_SUPER_HEAVY(SUPER_HEAVY,      200, 5, 1.2f, 1.2f, 800, 52, 0.9f);

    private final int id;
    private final int points;
    private final int maxHealth;
    private final float driveSpeed;
    private final float projectileSpeed;
    private final int shootDelayMS;

    private final int hitboxSize;       // assuming it's the same in width and height
    private final float tankScale;

    TankType(int id, int points, int maxHealth, float driveSpeed, float projectileSpeed, int shootDelayMS, int hitboxSize, float tankScale) {
        this.id = id;
        this.points = points;
        this.maxHealth = maxHealth;
        this.driveSpeed = driveSpeed;
        this.projectileSpeed = projectileSpeed;
        this.shootDelayMS = shootDelayMS;
        this.hitboxSize = hitboxSize;
        this.tankScale = tankScale;
    }

    public static TankType getTankTypeByCode(int tankCode) {
        switch (tankCode) {
            case 0 -> { return T_BASE; }
            case 1 -> { return T_FAST; }
            case 2 -> { return T_POWER; }
            case 3 -> { return T_HEAVY; }
            default -> { return T_BASE; }
        }
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

    public float getTankScale() {
        return tankScale;
    }
}
