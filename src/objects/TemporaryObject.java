package objects;

import gamestates.Playing;

import java.awt.*;

import static utils.Constants.ANI_SPEED;
import static utils.LoadSave.TEMP_OBJECTS_IMAGES;

public class TemporaryObject {

    Playing playing;
    private TemporaryObjectType type;
    private float x, y;
    private int width, height;
    private boolean active = true;
    private int aniTick, aniIndex;
    private int spritesNumber;

    public TemporaryObject(float x, float y, TemporaryObjectType type, Playing playing) {
        this.playing = playing;
        this.x = x;
        this.y = y;
        this.type = type;
        this.width = type.getWidth();
        this.height = type.getHeight();
        this.spritesNumber = type.getSpritesNumber();
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
            if (aniIndex == spritesNumber)
                active = false;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(TEMP_OBJECTS_IMAGES[type.getId()][aniIndex], (int)x, (int)y, width, height, null);
    }

    public boolean isActive() {
        return active;
    }
}
