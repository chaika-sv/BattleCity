package levels;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static utils.LoadSave.LoadBlockImages;

public class LevelManager {

    private Game game;

    private ArrayList<Level> levels;

    private int currentLevelIndex;

    public LevelManager(Game game) {
        this.game = game;
        loadLevelSprites();

        levels = new ArrayList<>();
        loadAllLevels();

        currentLevelIndex = 0;
    }

    private void loadLevelSprites() {
        LoadBlockImages();
    }

    private void loadAllLevels() {
        levels.add(new Level());
    }


    /**
     * Draw current level using building blocks from levelSprite and based on data from levelOne
     */
    public void draw(Graphics g) {
        for (LevelBlock block : levels.get(currentLevelIndex).getLevelBlocks()) {
            block.draw(g);
        }
    }

}
