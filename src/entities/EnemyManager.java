package entities;

import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.util.ArrayList;

import static main.Game.TILES_DEFAULT_SIZE;
import static utils.Constants.LevelConstants.*;

public class EnemyManager {

    private Playing playing;
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;

        enemies.add(new Enemy(TankType.T_BASE,  ENEMY_SPAWN_X_1, ENEMY_SPAWN_Y_1, (int)(TILES_DEFAULT_SIZE * Game.SCALE), (int)(TILES_DEFAULT_SIZE * Game.SCALE), playing ));
        enemies.add(new Enemy(TankType.T_BASE,  ENEMY_SPAWN_X_2, ENEMY_SPAWN_Y_2, (int)(TILES_DEFAULT_SIZE * Game.SCALE), (int)(TILES_DEFAULT_SIZE * Game.SCALE), playing ));
        enemies.add(new Enemy(TankType.T_BASE,  ENEMY_SPAWN_X_3, ENEMY_SPAWN_Y_3, (int)(TILES_DEFAULT_SIZE * Game.SCALE), (int)(TILES_DEFAULT_SIZE * Game.SCALE), playing ));
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

    public void resetAll() {
        for (Enemy e : enemies)
            if (e.isActive())
                e.setActive(false);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
