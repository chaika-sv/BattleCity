package utils;

public class Constants {

    public static final boolean DEBUG_MODE = true;
    public static final float ANI_SPEED = 25;

    public static class TankDirConstants {
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
        public static final int LONG_GUN = 1;
        public static final int BIG_GUN = 2;
        public static final int HEAVY = 3;
        public static final int FAST = 4;
        public static final int SUPER_FAST = 5;
        public static final int FAST_BIG_GUN = 6;
        public static final int SUPER_HEAVY = 7;
    }

}
