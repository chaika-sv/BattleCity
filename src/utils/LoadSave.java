package utils;

import levels.Level;
import levels.LevelBlockType;
import objects.TemporaryObjectType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static main.Game.TILES_DEFAULT_SIZE;
import static utils.Constants.DirConstants.*;
import static utils.Constants.LevelConstants.LEVEL_DIR;
import static utils.Constants.ProjectileConstants.*;
import static utils.Constants.TankColorConstants.*;
import static utils.Constants.TankTypeConstants.*;
import static utils.Constants.TempObjectsConstants.*;

public class LoadSave {

    public static final String MAIN_SPRITE = "sprite.png";

    public static Map<LevelBlockType, BufferedImage> BLOCK_IMAGES;
    public static Map<Integer, BufferedImage> PROJECTILE_IMAGES;
    public static BufferedImage[][] TEMP_OBJECTS_IMAGES;
    public static BufferedImage[] BIG_EXPLOSION_IMAGES;
    public static BufferedImage[][][][] TANK_IMAGES;      // dim0: color, dim1: tank type, dim2: direction, dim3: animation
    public static int[][][] TANK_HITBOX_OFFSETS;      // dim1: tank type, dim2: direction, dim3: x, y


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

    public static void LoadLevelFromFile(String fileName, Level level) {
        if (!fileName.equals("")) {
            try {
                FileInputStream fis = new FileInputStream(LEVEL_DIR + "/" + fileName);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Level loadedLevel = (Level) ois.readObject();
                level.copyLevel(loadedLevel);
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

        int colorOffsetX = 0;
        int colorOffsetY = 0;

        // 0 - tank color (4 colors)
        // 1 - tank types (4 types)
        // 2 - direction (4 directions)
        // 3 - animation (2 ani indexes)
        TANK_IMAGES = new BufferedImage[4][4][4][2];

        // 1 - tank types (4 types)
        // 2 - direction (4 directions)
        // 3 - x, y
        TANK_HITBOX_OFFSETS = new int[4][4][2];

        for (int i = 0; i < 4; i++) {

            switch (i) {
                case PLAYER_YELLOW -> { colorOffsetX = 0;     colorOffsetY = 0; }
                case ENEMY_GRAY -> { colorOffsetX = 520;     colorOffsetY = 0; }
                case PLAYER_GREEN -> { colorOffsetX = 0;     colorOffsetY = 520; }
                case ENEMY_RED -> { colorOffsetX = 520;     colorOffsetY = 520; }
            }
            
            TANK_IMAGES[i][BASE][UP][0] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 0, colorOffsetY + 0, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_IMAGES[i][BASE][UP][1] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 1, colorOffsetY + 0, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_HITBOX_OFFSETS[BASE][UP][0] = 4;
            TANK_HITBOX_OFFSETS[BASE][UP][1] = 8;
    
            TANK_IMAGES[i][BASE][LEFT][0] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 2, colorOffsetY + 0, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_IMAGES[i][BASE][LEFT][1] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 3 + 4, colorOffsetY + 0, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_HITBOX_OFFSETS[BASE][LEFT][0] = 8;
            TANK_HITBOX_OFFSETS[BASE][LEFT][1] = 4;
    
            TANK_IMAGES[i][BASE][DOWN][0] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 4 + 4, colorOffsetY + 0, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_IMAGES[i][BASE][DOWN][1] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 5 + 4, colorOffsetY + 0, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_HITBOX_OFFSETS[BASE][DOWN][0] = 4;
            TANK_HITBOX_OFFSETS[BASE][DOWN][1] = 4;
    
            TANK_IMAGES[i][BASE][RIGHT][0] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 6 + 4, colorOffsetY + 0, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_IMAGES[i][BASE][RIGHT][1] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 7 + 8, colorOffsetY + 0, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_HITBOX_OFFSETS[BASE][RIGHT][0] = 4;
            TANK_HITBOX_OFFSETS[BASE][RIGHT][1] = 4;
    
    
    
            TANK_IMAGES[i][FAST][UP][0] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 0, colorOffsetY + 328, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_IMAGES[i][FAST][UP][1] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 1, colorOffsetY + 328, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_HITBOX_OFFSETS[FAST][UP][0] = 0;
            TANK_HITBOX_OFFSETS[FAST][UP][1] = 0;
    
            TANK_IMAGES[i][FAST][LEFT][0] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 2, colorOffsetY + 328, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_IMAGES[i][FAST][LEFT][1] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 3 + 4, colorOffsetY + 328, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_HITBOX_OFFSETS[FAST][LEFT][0] = 0;
            TANK_HITBOX_OFFSETS[FAST][LEFT][1] = 4;
    
            TANK_IMAGES[i][FAST][DOWN][0] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 4 + 4, colorOffsetY + 328, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_IMAGES[i][FAST][DOWN][1] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 5 + 4, colorOffsetY + 328, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_HITBOX_OFFSETS[FAST][DOWN][0] = 0;
            TANK_HITBOX_OFFSETS[FAST][DOWN][1] = 4;
    
            TANK_IMAGES[i][FAST][RIGHT][0] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 6 + 4, colorOffsetY + 328, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_IMAGES[i][FAST][RIGHT][1] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 7 + 8, colorOffsetY + 328, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_HITBOX_OFFSETS[FAST][RIGHT][0] = 4;
            TANK_HITBOX_OFFSETS[FAST][RIGHT][1] = 0;
    
    
    
            TANK_IMAGES[i][POWER][UP][0] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 0, colorOffsetY + 132, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_IMAGES[i][POWER][UP][1] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 1, colorOffsetY + 132, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_HITBOX_OFFSETS[POWER][UP][0] = 0;
            TANK_HITBOX_OFFSETS[POWER][UP][1] = 0;
    
            TANK_IMAGES[i][POWER][LEFT][0] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 2, colorOffsetY + 132, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_IMAGES[i][POWER][LEFT][1] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 3 + 4, colorOffsetY + 132, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_HITBOX_OFFSETS[POWER][LEFT][0] = 0;
            TANK_HITBOX_OFFSETS[POWER][LEFT][1] = 0;
    
            TANK_IMAGES[i][POWER][DOWN][0] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 4 + 4, colorOffsetY + 132, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_IMAGES[i][POWER][DOWN][1] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 5 + 4, colorOffsetY + 132, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_HITBOX_OFFSETS[POWER][DOWN][0] = 0;
            TANK_HITBOX_OFFSETS[POWER][DOWN][1] = 4;
    
            TANK_IMAGES[i][POWER][RIGHT][0] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 6 + 4, colorOffsetY + 132, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_IMAGES[i][POWER][RIGHT][1] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 7 + 8, colorOffsetY + 132, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_HITBOX_OFFSETS[POWER][RIGHT][0] = 4;
            TANK_HITBOX_OFFSETS[POWER][RIGHT][1] = 0;
    
    
    
            TANK_IMAGES[i][HEAVY][UP][0] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 0, colorOffsetY + 458, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_IMAGES[i][HEAVY][UP][1] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 1, colorOffsetY + 458, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_HITBOX_OFFSETS[HEAVY][UP][0] = 0;
            TANK_HITBOX_OFFSETS[HEAVY][UP][1] = 2;
    
            TANK_IMAGES[i][HEAVY][LEFT][0] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 2, colorOffsetY + 458, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_IMAGES[i][HEAVY][LEFT][1] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 3 + 4, colorOffsetY + 458, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_HITBOX_OFFSETS[HEAVY][LEFT][0] = 0;
            TANK_HITBOX_OFFSETS[HEAVY][LEFT][1] = 2;
    
            TANK_IMAGES[i][HEAVY][DOWN][0] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 4 + 4, colorOffsetY + 458, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_IMAGES[i][HEAVY][DOWN][1] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 5 + 4, colorOffsetY + 458, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_HITBOX_OFFSETS[HEAVY][DOWN][0] = 0;
            TANK_HITBOX_OFFSETS[HEAVY][DOWN][1] = 2;
    
            TANK_IMAGES[i][HEAVY][RIGHT][0] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 6 + 4, colorOffsetY + 458, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
            TANK_IMAGES[i][HEAVY][RIGHT][1] = img.getSubimage(colorOffsetX + TILES_DEFAULT_SIZE * 7 + 8, colorOffsetY + 458, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
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

    public static void LoadExplosionImages() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.MAIN_SPRITE);

        TEMP_OBJECTS_IMAGES = new BufferedImage[4][3];

        for (int i = 0; i < TemporaryObjectType.TO_SMALL_EXPLOSION.getSpritesNumber(); i++)
            TEMP_OBJECTS_IMAGES[SMALL_EXPLOSION][i] = img.getSubimage(
                    1052 + TemporaryObjectType.TO_SMALL_EXPLOSION.getWidth() * i ,
                    512,
                    TemporaryObjectType.TO_SMALL_EXPLOSION.getWidth(),
                    TemporaryObjectType.TO_SMALL_EXPLOSION.getHeight());

        for (int i = 0; i < TemporaryObjectType.TO_BIG_EXPLOSION.getSpritesNumber(); i++)
            TEMP_OBJECTS_IMAGES[BIG_EXPLOSION][i] = img.getSubimage(
                    1244 + TemporaryObjectType.TO_BIG_EXPLOSION.getWidth() * i ,
                    512,
                    TemporaryObjectType.TO_BIG_EXPLOSION.getWidth(),
                    TemporaryObjectType.TO_BIG_EXPLOSION.getHeight());

    }

}
