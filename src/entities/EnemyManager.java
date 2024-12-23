package entities;

import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;

import static main.Game.TILES_DEFAULT_SIZE;
import static utils.Constants.EnemyConstants.*;

public class EnemyManager {

    private Playing playing;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private long lastSpawnDelayMS;
    private int curLevelEnemyCount;                 // Current enemies number

    private int maxEnemyCount;                      // Max number of enemies in the level (all types)
    private int maxActiveEnemyCount;                // Max number of enemies on the screen (at the same time)
    private int powerUpsEnemyCount;                 // Max number of enemies with power ups
    private int enemiesToKillCount;                 // Number of enemies left to kill in the level
    private Map<TankType, Integer> tanksCount;
    private Set<Integer> powerUpTanks;

    private Random rand;

    public EnemyManager(Playing playing) {
        this.playing = playing;
        rand = new Random();
        tanksCount = new LinkedHashMap<>();
    }

    /**
     * Apply enemy settings to the current level
     * @param enemySettings that needs to be applied
     */
    public void applyEnemySettings(EnemySettings enemySettings) {
        this.maxActiveEnemyCount = enemySettings.getActiveTanksCount();
        this.powerUpsEnemyCount = enemySettings.getPowerUpTanksCount();

        maxEnemyCount = 0;
        for(Map.Entry<TankType, Integer> entry : enemySettings.getTanksCount().entrySet()) {
            this.tanksCount.put(entry.getKey(), entry.getValue());
            maxEnemyCount += entry.getValue();
        }

        enemiesToKillCount = maxEnemyCount;
        curLevelEnemyCount = 0;

        powerUpTanks = new HashSet<>();
        int nextPowerUpNumber;

        // Randomly select numbers for tanks with power ups
        for (int i = 0; i < powerUpsEnemyCount; i++) {
            nextPowerUpNumber = rand.nextInt(maxEnemyCount);
            powerUpTanks.add(nextPowerUpNumber);
        }

    }

    /**
     * The method actually doesn't generate new enemies.
     * It generates new spawn points in one of three places.
     * It also calculates enemies that still needs to be generated in the level.
     */
    public void generateEnemies() {

        // Calculate active enemies count
        int currentEnemyCount = 0;
        for (Enemy e : enemies)
            if (e.isActive())
                currentEnemyCount++;

        // No more active enemies and no more enemies to generate
        if (currentEnemyCount == 0 && curLevelEnemyCount >= maxEnemyCount)
            playing.levelComplete();


        // We haven't already generated all enemies for the level
        if (curLevelEnemyCount < maxEnemyCount) {

            // We should have no more than maxActiveEnemyCount active enemies at the same time
            if (currentEnemyCount < maxActiveEnemyCount) {

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

                    lastSpawnDelayMS = System.currentTimeMillis();
                }
            }
        }
    }


    /**
     * Spawn new enemy of random type
     * @param x x coordinate to spawn
     * @param y y coordinate to spawn
     */
    public void spawnNewEnemy(int x, int y) {

        Rectangle2D.Float possibleTank = new Rectangle2D.Float(x, y, TILES_DEFAULT_SIZE * Game.SCALE, TILES_DEFAULT_SIZE * Game.SCALE);

        boolean doNotSpawn = false;

        // Check if new possible tank intersects any active enemy
        for (Enemy enemy : enemies)
            if (enemy.isActive())
                if (enemy.hitbox.intersects(possibleTank))
                    doNotSpawn = true;

        // Check if new possible tank intersects player
        if (playing.getPlayer().getHitbox().intersects(possibleTank))
            doNotSpawn = true;

        // Don't spawn new enemy if any possible collision
        if (!doNotSpawn) {

            TankType tankType;
            tankType = TankType.getTankTypeByCode(rand.nextInt(4), Enemy.class);

            // Trying to randomly select next tank type (if still need to spawn tanks of the type)
            while (!tanksCount.containsKey(tankType) || tanksCount.get(tankType) == 0)
                tankType = TankType.getTankTypeByCode(rand.nextInt(4), Enemy.class);

            // Decrease number of tanks of this type that still needs to be spawn
            tanksCount.computeIfPresent(tankType, (k, v) -> v - 1);

            // Create new enemy
            enemies.add(new Enemy(tankType, x, y, powerUpTanks.contains(curLevelEnemyCount), playing));
            curLevelEnemyCount++;
        }
    }

    public void blowUpAllEnemies() {
        for (Enemy e : enemies)
            if (e.isActive())
                e.killTheTank(null);
    }

    public void decreasedEnemiesToKillCount() {
        enemiesToKillCount--;
    }

    public void spawnNewEnemy(TankType tankType, int x, int y) {
        enemies.add(new Enemy(tankType, x, y, false, playing));
    }

    public void update() {
        if (!playing.isGameOver() || !playing.isPause() || !playing.isStartLevel() || !playing.isLevelComplete())
            generateEnemies();

        if (!playing.isFreeze())
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

    public int getEnemiesToKillCount() {
        return enemiesToKillCount;
    }
}
