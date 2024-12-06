package entities;

import gamestates.Playing;

import static utils.Constants.TankTypeConstants.MAX_TANK_TYPE;


public class Player extends Tank{

    private int points;

    public Player(TankType tankType, float x, float y, Playing playing) {
        super(tankType, x, y, playing);
    }

    @Override
    public void resetAll() {
        super.resetAll();
        active = true;
        points = 0;
    }

    public void levelUp() {
        if (tankType.getId() < MAX_TANK_TYPE) {
            tankType = TankType.getTankTypeByCode(tankType.getId() + 1);
            applyTankCharacteristics(tankType);
        }
    }

    public void addPoints(int v) {
        points += v;
    }

    public int getPoints() {
        return points;
    }
}
