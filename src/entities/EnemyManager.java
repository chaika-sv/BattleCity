package entities;

import gamestates.Playing;
import main.Game;
import objects.TemporaryObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static main.Game.TILES_DEFAULT_SIZE;
import static utils.Constants.EnemyConstants.*;

public class EnemyManager {

    private Playing playing;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private long lastSpawnDelayMS;
    private int curLevelEnemyCount;


    private Random rand;

    public EnemyManager(Playing playing) {
        this.playing = playing;
        rand = new Random();
    }

    public void generateEnemies() {

        // Calculate active enemies count
        int currentEnemyCount = 0;
        for (Enemy e : enemies)
            if (e.isActive())
                currentEnemyCount++;

        // No more active enemies and no more enemies to generate
        if (currentEnemyCount == 0 && curLevelEnemyCount >= MAX_ENEMY_CNT)
            // todo: level completed
            System.out.println("Level completed");

        // We haven't already generated all enemies for the level
        if (curLevelEnemyCount < MAX_ENEMY_CNT) {

            // We should have no more than MAX_ACTIVE_ENEMY_CNT active enemies at the same time
            if (currentEnemyCount < MAX_ACTIVE_ENEMY_CNT) {

                long currentTime = System.currentTimeMillis();

                if (currentTime - lastSpawnDelayMS > ENEMY_SPAWN_DELAY_MS) {
                    int nextDir = rand.nextInt(3);

                    int spawnX = ENEMY_SPAWN_X_1;
                    int spawnY = ENEMY_SPAWN_Y_1;

                    switch (nextDir) {
                        case 0 -> {
                            spawnX = ENEMY_SPAWN_X_1;
                            spawnY = ENEMY_SPAWN_Y_1;
                        }
                        case 1 -> {
                            spawnX = ENEMY_SPAWN_X_2;
                            spawnY = ENEMY_SPAWN_Y_2;
                        }
                        case 2 -> {
                            spawnX = ENEMY_SPAWN_X_3;
                            spawnY = ENEMY_SPAWN_Y_3;
                        }
                    }

                    playing.getObjectManager().createEnemySpawn(spawnX, spawnY);
                    curLevelEnemyCount++;

                    lastSpawnDelayMS = System.currentTimeMillis();
                }
            }
        }
    }


    public void spawnNewEnemy(int x, int y) {
        // todo: select enemy type
        TankType tankType = TankType.T_BASE;
        enemies.add(new Enemy(tankType, x, y, playing));
    }

    public void spawnNewEnemy(TankType tankType, int x, int y) {
        enemies.add(new Enemy(tankType, x, y, playing));
    }

    public void update() {
        if (!playing.isGameOver())
            generateEnemies();

        for (Enemy e : enemies)
            if (e.isActive())
                e.update();
    }

    public void draw(Graphics g) {
        // It's possible that new enemy can be created during the loop below
        // So we create a clone of the enemies array and draw it
        ArrayList<Enemy> tempEnemies = new ArrayList<>(enemies);

        for (Enemy e : tempEnemies)
            if (e.isActive())
                e.draw(g);
    }

    public void resetAll() {
        for (Enemy e : enemies)
            if (e.isActive())
                e.setActive(false);

        curLevelEnemyCount = 0;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
