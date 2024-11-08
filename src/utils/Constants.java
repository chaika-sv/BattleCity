package utils;

import main.Game;

public class Constants {

    public static final boolean DEBUG_MODE = false;
    public static final float ANI_SPEED = 25;



    public static class DirConstants {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
        public static final int UP = 2;
        public static final int DOWN = 3;
    }

    public static class TankStateConstants {
        public static final int IDLE = 0;
        public static final int MOVING = 1;
        public static final int ATTACK = 2;
        public static final int DEAD = 3;
    }

    public static class TankTypeConstants {
        public static final int BASE = 0;
        public static final int FAST = 1;
        public static final int POWER = 2;

        public static final int LONG_GUN = 8;
        public static final int BIG_GUN = 4;
        public static final int HEAVY = 3;
        public static final int SUPER_FAST = 5;
        public static final int FAST_BIG_GUN = 6;
        public static final int SUPER_HEAVY = 7;
    }

    public static class ProjectileConstants {
        public static final int PROJECTILE_DEFAULT_WIDTH = 12;
        public static final int PROJECTILE_DEFAULT_HEIGHT = 16;

        public static final int PROJECTILE_WIDTH = (int)(Game.SCALE * PROJECTILE_DEFAULT_WIDTH * 0.5f);
        public static final int PROJECTILE_HEIGHT = (int)(Game.SCALE * PROJECTILE_DEFAULT_HEIGHT * 0.5f);
        public static final float PROJECTILE_SPEED = 1.5f * Game.SCALE;
    }

    public static class ExplosionConstants {
        public static final int EXPLOSION_DEFAULT_WIDTH = 64;
        public static final int EXPLOSION_DEFAULT_HEIGHT = 64;

        public static final int EXPLOSION_WIDTH = (int)(Game.SCALE * EXPLOSION_DEFAULT_WIDTH);
        public static final int EXPLOSION_HEIGHT = (int)(Game.SCALE * EXPLOSION_DEFAULT_HEIGHT);
    }


}
