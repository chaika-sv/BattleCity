package utils;

import levels.LevelBlockType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static main.Game.TILES_DEFAULT_SIZE;
import static utils.Constants.DirConstants.*;
import static utils.Constants.ProjectileConstants.*;
import static utils.Constants.ExplosionConstants.*;
import static utils.Constants.TankTypeConstants.*;

public class LoadSave {

    public static final String MAIN_SPRITE = "sprite.png";

    public static Map<LevelBlockType, BufferedImage> BLOCK_IMAGES;
    public static Map<Integer, BufferedImage> PROJECTILE_IMAGES;
    public static BufferedImage[] EXPLOSION_IMAGES;
    public static BufferedImage[][][] TANK_IMAGES;      // dim1: tank type, dim2: direction, dim3: animation
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

    public static void LoadTankImages() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.MAIN_SPRITE);

        // 1 - tank types (4 types)
        // 2 - direction (4 directions)
        // 3 - animation (2 ani indexes)
        TANK_IMAGES = new BufferedImage[4][4][2];

        // 1 - tank types (4 types)
        // 2 - direction (4 directions)
        // 3 - x, y
        TANK_HITBOX_OFFSETS = new int[4][4][2];

        TANK_IMAGES[BASE][UP][0] = img.getSubimage(TILES_DEFAULT_SIZE * 0, 0, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_IMAGES[BASE][UP][1] = img.getSubimage(TILES_DEFAULT_SIZE * 1, 0, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_HITBOX_OFFSETS[BASE][UP][0] = 4;
        TANK_HITBOX_OFFSETS[BASE][UP][1] = 8;

        TANK_IMAGES[BASE][LEFT][0] = img.getSubimage(TILES_DEFAULT_SIZE * 2, 0, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_IMAGES[BASE][LEFT][1] = img.getSubimage(TILES_DEFAULT_SIZE * 3 + 4, 0, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_HITBOX_OFFSETS[BASE][LEFT][0] = 8;
        TANK_HITBOX_OFFSETS[BASE][LEFT][1] = 4;

        TANK_IMAGES[BASE][DOWN][0] = img.getSubimage(TILES_DEFAULT_SIZE * 4 + 4, 0, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_IMAGES[BASE][DOWN][1] = img.getSubimage(TILES_DEFAULT_SIZE * 5 + 4, 0, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_HITBOX_OFFSETS[BASE][DOWN][0] = 4;
        TANK_HITBOX_OFFSETS[BASE][DOWN][1] = 4;

        TANK_IMAGES[BASE][RIGHT][0] = img.getSubimage(TILES_DEFAULT_SIZE * 6 + 4, 0, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_IMAGES[BASE][RIGHT][1] = img.getSubimage(TILES_DEFAULT_SIZE * 7 + 8, 0, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_HITBOX_OFFSETS[BASE][RIGHT][0] = 4;
        TANK_HITBOX_OFFSETS[BASE][RIGHT][1] = 4;



        TANK_IMAGES[FAST][UP][0] = img.getSubimage(TILES_DEFAULT_SIZE * 0, 328, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_IMAGES[FAST][UP][1] = img.getSubimage(TILES_DEFAULT_SIZE * 1, 328, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_HITBOX_OFFSETS[FAST][UP][0] = 0;
        TANK_HITBOX_OFFSETS[FAST][UP][1] = 0;

        TANK_IMAGES[FAST][LEFT][0] = img.getSubimage(TILES_DEFAULT_SIZE * 2, 328, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_IMAGES[FAST][LEFT][1] = img.getSubimage(TILES_DEFAULT_SIZE * 3 + 4, 328, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_HITBOX_OFFSETS[FAST][LEFT][0] = 0;
        TANK_HITBOX_OFFSETS[FAST][LEFT][1] = 4;

        TANK_IMAGES[FAST][DOWN][0] = img.getSubimage(TILES_DEFAULT_SIZE * 4 + 4, 328, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_IMAGES[FAST][DOWN][1] = img.getSubimage(TILES_DEFAULT_SIZE * 5 + 4, 328, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_HITBOX_OFFSETS[FAST][DOWN][0] = 0;
        TANK_HITBOX_OFFSETS[FAST][DOWN][1] = 4;

        TANK_IMAGES[FAST][RIGHT][0] = img.getSubimage(TILES_DEFAULT_SIZE * 6 + 4, 328, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_IMAGES[FAST][RIGHT][1] = img.getSubimage(TILES_DEFAULT_SIZE * 7 + 8, 328, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_HITBOX_OFFSETS[FAST][RIGHT][0] = 4;
        TANK_HITBOX_OFFSETS[FAST][RIGHT][1] = 0;



        TANK_IMAGES[POWER][UP][0] = img.getSubimage(TILES_DEFAULT_SIZE * 0, 132, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_IMAGES[POWER][UP][1] = img.getSubimage(TILES_DEFAULT_SIZE * 1, 132, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_HITBOX_OFFSETS[POWER][UP][0] = 0;
        TANK_HITBOX_OFFSETS[POWER][UP][1] = 0;

        TANK_IMAGES[POWER][LEFT][0] = img.getSubimage(TILES_DEFAULT_SIZE * 2, 132, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_IMAGES[POWER][LEFT][1] = img.getSubimage(TILES_DEFAULT_SIZE * 3 + 4, 132, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_HITBOX_OFFSETS[POWER][LEFT][0] = 0;
        TANK_HITBOX_OFFSETS[POWER][LEFT][1] = 0;

        TANK_IMAGES[POWER][DOWN][0] = img.getSubimage(TILES_DEFAULT_SIZE * 4 + 4, 132, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_IMAGES[POWER][DOWN][1] = img.getSubimage(TILES_DEFAULT_SIZE * 5 + 4, 132, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_HITBOX_OFFSETS[POWER][DOWN][0] = 0;
        TANK_HITBOX_OFFSETS[POWER][DOWN][1] = 4;

        TANK_IMAGES[POWER][RIGHT][0] = img.getSubimage(TILES_DEFAULT_SIZE * 6 + 4, 132, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_IMAGES[POWER][RIGHT][1] = img.getSubimage(TILES_DEFAULT_SIZE * 7 + 8, 132, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_HITBOX_OFFSETS[POWER][RIGHT][0] = 4;
        TANK_HITBOX_OFFSETS[POWER][RIGHT][1] = 0;



        TANK_IMAGES[HEAVY][UP][0] = img.getSubimage(TILES_DEFAULT_SIZE * 0, 458, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_IMAGES[HEAVY][UP][1] = img.getSubimage(TILES_DEFAULT_SIZE * 1, 458, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_HITBOX_OFFSETS[HEAVY][UP][0] = 0;
        TANK_HITBOX_OFFSETS[HEAVY][UP][1] = 2;

        TANK_IMAGES[HEAVY][LEFT][0] = img.getSubimage(TILES_DEFAULT_SIZE * 2, 458, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_IMAGES[HEAVY][LEFT][1] = img.getSubimage(TILES_DEFAULT_SIZE * 3 + 4, 458, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_HITBOX_OFFSETS[HEAVY][LEFT][0] = 0;
        TANK_HITBOX_OFFSETS[HEAVY][LEFT][1] = 2;

        TANK_IMAGES[HEAVY][DOWN][0] = img.getSubimage(TILES_DEFAULT_SIZE * 4 + 4, 458, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_IMAGES[HEAVY][DOWN][1] = img.getSubimage(TILES_DEFAULT_SIZE * 5 + 4, 458, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_HITBOX_OFFSETS[HEAVY][DOWN][0] = 0;
        TANK_HITBOX_OFFSETS[HEAVY][DOWN][1] = 2;

        TANK_IMAGES[HEAVY][RIGHT][0] = img.getSubimage(TILES_DEFAULT_SIZE * 6 + 4, 458, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_IMAGES[HEAVY][RIGHT][1] = img.getSubimage(TILES_DEFAULT_SIZE * 7 + 8, 458, TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
        TANK_HITBOX_OFFSETS[HEAVY][RIGHT][0] = 0;
        TANK_HITBOX_OFFSETS[HEAVY][RIGHT][1] = 2;

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

        EXPLOSION_IMAGES = new BufferedImage[3];

        for (int i = 0; i < EXPLOSION_IMAGES.length; i++)
            EXPLOSION_IMAGES[i] = img.getSubimage(1052 + EXPLOSION_DEFAULT_WIDTH * i , 512, EXPLOSION_DEFAULT_WIDTH, EXPLOSION_DEFAULT_HEIGHT);

    }

}
