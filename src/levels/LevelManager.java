package levels;

import main.Game;

import java.awt.*;

import static main.Game.TILES_SIZE;
import static utils.Constants.DEBUG_MODE;

public class LevelManager {

    private Game game;

    public LevelManager(Game game) {
        this.game = game;
    }


    /**
     * Draw current level using building blocks from levelSprite and based on data from levelOne
     */
    public void draw(Graphics g) {

//        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
//            for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
//                if (DEBUG_MODE)
//                    g.drawRect(i*TILES_SIZE, j*TILES_SIZE, TILES_SIZE, TILES_SIZE);
//            }

//        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
//            for (int i = 0; i < levels.get(lvlIndex).getLvlData()[0].length; i++) {
//                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
//                g.drawImage(levelSprite[index], i*TILES_SIZE - xLvlOffset, j*TILES_SIZE, TILES_SIZE, TILES_SIZE, null);
//                if (DEBUG_MODE)
//                    g.drawRect(i*TILES_SIZE - xLvlOffset, j*TILES_SIZE, TILES_SIZE, TILES_SIZE);
//            }


    }
}
