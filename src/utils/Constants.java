package utils;

import main.Game;

import java.awt.*;
import java.util.List;

import static main.Game.TILES_IN_HEIGHT;
import static main.Game.TILES_SIZE;

public class Constants {

    public static final boolean DEBUG_MODE = false;
    public static final float ANI_SPEED = 25;

    public static class MovementConstants {
        public static final int TANK_FRONT_AREA = 3;
        public static final int MAX_SHIFT = 16;
    }

    public static class DirConstants {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
        public static final int UP = 2;
        public static final int DOWN = 3;
    }

    public static class PowerUpConstants {
        public static final int PU_SHIELD = 0;
        public static final int PU_TIMER = 1;
        public static final int PU_SHOVEL = 2;
        public static final int PU_STAR = 3;
        public static final int PU_GRENADE = 4;
        public static final int PU_HEALTH = 5;
        public static final int PU_GUN = 6;
        public static final int POWER_UP_ACTIVE_TIME_MS = 20000;
        public static final int FREEZE_TIME_MS = 10000;
        public static final int WALL_TIME_MS = 10000;
        public static final int MAX_POWER_UP_NUMBER = 6;
    }

    public static class EnemyConstants {
        public static final int ENEMY_SPAWN_DELAY_MS = 4000;
        public static final int ENEMY_SPAWN_X_1 = 0;
        public static final int ENEMY_SPAWN_Y_1 = 0;
        public static final int ENEMY_SPAWN_X_2 = 8 * TILES_SIZE;
        public static final int ENEMY_SPAWN_Y_2 = 0;
        public static final int ENEMY_SPAWN_X_3 = 14 * TILES_SIZE;
        public static final int ENEMY_SPAWN_Y_3 = 0;
        public static final int SEARCH_BOX_WIDTH = (int)(10 * TILES_SIZE * Game.SCALE);
        public static final int SEARCH_BOX_HEIGHT = (int)(10 * TILES_SIZE * Game.SCALE);
        public static final long CHANGE_DIR_DELAY_MS = 2000;
    }

    public static class TankColorConstants {
        public static final int PLAYER_YELLOW = 0;
        public static final int ENEMY_GRAY = 1;
        public static final int PLAYER_GREEN = 2;
        public static final int ENEMY_RED = 3;
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
        public static final int HEAVY = 3;

        public static final int BIG_GUN = 4;
        public static final int SUPER_FAST = 5;
        public static final int FAST_BIG_GUN = 6;
        public static final int SUPER_HEAVY = 7;
        public static final int LONG_GUN = 8;

        public static final int MAX_TANK_TYPE = 2;
    }

    public static class ProjectileConstants {
        public static final int PROJECTILE_DEFAULT_WIDTH = 12;
        public static final int PROJECTILE_DEFAULT_HEIGHT = 16;

        public static final int PROJECTILE_WIDTH = (int)(Game.SCALE * PROJECTILE_DEFAULT_WIDTH * 0.75f);
        public static final int PROJECTILE_HEIGHT = (int)(Game.SCALE * PROJECTILE_DEFAULT_HEIGHT * 0.75f);
    }

    public static class TempObjectsConstants {
        public static final int SMALL_EXPLOSION = 0;
        public static final int BIG_EXPLOSION = 1;
        public static final int SPAWN = 2;
        public static final int SHIELD = 3;

        public static final int EXPLOSION_DEFAULT_WIDTH = 64;
        public static final int EXPLOSION_DEFAULT_HEIGHT = 64;

        public static final int EXPLOSION_WIDTH = (int)(Game.SCALE * EXPLOSION_DEFAULT_WIDTH);
        public static final int EXPLOSION_HEIGHT = (int)(Game.SCALE * EXPLOSION_DEFAULT_HEIGHT);

        public static final int BIG_EXPLOSION_DEFAULT_WIDTH = 128;
        public static final int BIG_EXPLOSION_DEFAULT_HEIGHT = 128;

        public static final int BIG_EXPLOSION_WIDTH = (int)(Game.SCALE * BIG_EXPLOSION_DEFAULT_WIDTH);
        public static final int BIG_EXPLOSION_HEIGHT = (int)(Game.SCALE * BIG_EXPLOSION_DEFAULT_HEIGHT);

        public static final int SHIELD_OFFSET_X = (int)(Game.SCALE * (-2));
        public static final int SHIELD_OFFSET_Y = (int)(Game.SCALE * (-2));
    }

    public static class LevelConstants {
        public static final int START_FROM_LEVEL = 0;
        public static final int LEVELS_COUNT = 3;
        public static final int DRAW_ALL_LEVEL = 0;
        public static final int DRAW_LEVEL_WO_GRASS = 1;
        public static final int DRAW_GRASS = 2;
        public static final String LEVEL_DIR = "res/levels";
        public static final int PLAYER_1_SPAWN_X = 5 * TILES_SIZE;
        public static final int PLAYER_1_SPAWN_Y = (TILES_IN_HEIGHT - 1) * TILES_SIZE;
        public static final int PLAYER_2_SPAWN_X = 10 * TILES_SIZE;
        public static final int PLAYER_2_SPAWN_Y = (TILES_IN_HEIGHT - 1) * TILES_SIZE;
        public static final List<Point> BASE_WALL_POINTS = List.of(
                new Point(416, 864),
                new Point(416, 896),
                new Point(416, 928),

                new Point(448, 864),
                new Point(480, 864),

                new Point(512, 864),
                new Point(512, 896),
                new Point(512, 928)
        );
    }

    public static class Audio {
        public static int FIRE = 0;
        public static int LEVEL_INTRO = 1;
        public static int TANK_IDLE = 2;
        public static int TANK_MOVE = 3;
        public static int ENEMY_EXPLOSION = 4;
        public static int GAME_OVER = 5;
        public static int HIGH_SCORE = 6;
        public static int HIT_BRICK = 7;
        public static int HIT_ENEMY = 8;
        public static int HIT_STEEL = 9;
        public static int ICE = 10;
        public static int LIFE = 11;
        public static int PAUSE = 12;
        public static int PLAYER_EXPLOSION = 13;
        public static int POWER_UP_APPEAR = 14;
        public static int POWER_UP_PICKUP = 15;
        public static int SCORE = 16;
        public static int SCORE_BONUS = 17;
        public static int UNKNOWN_1 = 18;
        public static int UNKNOWN_2 = 19;
        public static int VICTORY = 20;
    }

}
