package objects;

import gamestates.Playing;

import java.awt.*;
import java.util.ArrayList;

import static utils.LoadSave.*;

public class ObjectManager {

    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadObjectSprites();
    }

    private void loadObjectSprites() {
        LoadProjectileImages();
    }

    public void shootProjectiles(int x, int y, int dir) {
        projectiles.add(new Projectile(x, y, dir));
    }

    public void update() {
        updateProjectiles();
    }

    private void updateProjectiles() {
        for (Projectile p : projectiles)
            if (p.isActive())
                p.update();
    }

    public void draw(Graphics g) {
        for (Projectile p : projectiles)
            if (p.isActive())
                p.draw(g);
    }

}
