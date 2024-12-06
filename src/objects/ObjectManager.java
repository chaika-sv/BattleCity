package objects;

import entities.Tank;
import gamestates.Playing;

import java.awt.*;
import java.util.ArrayList;

import static main.Game.TILES_IN_HEIGHT;
import static main.Game.TILES_SIZE;
import static utils.Constants.PowerUpConstants.*;
import static utils.LoadSave.*;

public class ObjectManager {

    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<TemporaryObject> temporaryObjects = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadObjectSprites();

        powerUps.add(new PowerUp(playing, PU_SHOVEL, 3 * TILES_SIZE, (TILES_IN_HEIGHT - 1) * TILES_SIZE));
    }

    private void loadObjectSprites() {
        LoadProjectileImages();
        LoadTempObjectsImages();
        loadPowerUpImages();
    }

    public void createEnemySpawn(int x, int y) {
        temporaryObjects.add(new TemporaryObject(x, y, TemporaryObjectType.TO_SPAWN, playing));
    }

    public void createShield(Tank tank) {
        TemporaryObject shield = new TemporaryObject((int)tank.getHitbox().getX(), (int)tank.getHitbox().getY(), TemporaryObjectType.TO_SHIELD, playing);
        temporaryObjects.add(shield);
        tank.setShield(shield);
    }

    /**
     * Create projectile
     * @param x projectile spawn x
     * @param y projectile spawn y
     * @param dir projectile direction
     * @param speed projectile speed
     * @param tank tank who shoot the projectile
     */
    public void shootProjectile(int x, int y, int dir, float speed, Tank tank) {
        projectiles.add(new Projectile(x, y, dir, speed, tank, playing));
    }

    public void createExplosion(int x, int y, TemporaryObjectType explosionType) {
        temporaryObjects.add(new TemporaryObject(x, y, explosionType, playing));
    }

    public void update() {
        updateProjectiles();
        updateTemporaryObjects();
        updatePowerUps();
    }

    public void draw(Graphics g) {
        drawProjectiles(g);
        drawTemporaryObjects(g);
    }

    public void drawAfterPlayer(Graphics g) {
        drawShield(g);
        drawPowerUps(g);
    }


    private void updateProjectiles() {
        for (Projectile p : projectiles)
            if (p.isActive())
                p.update();
    }

    /**
     * Update explosions, spawns, etc
     */
    private void updateTemporaryObjects() {
        for (TemporaryObject e : temporaryObjects)
            if (e.isActive())
                e.update();
    }

    private void updatePowerUps() {
        for (PowerUp p : powerUps)
            if (p.isActive())
                p.update();
    }

    private void drawProjectiles(Graphics g) {
        // It's possible that new projectile can be created during the loop below
        // So we create a clone of the projectiles array and draw it
        ArrayList<Projectile> tempProjectiles = new ArrayList<>(projectiles);

        for (Projectile p : tempProjectiles)
            if (p.isActive())
                p.draw(g);
    }

    /**
     * Draw explosions, spawns, etc
     */
    private void drawTemporaryObjects(Graphics g) {
        // It's possible that new projectile can be created during the loop below
        // So we create a clone of the projectiles array and draw it
        ArrayList<TemporaryObject> tempTempObjects = new ArrayList<>(temporaryObjects);

        for (TemporaryObject e : tempTempObjects)
            if (e.isActive())
                // Shield should be drawn after player
                if (e.getType() != TemporaryObjectType.TO_SHIELD)
                    e.draw(g);
    }

    private void drawShield(Graphics g) {
        for (TemporaryObject e : temporaryObjects)
            if (e.isActive())
                // Shield should be drawn after player
                if (e.getType() == TemporaryObjectType.TO_SHIELD)
                    e.draw(g);
    }

    private void drawPowerUps(Graphics g) {
        for (PowerUp p : powerUps)
            if (p.isActive())
                p.draw(g);
    }

    public void resetAll() {
        for (Projectile p : projectiles)
            if (p.isActive())
                p.setActive(false);

        for (TemporaryObject e : temporaryObjects)
            if (e.isActive())
                e.setActive(false);
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
}
