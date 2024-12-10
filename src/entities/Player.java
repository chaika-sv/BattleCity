package entities;

import gamestates.Playing;

import static utils.Constants.DirConstants.UP;
import static utils.Constants.LevelConstants.PLAYER_SPAWN_X;
import static utils.Constants.LevelConstants.PLAYER_SPAWN_Y;
import static utils.Constants.TankColorConstants.PLAYER_YELLOW;
import static utils.Constants.TankTypeConstants.MAX_TANK_TYPE;


public class Player extends Tank{

    private int points;

    public Player(TankType tankType, float x, float y, Playing playing) {
        super(tankType, x, y, playing);
        tankColor = PLAYER_YELLOW;
    }

    @Override
    public void resetAll() {
        super.resetAll();
        active = true;
        points = 0;
    }

    @Override
    public void killTheTank(Tank killerTank) {
        super.killTheTank(killerTank);

        playing.gameOver();
    }

    @Override
    public void injureTheTank() {
        super.injureTheTank();

        curDir = UP;
        hitbox.x = PLAYER_SPAWN_X;
        hitbox.y = PLAYER_SPAWN_Y;
        syncHitboxWithSprite();
        playing.getObjectManager().createShield(this);
        tankType = TankType.T_BASE;
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
