package entities;

import java.util.Map;

public class EnemySettings {

    private Map<TankType, Integer> tanksCount;
    private int activeTanksCount;

    public EnemySettings(Map<TankType, Integer> tanksCount, int activeTanksCount) {
        this.tanksCount = tanksCount;
        this.activeTanksCount = activeTanksCount;
    }

    public Map<TankType, Integer> getTanksCount() {
        return tanksCount;
    }

    public int getActiveTanksCount() {
        return activeTanksCount;
    }
}
