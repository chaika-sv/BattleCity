package utils;

import entities.EnemySettings;
import entities.TankType;
import levels.Level;
import levels.LevelBlockType;
import objects.TemporaryObjectType;
import ui.MenuItemType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static entities.TankType.*;
import static main.Game.TILES_DEFAULT_SIZE;
import static ui.MenuItemType.*;
import static utils.Constants.DirConstants.*;
import static utils.Constants.LevelConstants.LEVEL_DIR;
import static utils.Constants.ProjectileConstants.*;
import static utils.Constants.TankColorConstants.*;
import static utils.Constants.TankTypeConstants.*;
import static utils.Constants.TempObjectsConstants.*;

public class LoadSaveImages {

    public static final String MAIN_SPRITE = "sprite.png";
    public static final String LOGO_IMG = "logo.png";
    public static final String COPYRIGHT_IMG = "copyright.png";

    public static Map<LevelBlockType, BufferedImage> BLOCK_IMAGES;
    public static Map<Integer, BufferedImage> PROJECTILE_IMAGES;
    public static BufferedImage[][] TEMP_OBJECTS_IMAGES;    // small explosion, big explosion, enemy spawn
    public static BufferedImage[][][][] TANK_IMAGES;      // dim0: color, dim1: tank type, dim2: direction, dim3: animation
    public static int[][][] TANK_HITBOX_OFFSETS;      // dim1: tank type, dim2: direction, dim3: x, y
    public static Map<Integer, EnemySettings> ENEMY_SETTINGS;

    public static BufferedImage[] NUMBER_IMAGES;
    public static BufferedImage FIRST_PLAYER_INFO_IMG;
    public static BufferedImage SECOND_PLAYER_INFO_IMG;
    public static BufferedImage PLAYER_TANK_CNT_IMG;
    public static BufferedImage ENEMY_TANK_CNT_IMG;
    public static BufferedImage STAGE_NUM_IMG;
    public static BufferedImage PAUSE_IMG;
    public static BufferedImage STAGE_IMG;
    public static BufferedImage GAME_OVER_IMG;
    public static Map<Integer, BufferedImage> POINTS_IMAGES;

    public static Map<MenuItemType, BufferedImage> MENU_ITEM_IMAGES;
    public static BufferedImage[] POWER_UP_IMAGES;


    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSaveImages.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static void SetEnemySettings() {
        ENEMY_SETTINGS = new LinkedHashMap<>();

        Map<TankType, Integer> settings = new LinkedHashMap<>();

        ENEMY_SETTINGS.put(0, new EnemySettings(new LinkedHashMap<>() {{
            put(T_BASE, 1);
            put(T_FAST, 0);
            put(T_POWER, 0);
            put(T_HEAVY, 0);
        }}, 4, 0));

        ENEMY_SETTINGS.put(1, new EnemySettings(new LinkedHashMap<>() {{
            put(T_BASE, 0);
            put(T_FAST, 1);
            put(T_POWER, 0);
            put(T_HEAVY, 0);
        }}, 4, 1));

        ENEMY_SETTINGS.put(2, new EnemySettings(new LinkedHashMap<>() {{
            put(T_BASE, 0);
            put(T_FAST, 0);
            put(T_POWER, 1);
            put(T_HEAVY, 0);
        }}, 4, 1));

    }


