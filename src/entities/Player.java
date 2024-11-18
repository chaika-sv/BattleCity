package entities;

import gamestates.Playing;


public class Player extends Tank{

    public Player(TankType tankType, float x, float y, int width, int height, Playing playing) {
        super(tankType, x, y, width, height, playing);
    }

    @Override
    public void resetAll() {
        super.resetAll();
        active = true;
    }
}
