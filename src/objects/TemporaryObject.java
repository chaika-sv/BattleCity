package objects;

import gamestates.Playing;

import java.awt.*;

import static utils.Constants.ANI_SPEED;
import static utils.LoadSaveImages.TEMP_OBJECTS_IMAGES;

public class TemporaryObject {

    Playing playing;
    private TemporaryObjectType type;
    private int x, y;
    private int width, height;
    private boolean active = true;
    private int aniTick, aniIndex;
    private int spritesNumber;
    private int repeatingTimes;

    public TemporaryObject(int x, int y, TemporaryObjectType type, Playing playing) {
        this.playing = playing;
        this.x = x;
        this.y = y;
        this.type = type;
        this.width = type.getWidth();
        this.height = type.getHeight();
        this.spritesNumber = type.getSpritesNumber();
        this.repeatingTimes = type.getRepeatingTimes();
    }

    public void update() {
        updateAnimationTick();
    }

    /**
     * Increment animTick and when we reach animSpeed then increment animIndex
     * We use animIndex to display next animation sprite
     */
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex == spritesNumber) {
                repeatingTimes--;
                if (repeatingTimes == 0) {
                    active = false;
                    if (type == TemporaryObjectType.TO_SPAWN)
                        // Enemy type will be determine in enemy manager
                        playing.getEnemyManager().spawnNewEnemy(x, y);
                }
                else
                    aniIndex = 0;
            }
        }
    }

    public void draw(Graphics g) {
        g.drawImage(TEMP_OBJECTS_IMAGES[type.getId()][aniIndex], x, y, width, height, null);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public TemporaryObjectType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
