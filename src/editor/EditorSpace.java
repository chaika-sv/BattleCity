package editor;

import gamestates.Editing;
import levels.LevelBlock;
import levels.LevelBlockType;

import java.awt.*;
import java.util.ArrayList;

import static main.Game.*;
import static utils.LoadSave.LoadBlockImages;

public class EditorSpace {

    private Editing editing;
    private ArrayList<LevelBlock> sampleLevelBlocks;


    public EditorSpace(Editing editing) {
        this.editing = editing;

        LoadBlockImages();

        sampleLevelBlocks = new ArrayList<>();
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.BRICK_BIG, GAME_WIDTH + 10, 10));
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.METAL_HALF, GAME_WIDTH + 10, 100));
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(GAME_WIDTH + 1, 0, GAME_WIDTH + 2 * TILES_SIZE - 1, GAME_HEIGHT);

        for (LevelBlock block : sampleLevelBlocks)
            if (block.isActive())
                block.draw(g);
    }

    public ArrayList<LevelBlock> getSampleLevelBlocks() {
        return sampleLevelBlocks;
    }
}
