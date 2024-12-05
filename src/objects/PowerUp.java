package objects;

import entities.Player;
import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.PowerUpConstants.*;
import static utils.LoadSave.POWER_UP_IMAGES;

public class PowerUp {

    Playing playing;
    private int type;
    private int x, y;
    private int width, height;
    private boolean active = true;
    private long creationTimeMS;


    public PowerUp(Playing playing, int type, int x, int y) {
        this.playing = playing;
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = 64;
        this.height = 64;

        creationTimeMS = System.currentTimeMillis();
    }

    public void update() {

        checkIntersectWithPlayer();

        long currentTime = System.currentTimeMillis();

        if (currentTime - creationTimeMS > POWER_UP_ACTIVE_TIME_MS)
            active = false;

    }

    private void checkIntersectWithPlayer() {

        Rectangle2D.Float powerUpHitbox = new Rectangle2D.Float(x, y, width, height);

        Player player = playing.getPlayer();

        if (player.isActive())
            if (powerUpHitbox.intersects(player.getHitbox())) {
                player.applyPowerUp(type);
                active = false;
            }



    }

    public void draw(Graphics g) {
        g.drawImage(POWER_UP_IMAGES[type], x, y, (int)(width * Game.SCALE), (int)(height * Game.SCALE), null);
    }

    public boolean isActive() {
        return active;
    }
}
