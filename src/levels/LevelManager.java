package levels;

import main.Game;

import java.awt.*;
import java.util.ArrayList;

import static utils.Constants.LevelConstants.*;
import static utils.LoadSave.LoadBlockImages;

public class LevelManager {

    private Game game;

    private ArrayList<Level> levels;
    private Level currentLevel;

    private int currentLevelIndex;

    public LevelManager(Game game) {
        this.game = game;
        loadLevelSprites();

        levels = new ArrayList<>();
        // Load levels from files to the levels list
        loadAllLevels();
    }

    /**
     * Bricks, grass, etc
     */
    private void loadLevelSprites() {
        LoadBlockImages();
    }

    /**
     * Load levels from files to array list
     */
    private void loadAllLevels() {
        for (int i = 0; i < LEVELS_COUNT; i++)
            levels.add(new Level(i));
    }

    /**
     * Start first level
     */
    public void loadFirstLevel() {
        currentLevelIndex = 0;
        currentLevel = new Level(currentLevelIndex);
    }

    /**
     * Increment level index and then get source level from levels (as it was loaded from the file) and clone to current level
     */
    public void loadNextLevel() {
        currentLevelIndex++;
        currentLevel.cloneLevel(levels.get(currentLevelIndex));
    }

    /**
     * Get source level from levels (as it was loaded from the file) and clone to current level
     */
    public void reloadCurrentLevel() {
        currentLevel.cloneLevel(levels.get(currentLevelIndex));
    }

    /**
     * Draw current level using building blocks from levelBlocks
     * Everything without grass
     */
    public void draw(Graphics g) {
        currentLevel.draw(g, DRAW_LEVEL_WO_GRASS);
    }

    /**
     * Draw current level using building blocks from levelBlocks
     * The blocks need to be drawn on top of the player
     */
    public void drawAfterPlayer(Graphics g) {
        currentLevel.draw(g, DRAW_GRASS);
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}
