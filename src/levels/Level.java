package levels;

import java.util.ArrayList;

public class Level {

    private ArrayList<LevelBlock> levelBlocks;

    public Level(int v) {

        levelBlocks = new ArrayList<>();

        switch (v) {
            case 1 -> {
                levelBlocks.add(new LevelBlock(LevelBlockType.BRICK_BIG, 500, 500));
                levelBlocks.add(new LevelBlock(LevelBlockType.RIVER3_SMALL, 600, 600));
            }
            case 2 -> levelBlocks.add(new LevelBlock(LevelBlockType.METAL_BIG, 100, 100));
            case 3 -> levelBlocks.add(new LevelBlock(LevelBlockType.BRICK_HALF_SMALL, 500, 500));
        }


    }

    public ArrayList<LevelBlock> getLevelBlocks() {
        return levelBlocks;
    }
}