    public static void LoadLevelFromFile(String fileName, Level level) {
        if (!fileName.equals("")) {
            try {
                FileInputStream fis = new FileInputStream(LEVEL_DIR + "/" + fileName);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Level loadedLevel = (Level) ois.readObject();
                level.cloneLevel(loadedLevel);
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void SaveLevelToFile(String fileName, Level level) {
        if (!fileName.equals("")) {
            try {
                FileOutputStream fos = new FileOutputStream(LEVEL_DIR + "/" + fileName);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(level);
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void LoadInfoPanelImages() {
        BufferedImage img = LoadSaveImages.GetSpriteAtlas(LoadSaveImages.MAIN_SPRITE);
        FIRST_PLAYER_INFO_IMG = img.getSubimage(1534, 544, 64, 32);
        SECOND_PLAYER_INFO_IMG = img.getSubimage(1534, 640, 64, 32);
        PLAYER_TANK_CNT_IMG = img.getSubimage(1534, 672, 32, 32);
        ENEMY_TANK_CNT_IMG = img.getSubimage(1310, 770, 32, 32);
        STAGE_NUM_IMG = img.getSubimage(1532, 734, 64, 64);

        NUMBER_IMAGES = new BufferedImage[10];

        // 0..9
        for (int i = 0; i < 5; i++) {
            NUMBER_IMAGES[i] = img.getSubimage(1342 + i * 32, 734, 32, 32);
            NUMBER_IMAGES[i + 5] = img.getSubimage(1342 + i * 32, 766, 32, 32);
        }

        PAUSE_IMG = img.getSubimage(1182, 702, 160, 32);
        GAME_OVER_IMG = img.getSubimage(1182, 734, 128, 64);
        STAGE_IMG = img.getSubimage(1342, 702, 160, 32);

        POINTS_IMAGES = new LinkedHashMap<>();
        POINTS_IMAGES.put(100, img.getSubimage(1182, 654, 64, 32));
        POINTS_IMAGES.put(200, img.getSubimage(1246, 654, 64, 32));
        POINTS_IMAGES.put(300, img.getSubimage(1310, 654, 64, 32));
        POINTS_IMAGES.put(400, img.getSubimage(1374, 654, 64, 32));
        POINTS_IMAGES.put(500, img.getSubimage(1438, 654, 64, 32));

        MENU_ITEM_IMAGES = new LinkedHashMap<>();
        MENU_ITEM_IMAGES.put(MI_RESTART, img.getSubimage(1182, 832, MI_RESTART.getWidth(), MI_RESTART.getHeight()));
        MENU_ITEM_IMAGES.put(MI_MAIN_MENU, img.getSubimage(1182, 864, MI_MAIN_MENU.getWidth(), MI_MAIN_MENU.getHeight()));
        MENU_ITEM_IMAGES.put(MI_EXIT_GAME, img.getSubimage(1182, 896, MI_EXIT_GAME.getWidth(), MI_EXIT_GAME.getHeight()));
        MENU_ITEM_IMAGES.put(MI_RESUME, img.getSubimage(1182, 928, MI_RESUME.getWidth(), MI_RESUME.getHeight()));

        MENU_ITEM_IMAGES.put(MI_1_PLAYER, img.getSubimage(1182, 960, MI_1_PLAYER.getWidth(), MI_1_PLAYER.getHeight()));
        MENU_ITEM_IMAGES.put(MI_2_PLAYERS, img.getSubimage(1182, 992, MI_2_PLAYERS.getWidth(), MI_2_PLAYERS.getHeight()));
        MENU_ITEM_IMAGES.put(MI_EDITOR, img.getSubimage(1278, 864, MI_EDITOR.getWidth(), MI_EDITOR.getHeight()));

    }



    public static void loadPowerUpImages() {
        BufferedImage img = LoadSaveImages.GetSpriteAtlas(LoadSaveImages.MAIN_SPRITE);

        POWER_UP_IMAGES = new BufferedImage[7];

        // Find all the power ups from 0 to 6 in PowerUpConstants
        for (int i = 0; i < POWER_UP_IMAGES.length; i++)
            POWER_UP_IMAGES[i] = img.getSubimage(1054 + i * TILES_DEFAULT_SIZE, 446, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);

    }




    public static void LoadTankImages() {
        BufferedImage img = LoadSaveImages.GetSpriteAtlas(LoadSaveImages.MAIN_SPRITE);

        int cX = 0;
        int cY = 0;
        int aX = 0;
        int aY = 0;
        int s = TILES_DEFAULT_SIZE;

        // 0 - tank color (4 colors)
        // 0 - tank types (4 types)
        // 2 - direction (4 directions)
        // 3 - animation (2 ani indexes)
        TANK_IMAGES = new BufferedImage[4][4][4][2];

        // 0 - tank types (4 types)
        // 2 - direction (4 directions)
        // 3 - x, y
        TANK_HITBOX_OFFSETS = new int[4][4][2];

        for (int i = 0; i < 4; i++) {

            switch (i) {
                case PLAYER_YELLOW -> { cX = 0;     cY = 0;     aY = 0; }
                case ENEMY_GRAY -> { cX = 520;     cY = 0;    aY = 0; }
                case PLAYER_GREEN -> { cX = 0;     cY = 520;     aY = 4; }
                case ENEMY_RED -> { cX = 520;     cY = 520;     aY = 4; }
            }


            aX = 0;
            TANK_IMAGES[i][BASE][UP][0] = img.getSubimage(cX, cY + aY, s, s);
            TANK_IMAGES[i][BASE][UP][1] = img.getSubimage(cX + s,cY + aY, s, s);
            TANK_HITBOX_OFFSETS[BASE][UP][0] = 4;
            TANK_HITBOX_OFFSETS[BASE][UP][1] = 8;

            aX = (i == PLAYER_YELLOW || i == PLAYER_GREEN) ? 4 : 0;
            TANK_IMAGES[i][BASE][LEFT][0] = img.getSubimage(cX + s * 2,cY + aY, s, s);
            TANK_IMAGES[i][BASE][LEFT][1] = img.getSubimage(cX + s * 3 + aX, cY + aY,s, s);
            TANK_HITBOX_OFFSETS[BASE][LEFT][0] = 8;
            TANK_HITBOX_OFFSETS[BASE][LEFT][1] = 4;

            aX = (i == PLAYER_YELLOW || i == PLAYER_GREEN) ? 4 : 0;
            TANK_IMAGES[i][BASE][DOWN][0] = img.getSubimage(cX + s * 4 + aX, cY + aY, s, s);
            TANK_IMAGES[i][BASE][DOWN][1] = img.getSubimage(cX + s * 5 + aX, cY + aY, s, s);
            TANK_HITBOX_OFFSETS[BASE][DOWN][0] = 4;
            TANK_HITBOX_OFFSETS[BASE][DOWN][1] = 4;

            aX = (i == PLAYER_YELLOW || i == PLAYER_GREEN) ? 4 : 0;
            TANK_IMAGES[i][BASE][RIGHT][0] = img.getSubimage(cX + s * 6 + aX, cY + aY, s, s);
            aX = (i == PLAYER_YELLOW || i == PLAYER_GREEN) ? 8 : 0;
            TANK_IMAGES[i][BASE][RIGHT][1] = img.getSubimage(cX + s * 7 + aX, cY + aY, s, s);
            TANK_HITBOX_OFFSETS[BASE][RIGHT][0] = 4;
            TANK_HITBOX_OFFSETS[BASE][RIGHT][1] = 4;



            aY = (i == PLAYER_GREEN || i == ENEMY_RED) ? 4 : 0;;
            TANK_IMAGES[i][FAST][UP][0] = img.getSubimage(cX, cY + aY + 328, s, s);
            TANK_IMAGES[i][FAST][UP][1] = img.getSubimage(cX + s, cY + aY + 328, s, s);
            TANK_HITBOX_OFFSETS[FAST][UP][0] = 0;
            TANK_HITBOX_OFFSETS[FAST][UP][1] = 0;

            aX = (i == PLAYER_YELLOW || i == PLAYER_GREEN) ? 4 : 0;
            aY = (i == PLAYER_GREEN || i == ENEMY_RED) ? 4 : 0;;
            TANK_IMAGES[i][FAST][LEFT][0] = img.getSubimage(cX + s * 2, cY + aY + 328, s, s);
            TANK_IMAGES[i][FAST][LEFT][1] = img.getSubimage(cX + s * 3 + aX, cY + + aY + 328, s, s);
            TANK_HITBOX_OFFSETS[FAST][LEFT][0] = 0;
            TANK_HITBOX_OFFSETS[FAST][LEFT][1] = 4;

            aX = (i == PLAYER_YELLOW || i == PLAYER_GREEN) ? 4 : 0;
            aY = (i == PLAYER_GREEN || i == ENEMY_RED) ? 4 : 0;;
            TANK_IMAGES[i][FAST][DOWN][0] = img.getSubimage(cX + s * 4 + aX, cY + aY + 328, s, s);
            TANK_IMAGES[i][FAST][DOWN][1] = img.getSubimage(cX + s * 5 + aX, cY + aY + 328, s, s);
            TANK_HITBOX_OFFSETS[FAST][DOWN][0] = 0;
            TANK_HITBOX_OFFSETS[FAST][DOWN][1] = 4;

            aX = (i == PLAYER_YELLOW || i == PLAYER_GREEN) ? 4 : 0;
            aY = (i == PLAYER_GREEN || i == ENEMY_RED) ? 4 : 0;;
            TANK_IMAGES[i][FAST][RIGHT][0] = img.getSubimage(cX + s * 6 + aX, cY + aY + 328, s, s);
            aX = (i == PLAYER_YELLOW || i == PLAYER_GREEN) ? 8 : 0;
            TANK_IMAGES[i][FAST][RIGHT][1] = img.getSubimage(cX + s * 7 + aX, cY + aY + 328, s, s);
            TANK_HITBOX_OFFSETS[FAST][RIGHT][0] = 4;
            TANK_HITBOX_OFFSETS[FAST][RIGHT][1] = 0;



            aY = (i == PLAYER_GREEN || i == ENEMY_RED) ? 4 : 0;;
            TANK_IMAGES[i][POWER][UP][0] = img.getSubimage(cX, cY + aY + 132, s, s);
            TANK_IMAGES[i][POWER][UP][1] = img.getSubimage(cX + s, cY + aY + 132, s, s);
            TANK_HITBOX_OFFSETS[POWER][UP][0] = 0;
            TANK_HITBOX_OFFSETS[POWER][UP][1] = 0;

            aX = (i == PLAYER_YELLOW || i == PLAYER_GREEN) ? 4 : 0;
            aY = (i == PLAYER_GREEN || i == ENEMY_RED) ? 4 : 0;;
            TANK_IMAGES[i][POWER][LEFT][0] = img.getSubimage(cX + s * 2, cY + aY + 132, s, s);
            TANK_IMAGES[i][POWER][LEFT][1] = img.getSubimage(cX + s * 3 + aX, cY + aY + 132, s, s);
            TANK_HITBOX_OFFSETS[POWER][LEFT][0] = 0;
            TANK_HITBOX_OFFSETS[POWER][LEFT][1] = 0;

            aX = (i == PLAYER_YELLOW || i == PLAYER_GREEN) ? 4 : 0;
            aY = (i == PLAYER_GREEN || i == ENEMY_RED) ? 4 : 0;;
            TANK_IMAGES[i][POWER][DOWN][0] = img.getSubimage(cX + s * 4 + aX, cY + aY + 132, s, s);
            TANK_IMAGES[i][POWER][DOWN][1] = img.getSubimage(cX + s * 5 + aX, cY + aY + 132, s, s);
            TANK_HITBOX_OFFSETS[POWER][DOWN][0] = 0;
            TANK_HITBOX_OFFSETS[POWER][DOWN][1] = 4;

            aX = (i == PLAYER_YELLOW || i == PLAYER_GREEN) ? 4 : 0;
            aY = (i == PLAYER_GREEN || i == ENEMY_RED) ? 4 : 0;;
            TANK_IMAGES[i][POWER][RIGHT][0] = img.getSubimage(cX + s * 6 + aX, cY + aY + 132, s, s);
            aX = (i == PLAYER_YELLOW || i == PLAYER_GREEN) ? 8 : 0;
            TANK_IMAGES[i][POWER][RIGHT][1] = img.getSubimage(cX + s * 7 + aX, cY + aY + 132, s, s);
            TANK_HITBOX_OFFSETS[POWER][RIGHT][0] = 4;
            TANK_HITBOX_OFFSETS[POWER][RIGHT][1] = 0;



            aX = 0;
            aY = (i == PLAYER_GREEN || i == ENEMY_RED) ? 4 : 0;;
            TANK_IMAGES[i][HEAVY][UP][0] = img.getSubimage(cX, cY + aY + 458, s, s);
            TANK_IMAGES[i][HEAVY][UP][1] = img.getSubimage(cX + s, cY + aY + 458, s, s);
            TANK_HITBOX_OFFSETS[HEAVY][UP][0] = 0;
            TANK_HITBOX_OFFSETS[HEAVY][UP][1] = 2;

            aX = (i == PLAYER_YELLOW || i == PLAYER_GREEN) ? 4 : 0;
            aY = (i == PLAYER_GREEN || i == ENEMY_RED) ? 4 : 0;;
            TANK_IMAGES[i][HEAVY][LEFT][0] = img.getSubimage(cX + s * 2, cY + aY + 458, s, s);
            TANK_IMAGES[i][HEAVY][LEFT][1] = img.getSubimage(cX + s * 3 + aX, cY + aY + 458, s, s);
            TANK_HITBOX_OFFSETS[HEAVY][LEFT][0] = 0;
            TANK_HITBOX_OFFSETS[HEAVY][LEFT][1] = 2;

            aX = (i == PLAYER_YELLOW || i == PLAYER_GREEN) ? 4 : 0;
            aY = (i == PLAYER_GREEN || i == ENEMY_RED) ? 4 : 0;;
            TANK_IMAGES[i][HEAVY][DOWN][0] = img.getSubimage(cX + s * 4 + aX, cY + aY + 458, s, s);
            TANK_IMAGES[i][HEAVY][DOWN][1] = img.getSubimage(cX + s * 5 + aX, cY + aY + 458, s, s);
            TANK_HITBOX_OFFSETS[HEAVY][DOWN][0] = 0;
            TANK_HITBOX_OFFSETS[HEAVY][DOWN][1] = 2;

            aX = (i == PLAYER_YELLOW || i == PLAYER_GREEN) ? 4 : 0;
            aY = (i == PLAYER_GREEN || i == ENEMY_RED) ? 4 : 0;;
            TANK_IMAGES[i][HEAVY][RIGHT][0] = img.getSubimage(cX + s * 6 + aX, cY + aY + 458, s, s);
            aX = (i == PLAYER_YELLOW || i == PLAYER_GREEN) ? 8 : 0;
            TANK_IMAGES[i][HEAVY][RIGHT][1] = img.getSubimage(cX + s * 7 + aX, cY + aY + 458, s, s);
            TANK_HITBOX_OFFSETS[HEAVY][RIGHT][0] = 0;
            TANK_HITBOX_OFFSETS[HEAVY][RIGHT][1] = 2;
        }

    }


    public static void LoadBlockImages() {
        BufferedImage img = LoadSaveImages.GetSpriteAtlas(LoadSaveImages.MAIN_SPRITE);

        BLOCK_IMAGES = new LinkedHashMap<>();

        BLOCK_IMAGES.put(LevelBlockType.BRICK_BIG, img.getSubimage(1052, 0, 64, 64));
        BLOCK_IMAGES.put(LevelBlockType.METAL_BIG, img.getSubimage(1052, 64, 64, 64));
        BLOCK_IMAGES.put(LevelBlockType.RIVER1_BIG, img.getSubimage(1052, 128, 64, 64));
        BLOCK_IMAGES.put(LevelBlockType.GRASS_BIG, img.getSubimage(1116, 128, 64, 64));
        BLOCK_IMAGES.put(LevelBlockType.RIVER2_BIG, img.getSubimage(1052, 192, 64, 64));
        BLOCK_IMAGES.put(LevelBlockType.RIVER3_BIG, img.getSubimage(1116, 192, 64, 64));
        BLOCK_IMAGES.put(LevelBlockType.ICE_BIG, img.getSubimage(1180, 128, 64, 64));

        BLOCK_IMAGES.put(LevelBlockType.BRICK_SMALL, img.getSubimage(1052, 256, 32, 32));
        BLOCK_IMAGES.put(LevelBlockType.METAL_SMALL, img.getSubimage(1052, 288, 32, 32));
        BLOCK_IMAGES.put(LevelBlockType.GRASS_SMALL, img.getSubimage(1084, 288, 32, 32));
        BLOCK_IMAGES.put(LevelBlockType.ICE_SMALL, img.getSubimage(1116, 288, 32, 32));
        BLOCK_IMAGES.put(LevelBlockType.RIVER1_SMALL, img.getSubimage(1052, 320, 32, 32));
        BLOCK_IMAGES.put(LevelBlockType.RIVER2_SMALL, img.getSubimage(1084, 320, 32, 32));
        BLOCK_IMAGES.put(LevelBlockType.RIVER3_SMALL, img.getSubimage(1116, 320, 32, 32));

        BLOCK_IMAGES.put(LevelBlockType.BRICK_HALF_SMALL, img.getSubimage(1180, 256, 32, 16));
        BLOCK_IMAGES.put(LevelBlockType.BRICK_LITTLE1, img.getSubimage(1180, 256, 16, 16));
        BLOCK_IMAGES.put(LevelBlockType.BRICK_LITTLE2, img.getSubimage(1196, 256, 16, 16));
        BLOCK_IMAGES.put(LevelBlockType.BRICK_HALF, img.getSubimage(1308, 0, 64, 32));
        BLOCK_IMAGES.put(LevelBlockType.METAL_HALF, img.getSubimage(1308, 64, 64, 32));

        BLOCK_IMAGES.put(LevelBlockType.BASE_UP, img.getSubimage(1244, 128, 64, 64));
        BLOCK_IMAGES.put(LevelBlockType.BASE_DOWN, img.getSubimage(1308, 128, 64, 64));

        BLOCK_IMAGES.put(LevelBlockType.ERASE, img.getSubimage(1372, 128, 64, 64));
        BLOCK_IMAGES.put(LevelBlockType.SAVE, img.getSubimage(1436, 128, 64, 64));
        BLOCK_IMAGES.put(LevelBlockType.OPEN, img.getSubimage(1436, 192, 64, 64));
    }

    public static void LoadProjectileImages() {
        BufferedImage img = LoadSaveImages.GetSpriteAtlas(LoadSaveImages.MAIN_SPRITE);

        PROJECTILE_IMAGES = new LinkedHashMap<>();

        PROJECTILE_IMAGES.put(LEFT, img.getSubimage(1248, 408, PROJECTILE_DEFAULT_HEIGHT, PROJECTILE_DEFAULT_WIDTH));
        PROJECTILE_IMAGES.put(RIGHT, img.getSubimage(1412, 408, PROJECTILE_DEFAULT_HEIGHT, PROJECTILE_DEFAULT_WIDTH));
        PROJECTILE_IMAGES.put(UP, img.getSubimage(1320, 408, PROJECTILE_DEFAULT_WIDTH, PROJECTILE_DEFAULT_HEIGHT));
        PROJECTILE_IMAGES.put(DOWN, img.getSubimage(1384, 408, PROJECTILE_DEFAULT_WIDTH, PROJECTILE_DEFAULT_HEIGHT));
    }

    public static void LoadTempObjectsImages() {
        BufferedImage img = LoadSaveImages.GetSpriteAtlas(LoadSaveImages.MAIN_SPRITE);

        TEMP_OBJECTS_IMAGES = new BufferedImage[4][4];

        // Small explosion
        for (int i = 0; i < TemporaryObjectType.TO_SMALL_EXPLOSION.getSpritesNumber(); i++)
            TEMP_OBJECTS_IMAGES[SMALL_EXPLOSION][i] = img.getSubimage(
                    1052 + TemporaryObjectType.TO_SMALL_EXPLOSION.getWidth() * i ,
                    512,
                    TemporaryObjectType.TO_SMALL_EXPLOSION.getWidth(),
                    TemporaryObjectType.TO_SMALL_EXPLOSION.getHeight());

        // Big explosion
        for (int i = 0; i < TemporaryObjectType.TO_BIG_EXPLOSION.getSpritesNumber(); i++)
            TEMP_OBJECTS_IMAGES[BIG_EXPLOSION][i] = img.getSubimage(
                    1244 + TemporaryObjectType.TO_BIG_EXPLOSION.getWidth() * i ,
                    512,
                    TemporaryObjectType.TO_BIG_EXPLOSION.getWidth(),
                    TemporaryObjectType.TO_BIG_EXPLOSION.getHeight());

        // Enemy spawn
        for (int i = 0; i < TemporaryObjectType.TO_SPAWN.getSpritesNumber(); i++)
            TEMP_OBJECTS_IMAGES[SPAWN][i] = img.getSubimage(
                    1052 + TemporaryObjectType.TO_SPAWN.getWidth() * i ,
                    382,
                    TemporaryObjectType.TO_SPAWN.getWidth(),
                    TemporaryObjectType.TO_SPAWN.getHeight());

        // Shield
        for (int i = 0; i < TemporaryObjectType.TO_SHIELD.getSpritesNumber(); i++)
            TEMP_OBJECTS_IMAGES[SHIELD][i] = img.getSubimage(
                    1052 + TemporaryObjectType.TO_SHIELD.getWidth() * i ,
                    576,
                    TemporaryObjectType.TO_SHIELD.getWidth(),
                    TemporaryObjectType.TO_SHIELD.getHeight());
    }

}
