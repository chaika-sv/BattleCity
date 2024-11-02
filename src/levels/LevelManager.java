package levels;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static utils.Constants.DEBUG_MODE;
import static utils.LoadSave.LoadBlockImages;

public class LevelManager {

    private Game game;

    private ArrayList<Level> levels;

    private ArrayList<LevelBlock> levelBlocks;

    private int currentLevelIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        loadLevelSprites();

        levels = new ArrayList<>();
        loadAllLevels();

        currentLevelIndex = -1;
    }

    private void loadLevelSprites() {
        LoadBlockImages();
    }

    private void loadAllLevels() {
        // todo: load levels from somewhere
        levels.add(new Level(1));
        levels.add(new Level(2));
        levels.add(new Level(3));
    }

    public void loadNextLevel() {
        currentLevelIndex++;
        levelBlocks = levels.get(currentLevelIndex).getLevelBlocks();
    }

    /**
     * Draw current level using building blocks from levelSprite and based on data from levelOne
     */
    public void draw(Graphics g) {
        for (LevelBlock block : levelBlocks) {
            block.draw(g);
            if (DEBUG_MODE)
                block.drawHitbox(g);

        }
    }

    public ArrayList<LevelBlock> getLevelBlocks() {
        return levelBlocks;
    }
}
