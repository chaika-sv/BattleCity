package entities;

import java.util.Map;

public class EnemySettings {

    private Map<TankType, Integer> tanksCount;
    private int activeTanksCount;
    private int powerUpTanksCount;

    public EnemySettings(Map<TankType, Integer> tanksCount, int activeTanksCount, int powerUpTanksCount) {
        this.tanksCount = tanksCount;
        this.activeTanksCount = activeTanksCount;
        this.powerUpTanksCount = powerUpTanksCount;
    }

    public Map<TankType, Integer> getTanksCount() {
        return tanksCount;
    }

    public int getActiveTanksCount() {
        return activeTanksCount;
    }

    public int getPowerUpTanksCount() {
        return powerUpTanksCount;
    }
}
