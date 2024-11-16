package levels;

import main.Game;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import static utils.Constants.LevelConstants.*;
import static utils.LoadSave.LoadLevelFromFile;

public class Level implements Serializable {

    private int index;
    private ArrayList<LevelBlock> levelBlocks;

    public Level(int index) {

        this.index = index;

        levelBlocks = new ArrayList<>();

        if (index > 0)
            LoadLevelFromFile(String.valueOf(index), this);

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

    public void copyLevel(Level levelToCopy) {
        this.index = levelToCopy.index;
        this.levelBlocks = levelToCopy.levelBlocks;
    }

    public void addLevelBlock(LevelBlockType type, int x, int y) {
        if (type == LevelBlockType.BRICK_BIG || type == LevelBlockType.BRICK_SMALL || type == LevelBlockType.BRICK_HALF
                 || type == LevelBlockType.BRICK_LITTLE1 || type == LevelBlockType.BRICK_LITTLE2
        ) {

            int brickLenX = 0;
            int brickLenY = 0;

            switch (type) {
                case BRICK_BIG -> {
                    brickLenX = 4;
                    brickLenY = 4;
                }
                case BRICK_SMALL -> {
                    brickLenX = 2;
                    brickLenY = 2;
                }
                case BRICK_HALF -> {
                    brickLenX = 4;
                    brickLenY = 2;
                }
                case BRICK_LITTLE1, BRICK_LITTLE2 -> {
                    brickLenX = 1;
                    brickLenY = 1;
                }
            }

            for (int i = 0; i < brickLenX; i++)
                for (int j = 0; j < brickLenY; j++)

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
