package objects;

import gamestates.Playing;
import levels.LevelBlock;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.DEBUG_MODE;
import static utils.Constants.DirConstants.*;
import static utils.Constants.ExplosionConstants.*;
import static utils.Constants.ProjectileConstants.*;
import static utils.LoadSave.PROJECTILE_IMAGES;

public class Projectile {

    Playing playing;
    private Rectangle2D.Float hitbox;
    private Rectangle2D.Float explosionHitbox;
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
                        // Check if the explosion should destroy any other neighbor blocks
                        checkExplosionIntersect();
                        return;
                    }
                    // Otherwise it keeps moving (it's grass, river or ice)
                }
        }
    }

    /**
     * Check if exposition affects any other blocks
     */
    private void checkExplosionIntersect() {
        float explX = hitbox.x;
        float explY = hitbox.y;
        float explW = hitbox.width;
        float explH = hitbox.height;

        // Exposition hitbox must be bigger in width then projectile
        // If projectile's direction up or down then increase width
        if (dir == UP || dir == DOWN) {
            explX -= 32 * Game.SCALE;
            explW = 70 * Game.SCALE;
        }

        // If projectile's direction left or right then increase height
        if (dir == LEFT || dir == RIGHT) {
            explY -= 32 * Game.SCALE;
            explH = 70 * Game.SCALE;
        }

        explosionHitbox = new Rectangle2D.Float(explX, explY, explW, explH);

        // Destroy all blocks that were affected by the explosion hitbox
        for (LevelBlock levelBlock : playing.getLevelManager().getLevelBlocks())
            if (levelBlock.isActive())
                if (explosionHitbox.intersects(levelBlock.getHitbox()))
                    levelBlock.hitByProjectile();

    }

    /**
     * Set projectile inactive and create explosion
     */
    private void destroyProjectile() {
        active = false;
        playing.getObjectManager().createExplosion((int)(hitbox.x - EXPLOSION_WIDTH / 2), (int)(hitbox.y - EXPLOSION_HEIGHT / 2));
    }

    public void draw(Graphics g) {
        g.drawImage(PROJECTILE_IMAGES.get(dir), (int)hitbox.x, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height, null);

        // To see the explosion hitbox you need to draw inactive projectiles
        if (DEBUG_MODE)
            if (explosionHitbox != null)
                g.drawRect((int)explosionHitbox.x, (int)explosionHitbox.y, (int)explosionHitbox.width, (int)explosionHitbox.height);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
