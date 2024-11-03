package objects;

import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.DirConstants.*;
import static utils.Constants.ProjectileConstants.*;
import static utils.LoadSave.PROJECTILE_IMAGES;

public class Projectile {

    private Rectangle2D.Float hitbox;
    private int dir;
    private boolean active = true;

    public Projectile(int x, int y, int dir) {
        if (dir == UP || dir == DOWN)
            hitbox = new Rectangle2D.Float(x, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
        else if (dir == LEFT || dir == RIGHT)
            hitbox = new Rectangle2D.Float(x, y, PROJECTILE_HEIGHT, PROJECTILE_WIDTH);

        this.dir = dir;

    }

    public void update() {

        switch (dir) {
            case UP -> hitbox.y -= PROJECTILE_SPEED;
            case DOWN -> hitbox.y += PROJECTILE_SPEED;
            case LEFT -> hitbox.x -= PROJECTILE_SPEED;
            case RIGHT -> hitbox.x += PROJECTILE_SPEED;
        }

    }

    public void draw(Graphics g) {
        g.drawImage(PROJECTILE_IMAGES.get(dir), (int)hitbox.x, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height, null);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
