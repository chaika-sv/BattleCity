package entities;

import gamestates.Playing;

import static utils.Constants.PowerUpConstants.*;


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

    public void applyPowerUp(int powerUpType) {

        switch(powerUpType) {
            case PU_SHIELD -> {
                playing.getObjectManager().createShield(this);
            }
            case PU_CLOCK -> {

            }
            case PU_SHOVEL -> {

            }
            case PU_STAR -> {

            }
            case PU_GRENADE -> {

            }
            case PU_HEALTH -> {
                addHealth(1);
            }
            case PU_GUN -> {

            }
        }

    }


    public void addPoints(int v) {
        points += v;
    }

    public int getPoints() {
        return points;
    }
}
