package objects;

import entities.Enemy;
import entities.Player;
import entities.Tank;
import gamestates.Gamestate;
import gamestates.Playing;
import levels.LevelBlock;
import levels.LevelBlockType;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.DEBUG_MODE;
import static utils.Constants.DirConstants.*;
import static utils.Constants.ProjectileConstants.*;
import static utils.LoadSave.PROJECTILE_IMAGES;

public class Projectile {

    private Playing playing;
    private Tank tank;
    private Rectangle2D.Float hitbox;
    private Rectangle2D.Float explosionHitbox;
    private float speed;
    private int dir;
    private boolean active = true;

    public Projectile(int x, int y, int dir, float speed, Tank tank, Playing playing) {
        if (dir == UP || dir == DOWN)
            hitbox = new Rectangle2D.Float(x, y, PROJECTILE_WIDTH, PROJECTILE_HEIGHT);
        else if (dir == LEFT || dir == RIGHT)
            hitbox = new Rectangle2D.Float(x, y, PROJECTILE_HEIGHT, PROJECTILE_WIDTH);

        this.dir = dir;
        this.speed = speed;

        this.tank = tank;
        this.playing = playing;

    }

    public void update() {

        switch (dir) {
            case UP -> hitbox.y -= speed;
            case DOWN -> hitbox.y += speed;
            case LEFT -> hitbox.x -= speed;
            case RIGHT -> hitbox.x += speed;
        }

        checkLevelEdge();
        checkIntersectWithLevelBlock();
        checkIntersectWithTanks();
        checkIntersectWithAnotherProjectile();
    }

    private void checkLevelEdge() {
        if (hitbox.x < 0 || hitbox.y < 0
                || hitbox.x + hitbox.width > Game.GAME_WIDTH
                || hitbox.y + hitbox.height > Game.GAME_HEIGHT
        ) {
            // Destroy current projectile with explosion
            destroyProjectile(TemporaryObjectType.TO_SMALL_EXPLOSION);
        }
    }

    private void checkIntersectWithAnotherProjectile() {
        for (Projectile p : playing.getObjectManager().getProjectiles())
            // It's not current projectile
            if (p != this)
                if (p.isActive())
                    if (hitbox.intersects(p.getHitbox())) {
                        // Destroy current projectile with explosion
                        destroyProjectile(TemporaryObjectType.TO_SMALL_EXPLOSION);
                        // Another projectile just set inactive (we don't need two explosions)
                        p.setActive(false);
                    }
    }

    private void checkIntersectWithLevelBlock() {
        for (LevelBlock levelBlock : playing.getLevelManager().getCurrentLevel().getLevelBlocks()) {
            if (levelBlock.isActive())
                if (hitbox.intersects(levelBlock.getHitbox())) {
                    if (levelBlock.hitByProjectile()) {
                        // If the projectile should be destroyed by the level block (it's a brick or metal)
                        destroyProjectile(TemporaryObjectType.TO_SMALL_EXPLOSION);
                        // Check if the explosion should destroy any other neighbor blocks
                        checkExplosionIntersect();
                        // If base was destroyed then game over
                        if (levelBlock.getType() == LevelBlockType.BASE) {
                            playing.setGameOver(true);
                            Gamestate.state = Gamestate.GAMEOVER;
                        }
                        return;
                    }
                    // Otherwise it keeps moving (it's grass, river or ice)
                }
        }
    }

    private void checkIntersectWithTanks() {
        if (tank instanceof Player) {

            // check if player's projectile hit one of the enemies
            for (Enemy enemy : playing.getEnemyManager().getEnemies())
                if (enemy.isActive())
                    if (checkHitTank(enemy))
                        return;


        } else if (tank instanceof Enemy) {

            // check if enemy's projectile hit player
            if (playing.getPlayer().isActive())
                checkHitTank(playing.getPlayer());

        }
    }


    /**
     * Check if the projectile hit the tank and if it does then destroy the projectile
     * @param damagedTank tank to check with the projectile
     * @return true if the projectile hit the tank
     */
    private boolean checkHitTank(Tank damagedTank) {
        if (hitbox.intersects(damagedTank.getHitbox())) {

            if (damagedTank.hasShield())
                // If the tank has shield then just deactivate the projectile w/o any explosion
                active = false;
            else
                // Damage the tank
                if (damagedTank.hitByProjectile(1)) {
                    // If the tank was killed then destroy the projectile with big explosion
                    destroyProjectile(TemporaryObjectType.TO_BIG_EXPLOSION);
                    playing.getEnemyManager().decreasedEnemiesTiKillCount();

                    // Add points to player if it was player's projectile
                    if (tank instanceof Player)
                        ((Player)tank).addPoints(damagedTank.getPoints());
                } else {
                    // If the tank was just injured then destroy the projectile with small explosion
                    destroyProjectile(TemporaryObjectType.TO_SMALL_EXPLOSION);
                }

            return true;
        }
        return false;
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
            explW = 63 * Game.SCALE;
        }

        // If projectile's direction left or right then increase height
        if (dir == LEFT || dir == RIGHT) {
            explY -= 32 * Game.SCALE;
            explH = 63 * Game.SCALE;
        }

        explosionHitbox = new Rectangle2D.Float(explX, explY, explW, explH);

        // Destroy all blocks that were affected by the explosion hitbox
        for (LevelBlock levelBlock : playing.getLevelManager().getCurrentLevel().getLevelBlocks())
            if (levelBlock.isActive())
                if (explosionHitbox.intersects(levelBlock.getHitbox()))
                    levelBlock.hitByProjectile();

    }

    /**
     * Set projectile inactive and create explosion
     */
    private void destroyProjectile(TemporaryObjectType explosionType) {
        active = false;
        playing.getObjectManager().createExplosion(
                (int)(hitbox.x - explosionType.getWidth() / 2),
                (int)(hitbox.y - explosionType.getHeight() / 2),
                explosionType);
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

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
}
