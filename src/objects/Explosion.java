package objects;

import gamestates.Playing;

import java.awt.*;

import static utils.Constants.ANI_SPEED;
import static utils.Constants.ExplosionConstants.*;
import static utils.LoadSave.EXPLOSION_IMAGES;

public class Explosion {

    Playing playing;
    private float x, y;
    private int width, height;
    private boolean active = true;
    private int aniTick, aniIndex;

    public Explosion(float x, float y, Playing playing) {
        this.playing = playing;
        this.x = x;
        this.y = y;
        this.width = EXPLOSION_WIDTH;
        this.height = EXPLOSION_HEIGHT;
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
            if (aniIndex == EXPLOSION_IMAGES.length)
                active = false;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(EXPLOSION_IMAGES[aniIndex], (int)x, (int)y, width, height, null);
    }

    public boolean isActive() {
        return active;
    }
}
