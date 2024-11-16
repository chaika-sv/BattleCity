package objects;

import entities.Tank;
import gamestates.Playing;

import java.awt.*;
import java.util.ArrayList;

import static utils.LoadSave.*;

public class ObjectManager {

    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<TemporaryObject> temporaryObjects = new ArrayList<>();

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadObjectSprites();
    }

    private void loadObjectSprites() {
        LoadProjectileImages();
        LoadExplosionImages();
    }

    public void shootProjectile(int x, int y, int dir, float speed, Tank tank) {
        projectiles.add(new Projectile(x, y, dir, speed, tank, playing));
    }

    public void createExplosion(int x, int y, TemporaryObjectType type) {
        temporaryObjects.add(new TemporaryObject(x, y, type, playing));
    }

    public void update() {
        updateProjectiles();
        updateExplosions();
    }

    public void draw(Graphics g) {
        drawProjectiles(g);
        drawExplosions(g);
    }


    private void updateProjectiles() {
        for (Projectile p : projectiles)
            if (p.isActive())
                p.update();
    }

    private void updateExplosions() {
        for (TemporaryObject e : temporaryObjects)
            if (e.isActive())
                e.update();
    }

    private void drawProjectiles(Graphics g) {
        // It's possible that new projectile can be created during the loop below
        // So we create a clone of the projectiles array and draw it
        ArrayList<Projectile> tempProjectiles = new ArrayList<>(projectiles);

        for (Projectile p : tempProjectiles)
            if (p.isActive())
                p.draw(g);
    }

    private void drawExplosions(Graphics g) {
        // It's possible that new projectile can be created during the loop below
        // So we create a clone of the projectiles array and draw it
        ArrayList<TemporaryObject> tempTempObjects = new ArrayList<>(temporaryObjects);

        for (TemporaryObject e : tempTempObjects)
            if (e.isActive())
                e.draw(g);
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
