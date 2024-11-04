package objects;

import gamestates.Playing;

import java.awt.*;
import java.util.ArrayList;

import static utils.LoadSave.*;

public class ObjectManager {

    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadObjectSprites();
    }

    private void loadObjectSprites() {
        LoadProjectileImages();
        LoadExplosionImages();
    }

    public void shootProjectile(int x, int y, int dir) {
        projectiles.add(new Projectile(x, y, dir, playing));
    }

    public void createExplosion(int x, int y) {
        explosions.add(new Explosion(x, y, playing));
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
        for (Explosion e : explosions)
            if (e.isActive())
                e.update();
    }

    private void drawProjectiles(Graphics g) {
        for (Projectile p : projectiles)
            if (p.isActive())
                p.draw(g);
    }

    private void drawExplosions(Graphics g) {
        for (Explosion e : explosions)
            if (e.isActive())
                e.draw(g);
    }


}
