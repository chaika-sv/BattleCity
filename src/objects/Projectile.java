package objects;

import gamestates.Playing;
import levels.LevelBlock;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.DirConstants.*;
import static utils.Constants.ExplosionConstants.*;
import static utils.Constants.ProjectileConstants.*;
import static utils.LoadSave.PROJECTILE_IMAGES;

public class Projectile {

    Playing playing;
    private Rectangle2D.Float hitbox;
    private int dir;
    private boolean active = true;

    public Projectile(int x, int y, int dir, Playing playing) {
        if (dir == UP || dir == DOWN)
            hitbox = new Rectangle2D.Float(x, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
        else if (dir == LEFT || dir == RIGHT)
            hitbox = new Rectangle2D.Float(x, y, PROJECTILE_HEIGHT, PROJECTILE_WIDTH);

        this.dir = dir;

        this.playing = playing;

    }

    public void update() {

        switch (dir) {
            case UP -> hitbox.y -= PROJECTILE_SPEED;
            case DOWN -> hitbox.y += PROJECTILE_SPEED;
            case LEFT -> hitbox.x -= PROJECTILE_SPEED;
            case RIGHT -> hitbox.x += PROJECTILE_SPEED;
        }

        checkIntersectWithLevelBlock();

    }

    private void checkIntersectWithLevelBlock() {
        for (LevelBlock levelBlock : playing.getLevelManager().getLevelBlocks()) {
            if (levelBlock.isActive())
                if (this.hitbox.intersects(levelBlock.getHitbox())) {
                    if (levelBlock.hitByProjectile()) {
                        // If the projectile should be destroyed by the level block (it's a brick or metal)
                        destroyProjectile();
                        return;
                    }
                    // Otherwise it keeps moving (it's grass, river or ice)
                }
        }
    }

    private void destroyProjectile() {
        active = false;
        playing.getObjectManager().createExplosion((int)(hitbox.x - EXPLOSION_WIDTH / 2), (int)(hitbox.y - EXPLOSION_HEIGHT / 2));
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
