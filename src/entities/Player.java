package entities;

import gamestates.Playing;


public class Player extends Tank{

    public Player(TankType tankType, float x, float y, Playing playing) {
        super(tankType, x, y, playing);
    }

    @Override
    public void resetAll() {
        super.resetAll();
        active = true;
    }
}
