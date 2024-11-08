package entities;

import gamestates.Playing;

import static utils.Constants.DirConstants.*;
import static utils.Constants.TankStateConstants.*;

public class Player extends Tank{

    public Player(TankType tankType, float x, float y, int width, int height, Playing playing) {
        super(tankType, x, y, width, height, playing);
        this.state = IDLE;
        this.curDir = UP;
    }


}
