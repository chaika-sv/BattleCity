package entities;

import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.util.ArrayList;

import static main.Game.TILES_DEFAULT_SIZE;

public class EnemyManager {

    private Playing playing;
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;

        enemies.add(new Enemy(TankType.T_BASE,  100, 100, (int)(TILES_DEFAULT_SIZE * Game.SCALE), (int)(TILES_DEFAULT_SIZE * Game.SCALE), playing ));
    }

    public void update() {
        for (Enemy e : enemies)
            if (e.isActive())
                e.update();
    }

    public void draw(Graphics g) {
        for (Enemy e : enemies)
            if (e.isActive())
                e.draw(g);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
