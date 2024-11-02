package utils;

import levels.LevelBlockType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class LoadSave {

    public static final String MAIN_SPRITE = "sprite.png";

    public static Map<LevelBlockType, BufferedImage> BLOCK_IMAGES;

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
        BLOCK_IMAGES.put(LevelBlockType.BRICK_HALF, img.getSubimage(1308, 0, 64, 32));
        BLOCK_IMAGES.put(LevelBlockType.METAL_HALF, img.getSubimage(1308, 64, 64, 32));

    }

}