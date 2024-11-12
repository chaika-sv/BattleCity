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

        int leftOffset = (int)(10 * SCALE);
        int topOffset = (int)(10 * SCALE);

        int curTop = topOffset;
        int curLeft = GAME_WIDTH + leftOffset;

        sampleLevelBlocks = new ArrayList<>();
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.BRICK_BIG, curLeft, curTop));
        curTop += LevelBlockType.BRICK_BIG.getHeight() + topOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.BRICK_HALF, curLeft, curTop));
        curLeft += LevelBlockType.BRICK_HALF.getWidth() + leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.BRICK_SMALL, curLeft, curTop));
        curTop += LevelBlockType.BRICK_HALF.getHeight() + topOffset;
        curLeft = GAME_WIDTH + leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.BRICK_LITTLE1, curLeft, curTop));
        curLeft += LevelBlockType.BRICK_LITTLE1.getWidth() + leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.BRICK_LITTLE2, curLeft, curTop));

        curTop += LevelBlockType.BRICK_SMALL.getHeight() + topOffset;
        curLeft = GAME_WIDTH + leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.METAL_BIG, curLeft, curTop));
        curTop += LevelBlockType.METAL_BIG.getHeight() + topOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.METAL_HALF, curLeft, curTop));
        curLeft += LevelBlockType.METAL_HALF.getWidth() + leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.METAL_SMALL, curLeft, curTop));

        curLeft = GAME_WIDTH + leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.RIVER1_BIG, curLeft, curTop));
        curLeft += LevelBlockType.RIVER1_BIG.getWidth() + leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.RIVER1_SMALL, curLeft, curTop));
        curTop += LevelBlockType.RIVER1_BIG.getHeight() + topOffset;

        curLeft = GAME_WIDTH + leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.RIVER2_BIG, curLeft, curTop));
        curLeft += LevelBlockType.RIVER2_BIG.getWidth() + leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.RIVER2_SMALL, curLeft, curTop));
        curTop += LevelBlockType.RIVER2_BIG.getHeight() + topOffset;

        curLeft = GAME_WIDTH + leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.RIVER3_BIG, curLeft, curTop));
        curLeft += LevelBlockType.RIVER3_BIG.getWidth() + leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.RIVER3_SMALL, curLeft, curTop));
        curTop += LevelBlockType.RIVER3_BIG.getHeight() + topOffset;

        curLeft = GAME_WIDTH + leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.GRASS_BIG, curLeft, curTop));
        curLeft += LevelBlockType.GRASS_BIG.getWidth() + leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.GRASS_SMALL, curLeft, curTop));
        curTop += LevelBlockType.GRASS_BIG.getHeight() + topOffset;


        curLeft = GAME_WIDTH + leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.ICE_BIG, curLeft, curTop));
        curLeft += LevelBlockType.ICE_BIG.getWidth() + leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.ICE_SMALL, curLeft, curTop));
        curTop += LevelBlockType.ICE_BIG.getHeight() + topOffset;

        curLeft = GAME_WIDTH + leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.BASE_UP, curLeft, curTop));
        curTop += LevelBlockType.BASE_UP.getHeight() + topOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.BASE_DOWN, curLeft, curTop));

        curTop += LevelBlockType.BASE_UP.getHeight() + topOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.ERASE, curLeft, curTop));

        curTop += LevelBlockType.SAVE.getHeight() + topOffset * 6;
        curLeft -= leftOffset;
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.SAVE, curLeft, GAME_HEIGHT - LevelBlockType.SAVE.getHeight() - topOffset));
        curLeft += LevelBlockType.SAVE.getWidth();
        sampleLevelBlocks.add(new LevelBlock(LevelBlockType.OPEN, curLeft, GAME_HEIGHT - LevelBlockType.SAVE.getHeight() - topOffset));
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(GAME_WIDTH + 1, 0, GAME_WIDTH + 2 * TILES_SIZE - 1, GAME_HEIGHT);

        Color lineColor = new Color(10, 10, 10);
        g.setColor(lineColor);
        for (int i = 0; i < GAME_WIDTH / TILES_DEFAULT_SIZE; i++)
            g.drawLine(i * TILES_DEFAULT_SIZE, 0, i * TILES_DEFAULT_SIZE, GAME_HEIGHT);

        for (int i = 0; i < GAME_HEIGHT / TILES_DEFAULT_SIZE; i++)
            g.drawLine(0, i * TILES_DEFAULT_SIZE, GAME_WIDTH, i * TILES_DEFAULT_SIZE);


        for (LevelBlock block : sampleLevelBlocks)
            if (block.isActive()) {
                block.draw(g);
            }
    }

    public ArrayList<LevelBlock> getSampleLevelBlocks() {
        return sampleLevelBlocks;
    }
}
