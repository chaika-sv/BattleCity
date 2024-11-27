package entities;

import gamestates.Playing;


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


    public void addPoints(int v) {
        points += v;
    }

    public int getPoints() {
        return points;
    }
}
