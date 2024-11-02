package utils;

import levels.LevelBlock;
import main.Game;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static utils.Constants.DEBUG_MODE;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height, ArrayList<LevelBlock> levelBlocks) {

        // New possible hitbox to be checked
        Rectangle2D.Float hitbox = new Rectangle2D.Float(x, y, width, height);

        for (LevelBlock block : levelBlocks)
            if (hitbox.intersects(block.getHitbox()))
                return false;

        return true;
    }

    /**
     * Calculate X distance from player to wall assuming that player is near the wall
     * @param hitbox player's hitbox
     * @param xSpeed player's speed
     * @return offset distance between player and wall
     */
    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        return 0f;

    }
}
