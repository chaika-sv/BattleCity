package levels;

import java.util.ArrayList;

public class Level {

    private ArrayList<LevelBlock> levelBlocks;

    public Level() {

        levelBlocks = new ArrayList<>();
        levelBlocks.add(new LevelBlock(LevelBlockType.BRICK_BIG, 500, 500));
        levelBlocks.add(new LevelBlock(LevelBlockType.RIVER3_SMALL, 600, 600));

    }

    public ArrayList<LevelBlock> getLevelBlocks() {
        return levelBlocks;
    }
}
