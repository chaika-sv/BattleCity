package levels;

import main.Game;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import static utils.Constants.LevelConstants.*;

public class Level implements Serializable {

    private int index;
    private ArrayList<LevelBlock> levelBlocks;

    public Level(int index) {

        this.index = index;

        levelBlocks = new ArrayList<>();

        switch (index) {
            case 1 -> {

                String fileName = "1";

                try {
                    FileInputStream fis = new FileInputStream(LEVEL_DIR + "/" + fileName);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    Level loadedLevel = (Level) ois.readObject();
                    copyLevel(loadedLevel);
                    ois.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                /*
                addLevelBlock(LevelBlockType.BRICK_BIG, 300, 300);
                addLevelBlock(LevelBlockType.BRICK_BIG, 500, 500);
                addLevelBlock(LevelBlockType.RIVER3_BIG, 600, 600);
                addLevelBlock(LevelBlockType.METAL_BIG, 700, 600);
                addLevelBlock(LevelBlockType.GRASS_BIG, 600, 300);
                addLevelBlock(LevelBlockType.ICE_BIG, 600, 200);
                addLevelBlock(LevelBlockType.ICE_BIG, 664, 200);
                 */
            }
            case 2 -> levelBlocks.add(new LevelBlock(LevelBlockType.METAL_BIG, 100, 100));
            case 3 -> levelBlocks.add(new LevelBlock(LevelBlockType.BRICK_HALF_SMALL, 500, 500));
        }


    }

    public void copyLevel(Level levelToCopy) {
        this.index = levelToCopy.index;
        this.levelBlocks = levelToCopy.levelBlocks;
    }

    public void addLevelBlock(LevelBlockType type, int x, int y) {
        if (type == LevelBlockType.BRICK_BIG) {

            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++)

                    if ((i + j) % 2 == 0)
                        levelBlocks.add(
                                new LevelBlock(LevelBlockType.BRICK_LITTLE1,
                                (int)(x + i * LevelBlockType.BRICK_LITTLE1.getWidth() * Game.SCALE),
                                (int)(y + j * LevelBlockType.BRICK_LITTLE1.getHeight() * Game.SCALE))
                        );
                    else
                        levelBlocks.add(
                                new LevelBlock(LevelBlockType.BRICK_LITTLE2,
                                (int)(x + i * LevelBlockType.BRICK_LITTLE2.getWidth() * Game.SCALE),
                                (int)(y + j * LevelBlockType.BRICK_LITTLE2.getHeight() * Game.SCALE))
                        );

        } else {
            levelBlocks.add(new LevelBlock(type, x, y));
        }
    }

    public void draw(Graphics g, int drawOption) {
        for (LevelBlock block : levelBlocks)
            if (block.isActive())
                if ((drawOption == DRAW_ALL_LEVEL)
                        || ((drawOption == DRAW_LEVEL_WO_GRASS) && block.getType() != LevelBlockType.GRASS )
                        || ((drawOption == DRAW_GRASS) && block.getType() == LevelBlockType.GRASS )
                )
                    block.draw(g);
    }

    public ArrayList<LevelBlock> getLevelBlocks() {
        return levelBlocks;
    }
}
