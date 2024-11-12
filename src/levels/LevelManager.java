package levels;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static utils.Constants.DEBUG_MODE;
import static utils.Constants.LevelConstants.*;
import static utils.LoadSave.LoadBlockImages;

public class LevelManager {

    private Game game;

    private ArrayList<Level> levels;

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
    }

    /**
     * Draw current level using building blocks from levelBlocks
     * Everything without grass
     */
    public void draw(Graphics g) {
        getCurrentLevel().draw(g, DRAW_LEVEL_WO_GRASS);
    }

    /**
     * Draw current level using building blocks from levelBlocks
     * The blocks need to be drawn on top of the player
     */
    public void drawAfterPlayer(Graphics g) {
        getCurrentLevel().draw(g, DRAW_GRASS);
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }
}
