package utils;

import entities.EnemySettings;
import entities.TankType;
import levels.Level;
import levels.LevelBlockType;
import objects.TemporaryObjectType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static entities.TankType.*;
import static main.Game.TILES_DEFAULT_SIZE;
import static utils.Constants.DirConstants.*;
import static utils.Constants.LevelConstants.DRAW_GRASS;
import static utils.Constants.LevelConstants.LEVEL_DIR;
import static utils.Constants.ProjectileConstants.*;
import static utils.Constants.TankColorConstants.*;
import static utils.Constants.TankTypeConstants.*;
import static utils.Constants.TempObjectsConstants.*;

public class LoadSave {

    public static final String MAIN_SPRITE = "sprite.png";

    public static Map<LevelBlockType, BufferedImage> BLOCK_IMAGES;
    public static Map<Integer, BufferedImage> PROJECTILE_IMAGES;
    public static BufferedImage[][] TEMP_OBJECTS_IMAGES;    // small explosion, big explosion, enemy spawn
    public static BufferedImage[][][][] TANK_IMAGES;      // dim0: color, dim1: tank type, dim2: direction, dim3: animation
    public static int[][][] TANK_HITBOX_OFFSETS;      // dim1: tank type, dim2: direction, dim3: x, y
    public static Map<Integer, EnemySettings> ENEMY_SETTINGS;


    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
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

        Map<TankType, Integer> level0 = new LinkedHashMap<>();
        level0.put(T_BASE, 1);
        level0.put(T_FAST, 0);
        level0.put(T_POWER, 0);
        level0.put(T_HEAVY, 0);
        ENEMY_SETTINGS.put(0, new EnemySettings(level0, 4));
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

    public static void LoadTankImages() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.MAIN_SPRITE);

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
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.MAIN_SPRITE);

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
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.MAIN_SPRITE);

        PROJECTILE_IMAGES = new LinkedHashMap<>();

        PROJECTILE_IMAGES.put(LEFT, img.getSubimage(1248, 408, PROJECTILE_DEFAULT_HEIGHT, PROJECTILE_DEFAULT_WIDTH));
        PROJECTILE_IMAGES.put(RIGHT, img.getSubimage(1412, 408, PROJECTILE_DEFAULT_HEIGHT, PROJECTILE_DEFAULT_WIDTH));
        PROJECTILE_IMAGES.put(UP, img.getSubimage(1320, 408, PROJECTILE_DEFAULT_WIDTH, PROJECTILE_DEFAULT_HEIGHT));
        PROJECTILE_IMAGES.put(DOWN, img.getSubimage(1384, 408, PROJECTILE_DEFAULT_WIDTH, PROJECTILE_DEFAULT_HEIGHT));
    }

    public static void LoadTempObjectsImages() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.MAIN_SPRITE);

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
