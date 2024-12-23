package objects;

import entities.Tank;
import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import static main.Game.TILES_SIZE;
import static utils.Constants.Audio.POWER_UP_APPEAR;
import static utils.Constants.DEBUG_MODE;
import static utils.Constants.PowerUpConstants.*;
import static utils.LoadSaveImages.*;

public class ObjectManager {

    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<TemporaryObject> temporaryObjects = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private ArrayList<Rectangle2D.Float> debugBlocks = new ArrayList<>();

    private Random rand;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadObjectSprites();
        rand = new Random();
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
     * Generate random power up in random place
     */
    public void generateNewPowerUp() {
        //int powerUpType = rand.nextInt(MAX_POWER_UP_NUMBER);
        int powerUpType = PU_STAR;
        int x = rand.nextInt(Game.GAME_WIDTH - TILES_SIZE);
        int y = rand.nextInt(Game.GAME_HEIGHT - TILES_SIZE);

        powerUps.add(new PowerUp(playing, powerUpType, x, y));
        playing.getGame().getAudioPlayer().playEffect(POWER_UP_APPEAR);
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

    public void createDebugBlock(Rectangle2D.Float block) {
        debugBlocks.add(block);
    }

    public void update() {
        updateProjectiles();
        updateTemporaryObjects();
        updatePowerUps();
    }

    public void draw(Graphics g) {
        drawProjectiles(g);
        drawTemporaryObjects(g);

        if (DEBUG_MODE)
            drawDebugBlocks(g);
    }

    public void drawAfterPlayer(Graphics g) {
        drawShield(g);
        drawPowerUps(g);
    }

    private void drawDebugBlocks(Graphics g) {
        g.setColor(new Color(27, 55, 76, 240));
        for(Rectangle2D.Float block : debugBlocks)
            g.fillRect((int)block.x, (int)block.y, (int)block.width, (int)block.height);
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
        ArrayList<PowerUp> tempPowerUps = new ArrayList<>(powerUps);

        for (PowerUp p : tempPowerUps)
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

        java.util.List<TemporaryObject> shields = temporaryObjects.stream().filter(tempObj -> tempObj.getType().equals(TemporaryObjectType.TO_SHIELD)).collect(Collectors.toList());

        for (TemporaryObject e : shields)
            if (e.isActive())
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

        for (PowerUp p : powerUps)
            if (p.isActive())
                p.setActive(false);
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public ArrayList<Rectangle2D.Float> getDebugBlocks() {
        return debugBlocks;
    }
}
