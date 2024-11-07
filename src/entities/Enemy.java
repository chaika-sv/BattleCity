package entities;

import gamestates.Playing;
import main.Game;

import static utils.Constants.TankStateConstants.IDLE;

public class Enemy extends Tank{

    private boolean active = true;

    public Enemy(TankType tankType, float x, float y, int width, int height, Playing playing) {
        super(tankType, x, y, width, height, playing);
        this.state = IDLE;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
